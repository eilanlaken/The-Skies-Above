package com.fos.game.engine.renderer.shaders.simple;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.components.lights.ComponentDirectionalLight;
import com.fos.game.engine.components.lights.LightingEnvironment;
import com.fos.game.engine.components.lights.ComponentPointLight;
import com.fos.game.engine.components.modelinstance.ModelInstance;
import com.fos.game.engine.renderer.materials.instances.DiffuseNormalMapsMaterialInstance;
import com.fos.game.engine.renderer.shaders.base.GameShader;
import com.fos.game.engine.renderer.system.Renderer;

public class DiffuseNormalMapsShader extends GameShader {

    // current render context - for easy access
    private Camera camera;
    private RenderContext renderContext;
    private LightingEnvironment lightingEnvironment;
    // uniform variable bindings (locations)
    private int uniform_bodyTransformLocation;
    private int uniform_cameraPositionLocation;
    private int uniform_cameraTransformLocation;
    private int uniform_cameraProjectionLocation;
    private int uniform_cameraViewLocation;
    private int uniform_cameraCombinedLocation;
    private int uniforms_pointLightColorLocations[];
    private int uniforms_pointLightsPositionLocations[];
    private int uniforms_pointLightsIntensityLocations[];
    private int uniforms_directionalLightColorLocations[];
    private int uniforms_directionalLightsDirectionLocations[];
    private int uniforms_directionalLightsIntensityLocations[];
    private int uniform_reflectivityLocation;
    private int uniform_shineDamperLocation;
    private int uniform_ambientLocation;
    private int uniform_textureLocation;
    private int uniform_atlasWidthInvLocation;
    private int uniform_atlasHeightInvLocation;
    private int uniform_diffuseMapXLocation;
    private int uniform_diffuseMapYLocation;
    private int uniform_normalMapXLocation;
    private int uniform_normalMapYLocation;
    private int uniform_widthLocation;
    private int uniform_heightLocation;
    // gamma correction and TODO: HDR tone mapping
    private int uniform_gammaLocation;

    public DiffuseNormalMapsShader() {
        super(Gdx.files.internal("shaders/simple/fosDiffuseNormalMapsVertexShader.glsl").readString(),
                Gdx.files.internal("shaders/simple/fosDiffuseNormalMapsFragmentShader.glsl").readString());
        shaderProgram.bind();
        shaderProgram.setUniformi(uniform_textureLocation, 0);
    }

    @Override
    public void begin(Camera camera, RenderContext context) {
        this.camera = camera;
        this.renderContext = context;
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
        final ModelInstance modelInstance = (ModelInstance) renderable.userData;
        shaderProgram.setUniformMatrix(uniform_bodyTransformLocation, renderable.worldTransform);
        lightingEnvironment = (LightingEnvironment) renderable.environment;
        loadEnvironmentsParamsToGPU(lightingEnvironment);
        DiffuseNormalMapsMaterialInstance fosMaterial = (DiffuseNormalMapsMaterialInstance) modelInstance.getMaterial(renderable);
        loadMaterialParamsToGPU(fosMaterial);
        renderable.meshPart.render(shaderProgram);
    }

    private void loadMaterialParamsToGPU(final DiffuseNormalMapsMaterialInstance fosMaterial) {
        // bind texture
        Texture texture = fosMaterial.spriteSheet.getTextures().first();
        texture.bind();

        // load uv-remapping params
        final float inv_atlasWidth = fosMaterial.atlasWidthInv;
        final float inv_atlasHeight = fosMaterial.atlasHeightInv;
        final float xDiffuse = fosMaterial.xDiffuse;
        final float yDiffuse = fosMaterial.yDiffuse;
        final float xNormal = fosMaterial.xNormalMap;
        final float yNormal = fosMaterial.yNormalMap;
        final float width = fosMaterial.width;
        final float height = fosMaterial.height;
        // texture atlas dimensions
        shaderProgram.setUniformf(uniform_atlasWidthInvLocation, inv_atlasWidth);
        shaderProgram.setUniformf(uniform_atlasHeightInvLocation, inv_atlasHeight);
        // diffuse region params
        shaderProgram.setUniformf(uniform_diffuseMapXLocation, xDiffuse);
        shaderProgram.setUniformf(uniform_diffuseMapYLocation, yDiffuse);
        // normal region params
        shaderProgram.setUniformf(uniform_normalMapXLocation, xNormal);
        shaderProgram.setUniformf(uniform_normalMapYLocation, yNormal);
        // dimensions of maps
        shaderProgram.setUniformf(uniform_widthLocation, width);
        shaderProgram.setUniformf(uniform_heightLocation, height);
        // specular + ambient
        shaderProgram.setUniformf(uniform_shineDamperLocation, fosMaterial.shineDamper);
        shaderProgram.setUniformf(uniform_reflectivityLocation, fosMaterial.reflectivity);
        shaderProgram.setUniformf(uniform_ambientLocation, fosMaterial.ambient);
    }

    private void loadEnvironmentsParamsToGPU(final LightingEnvironment renderingLightingEnvironment) {
        if (renderingLightingEnvironment == null) return;
        // load environment point lights
        Array<ComponentPointLight> pointLights = renderingLightingEnvironment.pointLights;
        for (int i = 0; i < LightingEnvironment.MAX_POINT_LIGHTS; i++) {
            if (i < pointLights.size) {
                shaderProgram.setUniformf(uniforms_pointLightsPositionLocations[i], pointLights.get(i).worldPosition);
                shaderProgram.setUniformf(uniforms_pointLightColorLocations[i], pointLights.get(i).color.r, pointLights.get(i).color.g, pointLights.get(i).color.b);
                shaderProgram.setUniformf(uniforms_pointLightsIntensityLocations[i], pointLights.get(i).intensity);
            }
        }
        // load environment directional lights
        // load environment directional lights
        Array<ComponentDirectionalLight> directionalLights = renderingLightingEnvironment.directionalLights;
        for (int i = 0; i < LightingEnvironment.MAX_DIRECTIONAL_LIGHTS; i++) {
            if (i < directionalLights.size) {
                shaderProgram.setUniformf(uniforms_directionalLightsDirectionLocations[i], directionalLights.get(i).worldDirection);
                shaderProgram.setUniformf(uniforms_directionalLightColorLocations[i], directionalLights.get(i).color.r, directionalLights.get(i).color.g, directionalLights.get(i).color.b);
                shaderProgram.setUniformf(uniforms_directionalLightsIntensityLocations[i], directionalLights.get(i).intensity);
            }
        }

        // gamma, exposure factor, etc
        float gamma = Renderer.DEFAULT_GAMMA;
        shaderProgram.setUniformf(uniform_gammaLocation, gamma);
    }

    @Override
    protected void registerUniformLocations() {
        // body transform
        uniform_bodyTransformLocation = shaderProgram.fetchUniformLocation("body_transform", false);
        // render context
        uniform_cameraPositionLocation = shaderProgram.fetchUniformLocation("camera_position", false);
        uniform_cameraTransformLocation = shaderProgram.fetchUniformLocation("camera_transform", false);
        uniform_cameraProjectionLocation = shaderProgram.fetchUniformLocation("camera_projection", false);
        uniform_cameraViewLocation = shaderProgram.fetchUniformLocation("camera_view", false);
        uniform_cameraCombinedLocation = shaderProgram.fetchUniformLocation("camera_combined", false);

        uniform_textureLocation = shaderProgram.fetchUniformLocation("image", false);
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
        // set uv-remapping variables locations
        uniform_atlasWidthInvLocation = shaderProgram.fetchUniformLocation("atlas_width_inv", false);
        uniform_atlasHeightInvLocation = shaderProgram.fetchUniformLocation("atlas_height_inv", false);
        uniform_diffuseMapXLocation = shaderProgram.fetchUniformLocation("diffuse_map.x", false);
        uniform_diffuseMapYLocation = shaderProgram.fetchUniformLocation("diffuse_map.y", false);
        uniform_normalMapXLocation = shaderProgram.fetchUniformLocation("normal_map.x", false);
        uniform_normalMapYLocation = shaderProgram.fetchUniformLocation("normal_map.y", false);
        uniform_widthLocation = shaderProgram.fetchUniformLocation("width", false);
        uniform_heightLocation = shaderProgram.fetchUniformLocation("height", false);
        // set substance variables locations
        uniform_reflectivityLocation = shaderProgram.fetchUniformLocation("reflectivity", false);
        uniform_shineDamperLocation = shaderProgram.fetchUniformLocation("shine_damper", false);
        uniform_ambientLocation = shaderProgram.fetchUniformLocation("ambient", false);
        uniform_gammaLocation = shaderProgram.fetchUniformLocation("gamma", false);
    }
}
