package com.fos.game.engine.ecs.systems.renderer_old.shaders.rigged;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.components.lights3d.ComponentPointLight;
import com.fos.game.engine.ecs.components.lights3d.ComponentDirectionalLight;
import com.fos.game.engine.ecs.components.lights3d.LightingEnvironment;
import com.fos.game.engine.ecs.components.lights3d.ComponentSpotLight;
import com.fos.game.engine.ecs.components.modelinstance_old.RiggedModelInstance;
import com.fos.game.engine.ecs.systems.renderer_old.materials.instances.UniformColorMaterialInstance;
import com.fos.game.engine.ecs.systems.renderer_old.shaders.base.GameShader;

public class RiggedUniformColorCombinedShader extends GameShader {
    // TODO: make this shit work
    //private static final String vertex = Gdx.files.classpath("com/fos/game/engine/renderer/glsl/rigged/riggedUniformColorVertexShader.glsl").readString();
    //private static final String fragment = Gdx.files.classpath("com/fos/game/engine/renderer/glsl/rigged/riggedUniformColorFragmentShader.glsl").readString();

    public static final int MAX_NUMBER_OF_BONES = 24;
    private LightingEnvironment lightingEnvironment;

    // uniform variable bindings (locations)
    // body transform
    private int uniform_bodyTransformLocation;
    private int uniform_bonesTransformLocation[];
    // camera
    private int uniform_cameraPositionLocation;
    private int uniform_cameraTransformLocation;
    private int uniform_cameraProjectionLocation;
    private int uniform_cameraViewLocation;
    private int uniform_cameraCombinedLocation;
    // point lights
    private int uniforms_pointLightColorLocations[];
    private int uniforms_pointLightsPositionLocations[];
    private int uniforms_pointLightsIntensityLocations[];
    // spot lights
    private int uniforms_spotLightPositionsLocations[];
    private int uniforms_spotLightDirectionsLocations[];
    private int uniforms_spotLightColorsLocations[];
    private int uniforms_spotLightAnglesLocations[];
    private int uniforms_spotLightIntensitiesLocations[];
    // directional lights
    private int uniforms_directionalLightColorLocations[];
    private int uniforms_directionalLightsDirectionLocations[];
    private int uniforms_directionalLightsIntensityLocations[];
    // material
    private int uniform_reflectivityLocation;
    private int uniform_shineDamperLocation;
    private int uniform_ambientLocation;
    private int uniform_rLocation;
    private int uniform_gLocation;
    private int uniform_bLocation;

    public RiggedUniformColorCombinedShader() {
        super(Gdx.files.internal("shaders/rigged/riggedUniformColorVertexShader.glsl").readString(),
                Gdx.files.internal("shaders/rigged/riggedUniformColorFragmentShader.glsl").readString());
    }

    @Override
    public void begin(Camera camera, RenderContext renderContext) {
        shaderProgram.bind();
        shaderProgram.setUniformf(uniform_cameraPositionLocation, camera.position);
        shaderProgram.setUniformMatrix(uniform_cameraProjectionLocation, camera.projection);
        shaderProgram.setUniformMatrix(uniform_cameraViewLocation, camera.view);
        shaderProgram.setUniformMatrix(uniform_cameraCombinedLocation, camera.combined);
        renderContext.setDepthTest(GL20.GL_LEQUAL);
        renderContext.setCullFace(GL20.GL_BACK);
    }

    @Override
    public void render(Renderable renderable) {
        shaderProgram.setUniformMatrix(uniform_bodyTransformLocation, renderable.worldTransform);
        final RiggedModelInstance modelInstance = (RiggedModelInstance) renderable.userData;
        final Matrix4[] boneTransforms = modelInstance.rig.getUpdatedBoneTransforms();
        for (int i = 0; i < boneTransforms.length; i++) {
            shaderProgram.setUniformMatrix(uniform_bonesTransformLocation[i], boneTransforms[i]);
        }
        lightingEnvironment = (LightingEnvironment) renderable.environment;
        loadEnvironmentsParamsToGPU(lightingEnvironment);
        final UniformColorMaterialInstance fosMaterial = (UniformColorMaterialInstance) modelInstance.getMaterial(renderable);
        loadMaterialParamsToGPU(fosMaterial);
        renderable.meshPart.render(shaderProgram);
    }

    private void loadMaterialParamsToGPU(final UniformColorMaterialInstance fosMaterial) {
        // RGB
        shaderProgram.setUniformf(uniform_rLocation, fosMaterial.r);
        shaderProgram.setUniformf(uniform_gLocation, fosMaterial.g);
        shaderProgram.setUniformf(uniform_bLocation, fosMaterial.b);
        // specular + ambient
        shaderProgram.setUniformf(uniform_shineDamperLocation, fosMaterial.shineDamper);
        shaderProgram.setUniformf(uniform_reflectivityLocation, fosMaterial.reflectivity);
        shaderProgram.setUniformf(uniform_ambientLocation, fosMaterial.ambient);
    }

    private void loadEnvironmentsParamsToGPU(final LightingEnvironment lightingEnvironment) {
        if (lightingEnvironment == null) return;
        // load environment point lights
        Array<ComponentPointLight> pointLights = lightingEnvironment.pointLights;
        for (int i = 0; i < LightingEnvironment.MAX_POINT_LIGHTS; i++) {
            if (i < pointLights.size) {
                shaderProgram.setUniformf(uniforms_pointLightsPositionLocations[i], pointLights.get(i).worldPosition);
                shaderProgram.setUniformf(uniforms_pointLightColorLocations[i], pointLights.get(i).color.r, pointLights.get(i).color.g, pointLights.get(i).color.b);
                shaderProgram.setUniformf(uniforms_pointLightsIntensityLocations[i], pointLights.get(i).intensity);
            }
        }
        // load environment directional lights
        Array<ComponentDirectionalLight> directionalLights = lightingEnvironment.directionalLights;
        for (int i = 0; i < LightingEnvironment.MAX_DIRECTIONAL_LIGHTS; i++) {
            if (i < directionalLights.size) {
                shaderProgram.setUniformf(uniforms_directionalLightsDirectionLocations[i], directionalLights.get(i).worldDirection);
                shaderProgram.setUniformf(uniforms_directionalLightColorLocations[i], directionalLights.get(i).color.r, directionalLights.get(i).color.g, directionalLights.get(i).color.b);
                shaderProgram.setUniformf(uniforms_directionalLightsIntensityLocations[i], directionalLights.get(i).intensity);
            }
        }
        // load environment spot lights
        Array<ComponentSpotLight> spotLights = lightingEnvironment.spotLights;
        for (int i = 0; i < LightingEnvironment.MAX_SPOT_LIGHTS; i++) {
            if (i < spotLights.size) {
                shaderProgram.setUniformf(uniforms_spotLightPositionsLocations[i], spotLights.get(i).worldPosition);
                shaderProgram.setUniformf(uniforms_spotLightDirectionsLocations[i], spotLights.get(i).direction);
                shaderProgram.setUniformf(uniforms_spotLightColorsLocations[i], spotLights.get(i).color.r, spotLights.get(i).color.g, spotLights.get(i).color.b);
                shaderProgram.setUniformf(uniforms_spotLightIntensitiesLocations[i], spotLights.get(i).intensity);
                shaderProgram.setUniformf(uniforms_spotLightAnglesLocations[i], spotLights.get(i).cutoffAngle);
            }
        }
    }

    @Override
    protected void registerUniformLocations() {
        // body transform
        uniform_bodyTransformLocation = shaderProgram.fetchUniformLocation("body_transform", false);
        uniform_bonesTransformLocation = new int[MAX_NUMBER_OF_BONES];
        for (int i = 0; i < MAX_NUMBER_OF_BONES; i++) {
            int boneTransformLocation = shaderProgram.fetchUniformLocation("bones_transforms[" + i + "]", false);
            uniform_bonesTransformLocation[i] = boneTransformLocation;
        }
        // render context
        uniform_cameraPositionLocation = shaderProgram.fetchUniformLocation("camera_position", false);
        uniform_cameraTransformLocation = shaderProgram.fetchUniformLocation("camera_transform", false);
        uniform_cameraProjectionLocation = shaderProgram.fetchUniformLocation("camera_projection", false);
        uniform_cameraViewLocation = shaderProgram.fetchUniformLocation("camera_view", false);
        uniform_cameraCombinedLocation = shaderProgram.fetchUniformLocation("camera_combined", false);
        // set substance variables locations
        uniform_reflectivityLocation = shaderProgram.fetchUniformLocation("reflectivity", false);
        uniform_shineDamperLocation = shaderProgram.fetchUniformLocation("shine_damper", false);
        uniform_ambientLocation = shaderProgram.fetchUniformLocation("ambient", false);
        uniform_rLocation = shaderProgram.fetchUniformLocation("r", false);
        uniform_gLocation = shaderProgram.fetchUniformLocation("g", false);
        uniform_bLocation = shaderProgram.fetchUniformLocation("b", false);
        // set point lights locations
        uniforms_pointLightColorLocations = new int[LightingEnvironment.MAX_POINT_LIGHTS];
        uniforms_pointLightsPositionLocations = new int[LightingEnvironment.MAX_POINT_LIGHTS];
        uniforms_pointLightsIntensityLocations = new int[LightingEnvironment.MAX_POINT_LIGHTS];
        for (int i = 0; i < LightingEnvironment.MAX_POINT_LIGHTS; i++) {
            int lightPositionLocation = shaderProgram.fetchUniformLocation("point_lights[" + i + "].position", false);
            uniforms_pointLightsPositionLocations[i] = lightPositionLocation;
            int lightColorLocation = shaderProgram.fetchUniformLocation("point_lights[" + i + "].color", false);
            uniforms_pointLightColorLocations[i] = lightColorLocation;
            int lightIntensityLocation = shaderProgram.fetchUniformLocation("point_lights[" + i + "].intensity", false);
            uniforms_pointLightsIntensityLocations[i] = lightIntensityLocation;
        }
        // set directional lights locations
        uniforms_directionalLightsDirectionLocations = new int[LightingEnvironment.MAX_DIRECTIONAL_LIGHTS];
        uniforms_directionalLightColorLocations = new int[LightingEnvironment.MAX_DIRECTIONAL_LIGHTS];
        uniforms_directionalLightsIntensityLocations = new int[LightingEnvironment.MAX_DIRECTIONAL_LIGHTS];
        for (int i = 0; i < LightingEnvironment.MAX_DIRECTIONAL_LIGHTS; i++) {
            int lightDirectionLocation = shaderProgram.fetchUniformLocation("directional_lights[" + i + "].direction", false);
            uniforms_directionalLightsDirectionLocations[i] = lightDirectionLocation;
            int lightColorLocation = shaderProgram.fetchUniformLocation("directional_lights[" + i + "].color", false);
            uniforms_directionalLightColorLocations[i] = lightColorLocation;
            int lightIntensityLocation = shaderProgram.fetchUniformLocation("directional_lights[" + i + "].intensity", false);
            uniforms_directionalLightsIntensityLocations[i] = lightIntensityLocation;
        }
        // set spot lights locations
        uniforms_spotLightPositionsLocations = new int[LightingEnvironment.MAX_SPOT_LIGHTS];
        uniforms_spotLightDirectionsLocations = new int[LightingEnvironment.MAX_SPOT_LIGHTS];
        uniforms_spotLightColorsLocations = new int[LightingEnvironment.MAX_SPOT_LIGHTS];
        uniforms_spotLightIntensitiesLocations = new int[LightingEnvironment.MAX_SPOT_LIGHTS];
        uniforms_spotLightAnglesLocations = new int[LightingEnvironment.MAX_SPOT_LIGHTS];
        for (int i = 0; i < LightingEnvironment.MAX_SPOT_LIGHTS; i++) {
            int lightPositionsLocations = shaderProgram.fetchUniformLocation("spot_lights[" + i + "].position", false);
            uniforms_spotLightPositionsLocations[i] = lightPositionsLocations;
            int lightDirectionLocation = shaderProgram.fetchUniformLocation("spot_lights[" + i + "].direction", false);
            uniforms_spotLightDirectionsLocations[i] = lightDirectionLocation;
            int lightColorLocation = shaderProgram.fetchUniformLocation("spot_lights[" + i + "].color", false);
            uniforms_spotLightColorsLocations[i] = lightColorLocation;
            int lightIntensityLocation = shaderProgram.fetchUniformLocation("spot_lights[" + i + "].intensity", false);
            uniforms_spotLightIntensitiesLocations[i] = lightIntensityLocation;
            int lightAngleLocation = shaderProgram.fetchUniformLocation("spot_lights[" + i + "].angle", false);
            uniforms_spotLightAnglesLocations[i] = lightAngleLocation;
        }
    }
}
