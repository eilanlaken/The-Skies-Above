package com.fos.game.engine.ecs.systems.renderer.shaders.plain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.fos.game.engine.ecs.components.modelinstance_old.ModelInstance;
import com.fos.game.engine.ecs.systems.renderer.materials.base.Material;
import com.fos.game.engine.ecs.systems.renderer.materials.instances.GlowingColorMaterialInstance;
import com.fos.game.engine.ecs.systems.renderer.shaders.base.GameShader;

public class PlainColorBloomShader extends GameShader {

    // current render context - for easy access
    private Camera camera;
    private RenderContext renderContext;
    // uniform variable bindings (locations)
    private int uniform_bodyTransformLocation;
    private int uniform_cameraCombinedLocation;
    private int uniform_rLocation;
    private int uniform_gLocation;
    private int uniform_bLocation;
    private int uniform_intensityLocation;

    public PlainColorBloomShader() {
        super(Gdx.files.classpath("shaders/plain/plainBloomColorVertexShader.glsl").readString(),
                Gdx.files.classpath("shaders/plain/plainBloomColorFragmentShader.glsl").readString());
    }

    @Override
    public void begin(Camera camera, RenderContext renderContext) {
        this.camera = camera;
        this.renderContext = renderContext;
        shaderProgram.bind();
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

    private void loadMaterialParamsToGPU(final Material fosMaterial) {
        final GlowingColorMaterialInstance material = (GlowingColorMaterialInstance) fosMaterial;
        // RGB
        shaderProgram.setUniformf(uniform_rLocation, material.r);
        shaderProgram.setUniformf(uniform_gLocation, material.g);
        shaderProgram.setUniformf(uniform_bLocation, material.b);
        shaderProgram.setUniformf(uniform_intensityLocation, material.intensity);
    }

    @Override
    protected void registerUniformLocations() {
        // body transform
        uniform_bodyTransformLocation = shaderProgram.fetchUniformLocation("body_transform", false);
        // render context
        uniform_cameraCombinedLocation = shaderProgram.fetchUniformLocation("camera_combined", false);

        // set substance variables locations
        uniform_rLocation = shaderProgram.fetchUniformLocation("r", false);
        uniform_gLocation = shaderProgram.fetchUniformLocation("g", false);
        uniform_bLocation = shaderProgram.fetchUniformLocation("b", false);
        uniform_intensityLocation = shaderProgram.fetchUniformLocation("intensity", false);
    }
}
