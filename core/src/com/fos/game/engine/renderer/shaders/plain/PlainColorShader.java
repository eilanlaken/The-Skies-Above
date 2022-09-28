package com.fos.game.engine.renderer.shaders.plain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.fos.game.engine.components.modelinstance.ModelInstance;
import com.fos.game.engine.renderer.materials.base.Material;
import com.fos.game.engine.renderer.materials.instances.PlainColorMaterialInstance;
import com.fos.game.engine.renderer.shaders.base.GameShader;

public class PlainColorShader extends GameShader {

    // current render context - for easy access
    private Camera camera;
    private RenderContext renderContext;

    // uniform variable bindings (locations)
    private int uniform_bodyTransformLocation;
    private int uniform_cameraPositionLocation;
    private int uniform_cameraTransformLocation;
    private int uniform_cameraProjectionLocation;
    private int uniform_cameraViewLocation;
    private int uniform_cameraCombinedLocation;
    private int uniform_rLocation;
    private int uniform_gLocation;
    private int uniform_bLocation;

    public PlainColorShader() {
        super(Gdx.files.internal("shaders/plain/plainColorVertexShader.glsl").readString(),
                Gdx.files.internal("shaders/plain/plainColorFragmentShader.glsl").readString());
    }

    @Override
    public void begin(Camera camera, RenderContext renderContext) {
        this.camera = camera;
        this.renderContext = renderContext;
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
        Material material = modelInstance.getMaterial(renderable);
        loadMaterialParamsToGPU(material);
        renderable.meshPart.render(shaderProgram);
    }

    private void loadMaterialParamsToGPU(final Material material) {
        final PlainColorMaterialInstance materialInstance = (PlainColorMaterialInstance) material;
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
