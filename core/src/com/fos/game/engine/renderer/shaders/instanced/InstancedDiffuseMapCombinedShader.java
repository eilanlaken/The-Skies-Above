package com.fos.game.engine.renderer.shaders.instanced;

public class InstancedDiffuseMapCombinedShader {
    /*
    private ShaderProgram shaderProgram;

    // current render context - for easy access
    private Camera camera;
    private RenderContext renderContext;
    private RenderingEnvironment environment;

    // uniform variable bindings (locations)
    // camera
    private int uniform_cameraPositionLocation;
    private int uniform_cameraProjectionLocation;
    private int uniform_cameraCombinedLocation;

    // lighting - point lights
    private int uniforms_pointLightColorLocations[];
    private int uniforms_pointLightsPositionLocations[];
    private int uniforms_pointLightsIntensityLocations[];
    // lighting - directional lights
    private int uniforms_directionalLightColorLocations[];
    private int uniforms_directionalLightsDirectionLocations[];
    private int uniforms_directionalLightsIntensityLocations[];
    // material
    private int uniform_reflectivityLocation;
    private int uniform_shineDamperLocation;
    private int uniform_ambientLocation;
    // material - texture
    private int uniform_diffuseTextureLocation;
    // uv-remapping
    private int uniform_atlasWidthInvLocation;
    private int uniform_atlasHeightInvLocation;
    private int uniform_diffuseMapXLocation;
    private int uniform_diffuseMapYLocation;
    private int uniform_widthLocation;
    private int uniform_heightLocation;

    public InstancedDiffuseMapCombinedShader() {
        init();
    }

    @Override
    public void init() {
        String vertex = Gdx.files.internal("shaders/instanced/fosInstancedDiffuseMapVertexShader.glsl").readString();
        String fragment = Gdx.files.internal("shaders/instanced/fosInstancedDiffuseMapFragmentShader.glsl").readString();

        shaderProgram = new ShaderProgram(vertex, fragment);
        if (!shaderProgram.isCompiled())
            throw new GdxRuntimeException(shaderProgram.getLog());
        registerUniformLocations();
    }

    @Override
    public int compareTo(Shader other) {
        return 0;
    }

    @Override
    public boolean canRender(Renderable renderable) {
        final CompoundModelInstance modelInstance = (CompoundModelInstance) renderable.userData;
        final FOSMaterial materialInstance = modelInstance.getMaterial(renderable);
        if (materialInstance.getShadingMethod() == ShadingMethod.AnimatedDiffuseMap) return true;
        return false;
    }

    @Override
    public void begin(Camera camera, RenderContext context) {
        this.camera = camera;
        this.renderContext = context;
        shaderProgram.begin();
        shaderProgram.setUniformf(uniform_cameraPositionLocation, camera.position);
        shaderProgram.setUniformMatrix(uniform_cameraProjectionLocation, camera.projection);
        shaderProgram.setUniformMatrix(uniform_cameraCombinedLocation, camera.combined);
        renderContext.setDepthTest(GL20.GL_LEQUAL);
        renderContext.setCullFace(GL20.GL_BACK);
    }

    @Override
    public void render(Renderable renderable) {
        environment = (RenderingEnvironment) renderable.environment;
        loadEnvironmentsParamsToGPU(environment);
        final CompoundModelInstance modelInstance = (CompoundModelInstance) renderable.userData;
        AnimatedDiffuseMapMaterialInstance fosMaterial = (AnimatedDiffuseMapMaterialInstance) modelInstance.getMaterial(renderable);
        loadMaterialParamsToGPU(modelInstance, fosMaterial);
        renderable.meshPart.mesh.render(shaderProgram, GL30.GL_TRIANGLES);
    }

    private void loadMaterialParamsToGPU(final CompoundModelInstance modelInstance, final AnimatedDiffuseMapMaterialInstance fosMaterial) {
        // bind texture
        Texture texture = fosMaterial.spriteSheet.getTextures().first();
        shaderProgram.setUniformi(uniform_diffuseTextureLocation, renderContext.textureBinder.bind(texture));
        // load uv-remapping params
        final float inv_atlasWidth = fosMaterial.atlasWidthInv;
        final float inv_atlasHeight = fosMaterial.atlasHeightInv;
        final float elapsedTime = ((Entity) modelInstance.userData).getElapsedTime();
        final float xDiffuse = fosMaterial.getDiffuseX(elapsedTime);
        final float yDiffuse = fosMaterial.getDiffuseY(elapsedTime);
        final float width = fosMaterial.width;
        final float height = fosMaterial.height;
        // texture atlas dimensions
        shaderProgram.setUniformf(uniform_atlasWidthInvLocation, inv_atlasWidth);
        shaderProgram.setUniformf(uniform_atlasHeightInvLocation, inv_atlasHeight);
        // diffuse region params
        shaderProgram.setUniformf(uniform_diffuseMapXLocation, xDiffuse);
        shaderProgram.setUniformf(uniform_diffuseMapYLocation, yDiffuse);
        // dimensions of maps
        shaderProgram.setUniformf(uniform_widthLocation, width);
        shaderProgram.setUniformf(uniform_heightLocation, height);
        // specular + ambient
        shaderProgram.setUniformf(uniform_shineDamperLocation, fosMaterial.shineDamper);
        shaderProgram.setUniformf(uniform_reflectivityLocation, fosMaterial.reflectivity);
        shaderProgram.setUniformf(uniform_ambientLocation, fosMaterial.ambient);
    }

    private void loadEnvironmentsParamsToGPU(final RenderingEnvironment renderingEnvironment) {
        if (renderingEnvironment == null) return;
        // load environment point lights
        Array<PointLight> pointLights = renderingEnvironment.pointLights;
        for (int i = 0; i < RenderingEnvironment.MAX_POINT_LIGHTS; i++) {
            if (i < pointLights.size) {
                shaderProgram.setUniformf(uniforms_pointLightsPositionLocations[i], pointLights.get(i).position);
                shaderProgram.setUniformf(uniforms_pointLightColorLocations[i], pointLights.get(i).color.r, pointLights.get(i).color.g, pointLights.get(i).color.b);
                shaderProgram.setUniformf(uniforms_pointLightsIntensityLocations[i], pointLights.get(i).intensity);
            }
        }
        // load environment directional lights
        Array<DirectionalLight> directionalLights = renderingEnvironment.directionalLights;
        for (int i = 0; i < RenderingEnvironment.MAX_DIRECTIONAL_LIGHTS; i++) {
            if (i < directionalLights.size) {
                shaderProgram.setUniformf(uniforms_directionalLightsDirectionLocations[i], directionalLights.get(i).direction);
                shaderProgram.setUniformf(uniforms_directionalLightColorLocations[i], directionalLights.get(i).color.r, directionalLights.get(i).color.g, directionalLights.get(i).color.b);
                shaderProgram.setUniformf(uniforms_directionalLightsIntensityLocations[i], directionalLights.get(i).intensity);
            }
        }
    }

    @Override
    public void end() {
        shaderProgram.end();
    }

    @Override
    public void dispose() {
        shaderProgram.dispose();
    }

    private void registerUniformLocations() {
        // render context
        uniform_cameraPositionLocation = shaderProgram.fetchUniformLocation("camera_position", false);
        uniform_cameraProjectionLocation = shaderProgram.fetchUniformLocation("camera_projection", false);
        uniform_cameraCombinedLocation = shaderProgram.fetchUniformLocation("camera_combined", false);

        uniform_diffuseTextureLocation = shaderProgram.fetchUniformLocation("image", false);
        // set point lights locations
        uniforms_pointLightColorLocations = new int[RenderingEnvironment.MAX_POINT_LIGHTS];
        uniforms_pointLightsPositionLocations = new int[RenderingEnvironment.MAX_POINT_LIGHTS];
        uniforms_pointLightsIntensityLocations = new int[RenderingEnvironment.MAX_POINT_LIGHTS];
        for (int i = 0; i < RenderingEnvironment.MAX_POINT_LIGHTS; i++) {
            int lightPositionLocation = shaderProgram.fetchUniformLocation("point_lights[" + i + "].position", false);
            uniforms_pointLightsPositionLocations[i] = lightPositionLocation;
            int lightColorLocation = shaderProgram.fetchUniformLocation("point_lights[" + i + "].color", false);
            uniforms_pointLightColorLocations[i] = lightColorLocation;
            int lightIntensityLocation = shaderProgram.fetchUniformLocation("point_lights[" + i + "].intensity", false);
            uniforms_pointLightsIntensityLocations[i] = lightIntensityLocation;
        }
        // set directional lights locations
        uniforms_directionalLightsDirectionLocations = new int[RenderingEnvironment.MAX_POINT_LIGHTS];
        uniforms_directionalLightColorLocations = new int[RenderingEnvironment.MAX_POINT_LIGHTS];
        uniforms_directionalLightsIntensityLocations = new int[RenderingEnvironment.MAX_POINT_LIGHTS];
        for (int i = 0; i < RenderingEnvironment.MAX_DIRECTIONAL_LIGHTS; i++) {
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
        uniform_widthLocation = shaderProgram.fetchUniformLocation("width", false);
        uniform_heightLocation = shaderProgram.fetchUniformLocation("height", false);
        // set substance variables locations
        uniform_reflectivityLocation = shaderProgram.fetchUniformLocation("reflectivity", false);
        uniform_shineDamperLocation = shaderProgram.fetchUniformLocation("shine_damper", false);
        uniform_ambientLocation = shaderProgram.fetchUniformLocation("ambient", false);
    }
    */

}
