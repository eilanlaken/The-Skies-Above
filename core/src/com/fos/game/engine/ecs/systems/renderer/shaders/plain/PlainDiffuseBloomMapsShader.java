package com.fos.game.engine.ecs.systems.renderer.shaders.plain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.fos.game.engine.ecs.components.modelinstance.ModelInstance;
import com.fos.game.engine.ecs.systems.renderer.materials.instances.PlainDiffuseBloomMapsMaterialInstance;
import com.fos.game.engine.ecs.systems.renderer.shaders.base.GameShader;

public class PlainDiffuseBloomMapsShader extends GameShader {

    // current render context - for easy access
    private Camera camera;
    private RenderContext renderContext;

    // uniform variable bindings (locations)
    // body transform
    private int uniform_bodyTransformLocation;
    // camera
    private int uniform_cameraPositionLocation;
    private int uniform_cameraProjectionLocation;
    private int uniform_cameraViewLocation;
    private int uniform_cameraCombinedLocation;

    // material - texture
    private int uniform_textureLocation;
    // uv-remapping
    private int uniform_atlasWidthInvLocation;
    private int uniform_atlasHeightInvLocation;
    private int uniform_diffuseMapXLocation;
    private int uniform_diffuseMapYLocation;
    private int uniform_bloomMapXLocation;
    private int uniform_bloomMapYLocation;
    private int uniform_widthLocation;
    private int uniform_heightLocation;
    private int uniform_intensity;

    public PlainDiffuseBloomMapsShader() {
        super(Gdx.files.internal("shaders/plain/plainDiffuseBloomMapsVertexShader.glsl").readString(),
                Gdx.files.internal("shaders/plain/plainDiffuseBloomMapsFragmentShader.glsl").readString());
        shaderProgram.bind();
        shaderProgram.setUniformi(uniform_textureLocation, 0);
    }

    @Override
    public void begin(Camera camera, RenderContext context) {
        this.camera = camera;
        this.renderContext = context;
        renderContext.setDepthTest(GL20.GL_LEQUAL);
        renderContext.setCullFace(GL20.GL_BACK);
        shaderProgram.bind();
        shaderProgram.setUniformf(uniform_cameraPositionLocation, camera.position);
        shaderProgram.setUniformMatrix(uniform_cameraProjectionLocation, camera.projection);
        shaderProgram.setUniformMatrix(uniform_cameraViewLocation, camera.view);
        shaderProgram.setUniformMatrix(uniform_cameraCombinedLocation, camera.combined);
    }

    @Override
    public void render(Renderable renderable) {
        final ModelInstance modelInstance = (ModelInstance) renderable.userData;
        shaderProgram.setUniformMatrix(uniform_bodyTransformLocation, renderable.worldTransform);
        PlainDiffuseBloomMapsMaterialInstance fosMaterial = (PlainDiffuseBloomMapsMaterialInstance) modelInstance.getMaterial(renderable);
        loadMaterialParamsToGPU(fosMaterial);
        renderable.meshPart.render(shaderProgram);
    }

    private void loadMaterialParamsToGPU(final PlainDiffuseBloomMapsMaterialInstance fosMaterial) {
        // bind texture
        Texture texture = fosMaterial.spriteSheet.getTextures().first();
        texture.bind();

        // load uv-remapping params
        final float inv_atlasWidth = fosMaterial.atlasWidthInv;
        final float inv_atlasHeight = fosMaterial.atlasHeightInv;
        final float xDiffuse = fosMaterial.getDiffuseX(0);
        final float yDiffuse = fosMaterial.getDiffuseY(0);
        final float xBloom = fosMaterial.getBloomX(0);
        final float yBloom = fosMaterial.getBloomY(0);
        final float width = fosMaterial.width;
        final float height = fosMaterial.height;
        final float intensity = fosMaterial.intensity;
        // texture atlas dimensions
        shaderProgram.setUniformf(uniform_atlasWidthInvLocation, inv_atlasWidth);
        shaderProgram.setUniformf(uniform_atlasHeightInvLocation, inv_atlasHeight);
        shaderProgram.setUniformf(uniform_diffuseMapXLocation, xDiffuse);
        shaderProgram.setUniformf(uniform_diffuseMapYLocation, yDiffuse);
        shaderProgram.setUniformf(uniform_bloomMapXLocation, xBloom);
        shaderProgram.setUniformf(uniform_bloomMapYLocation, yBloom);
        shaderProgram.setUniformf(uniform_diffuseMapYLocation, yDiffuse);
        shaderProgram.setUniformf(uniform_widthLocation, width);
        shaderProgram.setUniformf(uniform_heightLocation, height);
        shaderProgram.setUniformf(uniform_intensity, intensity);
    }


    @Override
    protected void registerUniformLocations() {
        // body transform
        uniform_bodyTransformLocation = shaderProgram.fetchUniformLocation("body_transform", false);
        // render context
        uniform_cameraPositionLocation = shaderProgram.fetchUniformLocation("camera_position", false);
        uniform_cameraProjectionLocation = shaderProgram.fetchUniformLocation("camera_projection", false);
        uniform_cameraViewLocation = shaderProgram.fetchUniformLocation("camera_view", false);
        uniform_cameraCombinedLocation = shaderProgram.fetchUniformLocation("camera_combined", false);
        uniform_textureLocation = shaderProgram.fetchUniformLocation("image", false);
        // set uv-remapping variables locations
        uniform_atlasWidthInvLocation = shaderProgram.fetchUniformLocation("atlas_width_inv", false);
        uniform_atlasHeightInvLocation = shaderProgram.fetchUniformLocation("atlas_height_inv", false);
        uniform_diffuseMapXLocation = shaderProgram.fetchUniformLocation("diffuse_map.x", false);
        uniform_diffuseMapYLocation = shaderProgram.fetchUniformLocation("diffuse_map.y", false);
        uniform_bloomMapXLocation = shaderProgram.fetchUniformLocation("bloom_map.x", false);
        uniform_bloomMapYLocation = shaderProgram.fetchUniformLocation("bloom_map.y", false);
        uniform_widthLocation = shaderProgram.fetchUniformLocation("width", false);
        uniform_heightLocation = shaderProgram.fetchUniformLocation("height", false);
        uniform_intensity = shaderProgram.fetchUniformLocation("intensity", false);
    }

}