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
import com.fos.game.engine.renderer.materials.instances.AlbedoMapMaterialInstance;
import com.fos.game.engine.renderer.shaders.base.GameShader;
import com.fos.game.engine.renderer.system.Renderer;

public class PBRAlbedoMapShader extends GameShader {

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
    private int uniform_reflectivityLocation;
    private int uniform_roughnessLocation;
    private int uniform_ambientLocation;
    private int uniform_textureLocation;
    private int uniform_atlasWidthInvLocation;
    private int uniform_atlasHeightInvLocation;
    private int uniform_diffuseMapXLocation;
    private int uniform_diffuseMapYLocation;
    private int uniform_widthLocation;
    private int uniform_heightLocation;
    private int uniform_gammaLocation;

    public PBRAlbedoMapShader() {
        super(Gdx.files.internal("shaders/pbr/pbrAlbedoMapVertexShader.glsl").readString(),
                Gdx.files.internal("shaders/pbr/pbrAlbedoMapFragmentShader.glsl").readString());
        shaderProgram.bind();
        shaderProgram.setUniformi(uniform_textureLocation, 0);
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
        AlbedoMapMaterialInstance fosMaterial = (AlbedoMapMaterialInstance) modelInstance.getMaterial(renderable);
        loadMaterialParamsToGPU(fosMaterial);
        renderable.meshPart.render(shaderProgram);
    }

    private void loadMaterialParamsToGPU(final AlbedoMapMaterialInstance fosMaterial) {
        // bind texture
        Texture texture = fosMaterial.spriteSheet.getTextures().first();
        texture.bind();
        // load uv-remapping params
        final float inv_atlasWidth = fosMaterial.atlasWidthInv;
        final float inv_atlasHeight = fosMaterial.atlasHeightInv;
        final float xAlbedo = fosMaterial.xAlbedo;
        final float yAlbedo = fosMaterial.yAlbedo;
        final float width = fosMaterial.width;
        final float height = fosMaterial.height;
        // texture atlas dimensions
        shaderProgram.setUniformf(uniform_atlasWidthInvLocation, inv_atlasWidth);
        shaderProgram.setUniformf(uniform_atlasHeightInvLocation, inv_atlasHeight);
        // diffuse region params
        shaderProgram.setUniformf(uniform_diffuseMapXLocation, xAlbedo);
        shaderProgram.setUniformf(uniform_diffuseMapYLocation, yAlbedo);
        // dimensions of maps
        shaderProgram.setUniformf(uniform_widthLocation, width);
        shaderProgram.setUniformf(uniform_heightLocation, height);
        // specular + ambient
        shaderProgram.setUniformf(uniform_roughnessLocation, fosMaterial.roughness);
        shaderProgram.setUniformf(uniform_reflectivityLocation, fosMaterial.metallic);
        shaderProgram.setUniformf(uniform_ambientLocation, fosMaterial.ambient);
    }

    private void loadEnvironmentsParamsToGPU(final LightingEnvironment lightingEnvironment) {
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

        float gamma = Renderer.DEFAULT_GAMMA;
        shaderProgram.setUniformf(uniform_gammaLocation, gamma);
    }

    @Override
    protected void registerUniformLocations() {
        // body transform
        uniform_bodyTransformLocation = shaderProgram.fetchUniformLocation("body_transform", true);
        // render context
        uniform_cameraPositionLocation = shaderProgram.fetchUniformLocation("camera_position", true);
        uniform_cameraCombinedLocation = shaderProgram.fetchUniformLocation("camera_combined", true);

        uniform_textureLocation = shaderProgram.fetchUniformLocation("image", true);
        // set point lights locations
        uniforms_pointLightColorLocations = new int[LightingEnvironment.MAX_POINT_LIGHTS];
        uniforms_pointLightsPositionLocations = new int[LightingEnvironment.MAX_POINT_LIGHTS];
        uniforms_pointLightsIntensityLocations = new int[LightingEnvironment.MAX_POINT_LIGHTS];
        for (int i = 0; i < LightingEnvironment.MAX_POINT_LIGHTS; i++) {
            int lightPositionLocation = shaderProgram.fetchUniformLocation("point_lights[" + i + "].position", true);
            uniforms_pointLightsPositionLocations[i] = lightPositionLocation;

            int lightColorLocation = shaderProgram.fetchUniformLocation("point_lights[" + i + "].color", true);
            uniforms_pointLightColorLocations[i] = lightColorLocation;

            int lightIntensityLocation = shaderProgram.fetchUniformLocation("point_lights[" + i + "].intensity", true);
            uniforms_pointLightsIntensityLocations[i] = lightIntensityLocation;
        }
        // set directional lights locations
        uniforms_directionalLightsDirectionLocations = new int[LightingEnvironment.MAX_DIRECTIONAL_LIGHTS];
        uniforms_directionalLightColorLocations = new int[LightingEnvironment.MAX_DIRECTIONAL_LIGHTS];
        uniforms_directionalLightsIntensityLocations = new int[LightingEnvironment.MAX_DIRECTIONAL_LIGHTS];
        for (int i = 0; i < LightingEnvironment.MAX_DIRECTIONAL_LIGHTS; i++) {
            int lightDirectionLocation = shaderProgram.fetchUniformLocation("directional_lights[" + i + "].direction", false);
            uniforms_directionalLightsDirectionLocations[i] = lightDirectionLocation;
            int lightColorLocation = shaderProgram.fetchUniformLocation("directional_lights[" + i + "].color", true);
            uniforms_directionalLightColorLocations[i] = lightColorLocation;
            int lightIntensityLocation = shaderProgram.fetchUniformLocation("directional_lights[" + i + "].intensity", true);
            uniforms_directionalLightsIntensityLocations[i] = lightIntensityLocation;
        }
        // set uv-remapping variables locations
        uniform_atlasWidthInvLocation = shaderProgram.fetchUniformLocation("atlas_width_inv", true);
        uniform_atlasHeightInvLocation = shaderProgram.fetchUniformLocation("atlas_height_inv", true);
        uniform_diffuseMapXLocation = shaderProgram.fetchUniformLocation("diffuse_map.x", true);
        uniform_diffuseMapYLocation = shaderProgram.fetchUniformLocation("diffuse_map.y", true);
        uniform_widthLocation = shaderProgram.fetchUniformLocation("width", true);
        uniform_heightLocation = shaderProgram.fetchUniformLocation("height", true);
        // set substance variables locations
        uniform_reflectivityLocation = shaderProgram.fetchUniformLocation("metallic", true);
        uniform_roughnessLocation = shaderProgram.fetchUniformLocation("roughness", true);
        uniform_ambientLocation = shaderProgram.fetchUniformLocation("ambient", false);
        uniform_gammaLocation = shaderProgram.fetchUniformLocation("gamma", false);
    }
}
