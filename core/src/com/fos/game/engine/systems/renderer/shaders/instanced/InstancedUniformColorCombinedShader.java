package com.fos.game.engine.systems.renderer.shaders.instanced;

public class InstancedUniformColorCombinedShader {
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
    private int uniform_cameraViewLocation;
    private int uniform_cameraCombinedLocation;
    // lighting
    private int uniforms_pointLightColorLocations[];
    private int uniforms_pointLightsPositionLocations[];
    private int uniforms_pointLightsIntensityLocations[];
    // material
    private int uniform_reflectivityLocation;
    private int uniform_shineDamperLocation;
    private int uniform_ambientLocation;
    private int uniform_rLocation;
    private int uniform_gLocation;
    private int uniform_bLocation;

    public InstancedUniformColorCombinedShader() {
        init();
    }

    @Override
    public void init() {
        String vertex = Gdx.files.internal("shaders/instanced/fosInstancedUniformColorVertexShader.glsl").readString();
        String fragment = Gdx.files.internal("shaders/instanced/fosInstancedUniformColorFragmentShader.glsl").readString();

        shaderProgram = new ShaderProgram(vertex, fragment);
        if (!shaderProgram.isCompiled())
            throw new GdxRuntimeException(shaderProgram.getLog());
        registerUniformLocations();
    }

    public void updateStaticVariables(Matrix4 cameraProjection) {
        shaderProgram.begin();
        shaderProgram.setUniformMatrix(uniform_cameraProjectionLocation, cameraProjection);
        shaderProgram.end();
    }

    @Override
    public int compareTo(Shader shader) {
        return 0;
    }

    @Override
    public boolean canRender(Renderable renderable) {
        final CompoundModelInstance modelInstance = (CompoundModelInstance) renderable.userData;
        final Material materialInstance = modelInstance.getMaterial(renderable);
        if (materialInstance.getShadingMethod() == ShadingMethod.UniformColor) return true;
        return false;
    }

    @Override
    public void begin(Camera camera, RenderContext renderContext) {
        this.camera = camera;
        this.renderContext = renderContext;
        shaderProgram.begin();
        shaderProgram.setUniformf(uniform_cameraPositionLocation, camera.position);
        shaderProgram.setUniformMatrix(uniform_cameraProjectionLocation, camera.projection);
        shaderProgram.setUniformMatrix(uniform_cameraViewLocation, camera.view);
        shaderProgram.setUniformMatrix(uniform_cameraCombinedLocation, camera.combined);
        renderContext.setDepthTest(GL20.GL_LEQUAL);
        renderContext.setCullFace(GL20.GL_BACK);
    }

    @Override
    public void render(Renderable renderable) {
        environment = (RenderingEnvironment) renderable.environment;
        loadEnvironmentsParamsToGPU(environment);
        final CompoundModelInstance modelInstance = (CompoundModelInstance) renderable.userData;
        final UniformColorMaterialInstance fosMaterial = (UniformColorMaterialInstance) modelInstance.getMaterial(renderable);
        loadMaterialParamsToGPU(fosMaterial);
        renderable.meshPart.mesh.render(shaderProgram, GL30.GL_TRIANGLES);
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

    private void loadEnvironmentsParamsToGPU(final RenderingEnvironment renderingEnvironment) {
        if (renderingEnvironment == null) return;
        // load environment point lights
        Array<ComponentPointLight> pointLights = renderingEnvironment.pointLights;
        for (int i = 0; i < RenderingEnvironment.MAX_POINT_LIGHTS; i++) {
            if (i < pointLights.size) {
                shaderProgram.setUniformf(uniforms_pointLightsPositionLocations[i], pointLights.get(i).position);
                shaderProgram.setUniformf(uniforms_pointLightColorLocations[i], pointLights.get(i).color.r, pointLights.get(i).color.g, pointLights.get(i).color.b);
                shaderProgram.setUniformf(uniforms_pointLightsIntensityLocations[i], pointLights.get(i).intensity);
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
        uniform_cameraViewLocation = shaderProgram.fetchUniformLocation("camera_view", false);
        uniform_cameraCombinedLocation = shaderProgram.fetchUniformLocation("camera_combined", false);
        // set substance variables locations
        uniform_reflectivityLocation = shaderProgram.fetchUniformLocation("reflectivity", false);
        uniform_shineDamperLocation = shaderProgram.fetchUniformLocation("shine_damper", false);
        uniform_ambientLocation = shaderProgram.fetchUniformLocation("ambient", false);
        uniform_rLocation = shaderProgram.fetchUniformLocation("r", false);
        uniform_gLocation = shaderProgram.fetchUniformLocation("g", false);
        uniform_bLocation = shaderProgram.fetchUniformLocation("b", false);
        // set lights locations
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
    }
    */
}
