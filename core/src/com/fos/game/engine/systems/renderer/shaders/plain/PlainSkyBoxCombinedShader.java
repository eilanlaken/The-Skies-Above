package com.fos.game.engine.systems.renderer.shaders.plain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.fos.game.engine.components.modelinstance.ModelInstance;
import com.fos.game.engine.systems.renderer.materials.base.Material;
import com.fos.game.engine.systems.renderer.shaders.base.ShadingMethod;
import com.fos.game.engine.systems.renderer.materials.instances.AnimatedSkyBoxSideMaterialInstance;

@Deprecated
// TODO: fix
public class PlainSkyBoxCombinedShader implements Shader {

    private ShaderProgram shaderProgram;

    private Camera camera;
    private Matrix4 skyBoxTransform;
    private PerspectiveCamera skyBoxCamera;
    private RenderContext renderContext;
    private Matrix4 combined = new Matrix4().idt();

    // transform locations
    private int uniform_skyBoxTransformLocation;
    private int uniform_cameraCombinedLocation;
    // material - texture
    private int uniform_textureLocation;
    // uv-remapping
    private int uniform_diffuseMapXLocation;
    private int uniform_diffuseMapYLocation;
    private int uniform_diffuseMapWidthLocation;
    private int uniform_diffuseMapHeightLocation;
    private int uniform_atlasWidthInvLocation;
    private int uniform_atlasHeightInvLocation;

    public PlainSkyBoxCombinedShader() {
        init();
    }

    @Override
    public void init() {
        String vertex = Gdx.files.internal("shaders/plain/plainSkyBoxVertexShader.glsl").readString();
        String fragment = Gdx.files.internal("shaders/plain/plainSkyBoxFragmentShader.glsl").readString();

        shaderProgram = new ShaderProgram(vertex, fragment);
        if (!shaderProgram.isCompiled())
            throw new GdxRuntimeException(shaderProgram.getLog());
        registerUniformLocations();

        // init skyboxCamera
        skyBoxCamera = new PerspectiveCamera();
        skyBoxCamera.fieldOfView = 75;
        skyBoxCamera.viewportWidth = Gdx.graphics.getWidth();
        skyBoxCamera.viewportHeight = Gdx.graphics.getHeight();
        skyBoxCamera.near = 0.1f;
        skyBoxCamera.far = 28000;
        skyBoxCamera.update();

        skyBoxTransform = new Matrix4().idt();
        shaderProgram.bind();
        shaderProgram.setUniformi(uniform_textureLocation, 0);
    }

    @Override
    public int compareTo(Shader other) {
        return 0;
    }

    @Override
    public boolean canRender(Renderable renderable) {
        final ModelInstance modelInstance = (ModelInstance) renderable.userData;
        final Material materialInstance = modelInstance.getMaterial(renderable);
        if (materialInstance.getShadingMethod() == ShadingMethod.PlainSkyBox) return true;
        return false;
    }

    @Override
    public void begin(Camera camera, RenderContext context) {
        this.camera = camera;
        this.renderContext = context;
        this.renderContext.setDepthTest(GL20.GL_LEQUAL);
        shaderProgram.begin();
        loadTransformsToGPU();
    }

    @Override
    public void render(Renderable renderable) {
        final ModelInstance modelInstance = (ModelInstance) renderable.userData;
        AnimatedSkyBoxSideMaterialInstance fosMaterial = (AnimatedSkyBoxSideMaterialInstance) modelInstance.getMaterial(renderable);
        loadMaterialParamsToGPU(fosMaterial);
        renderable.meshPart.render(shaderProgram);
    }

    private void loadTransformsToGPU() {
        combined.set(skyBoxCamera.projection);
        combined.mul(camera.view);
        shaderProgram.setUniformMatrix(uniform_cameraCombinedLocation, combined);
        skyBoxTransform.val[12] = camera.position.x;
        skyBoxTransform.val[13] = camera.position.y;
        skyBoxTransform.val[14] = camera.position.z;
        shaderProgram.setUniformMatrix(uniform_skyBoxTransformLocation, skyBoxTransform);
    }

    private void loadMaterialParamsToGPU(final AnimatedSkyBoxSideMaterialInstance fosMaterial) {
        final float elapsedTime = 0;
        Texture texture = fosMaterial.spriteSheet.getTextures().first();
        texture.bind();

        // load uv-remapping params
        final float inv_atlasWidth = fosMaterial.atlasWidthInv;
        final float inv_atlasHeight = fosMaterial.atlasHeightInv;
        final float xDiffuse = fosMaterial.getDiffuseX(elapsedTime);
        final float yDiffuse = fosMaterial.getDiffuseY(elapsedTime);
        final float widthDiffuse = fosMaterial.getWidth(elapsedTime);
        final float heightDiffuse = fosMaterial.getHeight(elapsedTime);
        // texture atlas dimensions
        shaderProgram.setUniformf(uniform_atlasWidthInvLocation, inv_atlasWidth);
        shaderProgram.setUniformf(uniform_atlasHeightInvLocation, inv_atlasHeight);
        // diffuse region params
        shaderProgram.setUniformf(uniform_diffuseMapXLocation, xDiffuse);
        shaderProgram.setUniformf(uniform_diffuseMapYLocation, yDiffuse);
        // dimensions of maps
        shaderProgram.setUniformf(uniform_diffuseMapWidthLocation, widthDiffuse);
        shaderProgram.setUniformf(uniform_diffuseMapHeightLocation, heightDiffuse);
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
        // body transform and camera
        uniform_skyBoxTransformLocation = shaderProgram.fetchUniformLocation("skybox_transform", false);
        uniform_cameraCombinedLocation = shaderProgram.fetchUniformLocation("camera_combined", false);
        // texture
        uniform_textureLocation = shaderProgram.fetchUniformLocation("image", false);
        // set uv-remapping variables locations
        uniform_atlasWidthInvLocation = shaderProgram.fetchUniformLocation("atlas_width_inv", false);
        uniform_atlasHeightInvLocation = shaderProgram.fetchUniformLocation("atlas_height_inv", false);
        uniform_diffuseMapXLocation = shaderProgram.fetchUniformLocation("diffuse_map.x", false);
        uniform_diffuseMapYLocation = shaderProgram.fetchUniformLocation("diffuse_map.y", false);
        uniform_diffuseMapWidthLocation = shaderProgram.fetchUniformLocation("diffuse_map.width", false);
        uniform_diffuseMapHeightLocation = shaderProgram.fetchUniformLocation("diffuse_map.height", false);
    }

}
