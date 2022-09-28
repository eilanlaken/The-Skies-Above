package com.fos.game.engine.renderer.shaders.pbr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.components.lights.ComponentPointLight;
import com.fos.game.engine.components.lights.ComponentDirectionalLight;
import com.fos.game.engine.components.lights.LightingEnvironment;
import com.fos.game.engine.components.modelinstance.ModelInstance;
import com.fos.game.engine.renderer.materials.base.BasicMaterialMap;
import com.fos.game.engine.renderer.materials.instances.BlendAlbedoMapsMaterialInstance;
import com.fos.game.engine.renderer.shaders.base.GameShader;
import com.fos.game.engine.renderer.system.Renderer;

public class PBRBlendAlbedoMapsShader extends GameShader {

    private static final short BLENDED_MAPS_NUMBER = 4;

    // current render context - for easy access
    private Camera camera;
    private RenderContext renderContext;
    private LightingEnvironment lightingEnvironment;

    // uniform variable bindings (locations)
    private int uniform_bodyTransformLocation;
    private int uniform_cameraPositionLocation;
    private int uniform_cameraCombinedLocation;
    private int uniforms_pointLightColorLocations[];
    private int uniforms_pointLightsPositionLocations[];
    private int uniforms_pointLightsIntensityLocations[];
    private int uniforms_directionalLightColorLocations[];
    private int uniforms_directionalLightsDirectionLocations[];
    private int uniforms_directionalLightsIntensityLocations[];
    private int uniforms_materialMapsTextureLocations[];
    private int uniforms_materialMapsRoughnessLocations[];
    private int uniforms_materialMapsMetallicLocations[];
    private int uniforms_materialMapsAmbientLocations[];
    private int uniform_blendMapLocation;

    private int uniform_gammaLocation;

    public PBRBlendAlbedoMapsShader() {
        super(Gdx.files.internal("shaders/pbr/pbrBlendAlbedoMapsVertexShader.glsl").readString(),
                Gdx.files.internal("shaders/pbr/pbrBlendAlbedoMapsFragmentShader.glsl").readString());
        shaderProgram.bind();
        for (int i = 0; i < BLENDED_MAPS_NUMBER; i++) {
            shaderProgram.setUniformi(uniforms_materialMapsTextureLocations[i], i + 1);
        }
        shaderProgram.setUniformi(uniform_blendMapLocation, 0);
    }

    @Override
    public void begin(Camera camera, RenderContext context) {
        this.camera = camera;
        this.renderContext = context;
        shaderProgram.bind();
        shaderProgram.setUniformf(uniform_cameraPositionLocation, camera.position);
        shaderProgram.setUniformMatrix(uniform_cameraCombinedLocation, camera.combined);
        renderContext.setDepthTest(GL20.GL_LEQUAL);
        renderContext.setCullFace(GL20.GL_BACK);
    }

    @Override
    public void render(Renderable renderable) {
        shaderProgram.setUniformMatrix(uniform_bodyTransformLocation, renderable.worldTransform);
        lightingEnvironment = (LightingEnvironment) renderable.environment;
        loadEnvironmentsParamsToGPU(lightingEnvironment);
        final ModelInstance modelInstance = (ModelInstance) renderable.userData;
        BlendAlbedoMapsMaterialInstance fosMaterial = (BlendAlbedoMapsMaterialInstance) modelInstance.getMaterial(renderable);
        loadMaterialParamsToGPU(fosMaterial);
        renderable.meshPart.render(shaderProgram);
    }

    private void loadMaterialParamsToGPU(final BlendAlbedoMapsMaterialInstance fosMaterial) {
        BasicMaterialMap[] materialMaps = fosMaterial.basicMaterialMaps;
        for (int i = 0; i < BLENDED_MAPS_NUMBER; i++) {
            shaderProgram.setUniformf(uniforms_materialMapsRoughnessLocations[i], materialMaps[i].roughness);
            shaderProgram.setUniformf(uniforms_materialMapsMetallicLocations[i], materialMaps[i].metallic);
            shaderProgram.setUniformf(uniforms_materialMapsAmbientLocations[i], materialMaps[i].ambient);
        }
        // bind texture
        Texture blendMap = fosMaterial.blendTexture;
        Texture texture_0 = materialMaps[0].texture;
        Texture texture_1 = materialMaps[1].texture;
        Texture texture_2 = materialMaps[2].texture;
        Texture texture_3 = materialMaps[3].texture;

        Gdx.gl20.glActiveTexture(GL20.GL_TEXTURE4);
        texture_3.bind();

        Gdx.gl20.glActiveTexture(GL20.GL_TEXTURE3);
        texture_2.bind();

        Gdx.gl20.glActiveTexture(GL20.GL_TEXTURE2);
        texture_1.bind();

        Gdx.gl20.glActiveTexture(GL20.GL_TEXTURE1);
        texture_0.bind();

        Gdx.gl20.glActiveTexture(GL20.GL_TEXTURE0);
        blendMap.bind(0);
    }

    private void loadEnvironmentsParamsToGPU(final LightingEnvironment lightingEnvironment) {
        Array<ComponentPointLight> pointLights = lightingEnvironment.pointLights;
        for (int i = 0; i < LightingEnvironment.MAX_POINT_LIGHTS; i++) {
            if (i < pointLights.size) {
                shaderProgram.setUniformf(uniforms_pointLightsPositionLocations[i], pointLights.get(i).worldPosition);
                shaderProgram.setUniformf(uniforms_pointLightColorLocations[i], pointLights.get(i).color.r, pointLights.get(i).color.g, pointLights.get(i).color.b);
                shaderProgram.setUniformf(uniforms_pointLightsIntensityLocations[i], pointLights.get(i).intensity);
            }
        }
        Array<ComponentDirectionalLight> directionalLights = lightingEnvironment.directionalLights;
        for (int i = 0; i < LightingEnvironment.MAX_DIRECTIONAL_LIGHTS; i++) {
            if (i < directionalLights.size) {
                shaderProgram.setUniformf(uniforms_directionalLightsDirectionLocations[i], directionalLights.get(i).worldDirection);
                shaderProgram.setUniformf(uniforms_directionalLightColorLocations[i], directionalLights.get(i).color.r, directionalLights.get(i).color.g, directionalLights.get(i).color.b);
                shaderProgram.setUniformf(uniforms_directionalLightsIntensityLocations[i], directionalLights.get(i).intensity);
            }
        }
        float gamma = Renderer.DEFAULT_GAMMA;
        shaderProgram.setUniformf(uniform_gammaLocation, gamma);
    }

    @Override
    protected void registerUniformLocations() {
        // body transform
        uniform_bodyTransformLocation = shaderProgram.fetchUniformLocation("body_transform", false);
        uniform_cameraPositionLocation = shaderProgram.fetchUniformLocation("camera_position", false);
        uniform_cameraCombinedLocation = shaderProgram.fetchUniformLocation("camera_combined", false);
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
        // material maps and blend maps
        uniform_blendMapLocation = shaderProgram.fetchUniformLocation("blend_map", false);
        uniforms_materialMapsTextureLocations = new int[BLENDED_MAPS_NUMBER];
        uniforms_materialMapsRoughnessLocations = new int[BLENDED_MAPS_NUMBER];
        uniforms_materialMapsMetallicLocations = new int[BLENDED_MAPS_NUMBER];
        uniforms_materialMapsAmbientLocations = new int[BLENDED_MAPS_NUMBER];
        for (int i = 0; i < BLENDED_MAPS_NUMBER; i++) {
            uniforms_materialMapsTextureLocations[i] = shaderProgram.fetchUniformLocation("material_maps[" + i + "].texture", false);
            uniforms_materialMapsRoughnessLocations[i] = shaderProgram.fetchUniformLocation("material_maps[" + i + "].roughness", false);
            uniforms_materialMapsMetallicLocations[i] = shaderProgram.fetchUniformLocation("material_maps[" + i + "].metallic", false);
            uniforms_materialMapsAmbientLocations[i] = shaderProgram.fetchUniformLocation("material_maps[" + i + "].ambient", false);
        }
        // gamma correction
        uniform_gammaLocation = shaderProgram.fetchUniformLocation("gamma", false);
    }
}
