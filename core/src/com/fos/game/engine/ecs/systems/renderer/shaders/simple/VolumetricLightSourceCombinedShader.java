package com.fos.game.engine.ecs.systems.renderer.shaders.simple;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.fos.game.engine.ecs.components.lights.LightingEnvironment;
import com.fos.game.engine.ecs.systems.renderer.materials.base.Material;
import com.fos.game.engine.ecs.systems.renderer.shaders.base.GameShader;
import com.fos.game.engine.ecs.systems.renderer.materials.instances.VolumetricLightSourceMaterialInstance;

public class VolumetricLightSourceCombinedShader extends GameShader {

    private ShaderProgram shaderProgram;

    // current render context - for easy access
    private Camera camera;
    private RenderContext renderContext;
    private LightingEnvironment lightingEnvironment;

    // uniform variable bindings (locations)
    // body transform
    private int uniform_bodyTransformLocation;
    // camera
    private int uniform_cameraPositionLocation;
    private int uniform_cameraTransformLocation;
    private int uniform_cameraProjectionLocation;
    private int uniform_cameraViewLocation;
    private int uniform_cameraCombinedLocation;
    // material
    private int uniform_rLocation;
    private int uniform_gLocation;
    private int uniform_bLocation;

    public VolumetricLightSourceCombinedShader() {
        super(Gdx.files.internal("shaders/simple/fosVolumetricLightSourceVertexShader.glsl").readString(),
                Gdx.files.internal("shaders/simple/fosVolumetricLightSourceFragmentShader.glsl").readString());
    }


    @Override
    public int compareTo(Shader shader) {
        return 0;
    }

    @Override
    public boolean canRender(Renderable renderable) {
        //final CompoundModelInstance modelInstance = (CompoundModelInstance) renderable.userData;
        //final Material materialInstance = modelInstance.getMaterial(renderable);
        //if (materialInstance.getShadingMethod() == ShadingMethod.VolumetricLightSourceColor) return true;
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
        shaderProgram.setUniformMatrix(uniform_bodyTransformLocation, renderable.worldTransform);
        //final CompoundModelInstance modelInstance = (CompoundModelInstance) renderable.userData;

        //final Material fosMaterial = modelInstance.getMaterial(renderable);
        //loadMaterialParamsToGPU(fosMaterial);
        renderable.meshPart.render(shaderProgram);
    }

    private void loadMaterialParamsToGPU(final Material material) {
        final VolumetricLightSourceMaterialInstance materialInstance = (VolumetricLightSourceMaterialInstance) material;
        // RGB
        shaderProgram.setUniformf(uniform_rLocation, materialInstance.r);
        shaderProgram.setUniformf(uniform_gLocation, materialInstance.g);
        shaderProgram.setUniformf(uniform_bLocation, materialInstance.b);
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
        // set substance variables locations
        uniform_rLocation = shaderProgram.fetchUniformLocation("r", false);
        uniform_gLocation = shaderProgram.fetchUniformLocation("g", false);
        uniform_bLocation = shaderProgram.fetchUniformLocation("b", false);
    }


}
