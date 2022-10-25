package com.fos.game.engine.ecs.systems.renderer;

// TODO: implement.

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.fos.game.engine.core.graphics.g2d.Physics2DDebugRenderer;
import com.fos.game.engine.core.graphics.g2d.RenderTarget;
import com.fos.game.engine.core.graphics.g2d.SpriteBatch;
import com.fos.game.engine.ecs.components.animations2d.ComponentAnimations2D;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.camera.ComponentCamera;
import com.fos.game.engine.ecs.components.lights2d.ComponentLight2D;
import com.fos.game.engine.ecs.components.transform.ComponentTransform;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.systems.base.EntitiesProcessor;
import com.fos.game.engine.ecs.systems.renderer2d.Config;

import java.util.Map;

/***
 * The @Renderer is where the scene is composed from all the cameras_old back buffers.
 * The 2d rendering and 3d rendering tasks are delegated to the Renderer2D and Renderer3D,
 * respectively. Then the final scene is composed.
 */
public class Renderer implements EntitiesProcessor, Disposable {

    private Renderer2D renderer2D;
    private Renderer3D renderer3D;
    private SpriteBatch spriteBatch;
    private ModelBatch modelBatch;
    private Physics2DDebugRenderer physics2DDebugRenderer;
    private ShapeRenderer shapeRenderer;
    public boolean debugMode;

    // state management
    private Array<ComponentCamera> allCameras;

    public Renderer() {
        this.spriteBatch = new SpriteBatch();
        this.modelBatch = new ModelBatch(new ShaderProvider(), new ModelInstanceSorter());
        this.renderer2D = new Renderer2D();
        this.renderer3D = new Renderer3D();
        this.physics2DDebugRenderer = new Physics2DDebugRenderer();
        this.shapeRenderer = new ShapeRenderer();
        this.debugMode = Config.DEFAULT.debugMode;
        this.allCameras = new Array<>();
    }

    @Override
    public void process(Array<Entity> entities) {
        this.allCameras.clear();
        for (Entity entity : entities) {
            if ((entity.componentsBitMask & ComponentType.ANIMATIONS_2D.bitMask) > 0) {
                ComponentAnimations2D animation = (ComponentAnimations2D) entity.components[ComponentType.ANIMATIONS_2D.ordinal()];
                final float delta = Gdx.graphics.getDeltaTime();
                animation.advanceTime(delta);
            }
            if ((entity.componentsBitMask & ComponentType.CAMERA.bitMask) > 0) {
                final ComponentTransform transform = (ComponentTransform) entity.components[ComponentType.TRANSFORM_2D.ordinal()];
                final ComponentCamera camera = (ComponentCamera) entity.components[ComponentType.CAMERA_2D.ordinal()];
                RendererUtils.applyTransform(transform, camera);
                allCameras.add(camera);
            }
            if ((entity.componentsBitMask & ComponentType.LIGHT_2D.bitMask) > 0) {
                final ComponentTransform transform = (ComponentTransform) entity.components[ComponentType.TRANSFORM_2D.ordinal()];
                final ComponentLight2D light2D = (ComponentLight2D) entity.components[ComponentType.LIGHT_2D.ordinal()];
                RendererUtils.applyTransform(transform, light2D);
            }
        }

        // render to cameras internal buffers using Renderer2D and Renderer3D
        Map<ComponentCamera, Array<Entity>> cameraEntitiesMap = RendererUtils.getCameraEntitiesMap(allCameras, entities);
        for (ComponentCamera camera : allCameras) {
            Camera lens = camera.lens;
            if (lens instanceof OrthographicCamera) renderer2D.renderToCameraInternalBuffer(spriteBatch, camera, cameraEntitiesMap.get(camera), debugMode);
            if (lens instanceof PerspectiveCamera) renderer3D.renderToCameraInternalBuffer(modelBatch, camera, cameraEntitiesMap.get(camera), debugMode);
        }

        // compose scene
        Map<RenderTarget, Array<ComponentCamera>> renderTargetCamerasMap = RendererUtils.getRenderTargetCamerasMap(allCameras);
        for (Map.Entry<RenderTarget, Array<ComponentCamera>> renderTargetCameras : renderTargetCamerasMap.entrySet()) {
            renderToTarget(renderTargetCameras.getKey(), renderTargetCameras.getValue());
        }
    }

    protected void renderToTarget(final RenderTarget renderTarget, final Array<ComponentCamera> renderTargetCameras) {
        renderTargetCameras.sort(RendererUtils.camerasComparator);
        final FrameBuffer frameBuffer = renderTarget == null ? null : renderTarget.targetFrameBuffer;
        if (frameBuffer != null) frameBuffer.begin();
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        for (ComponentCamera camera : renderTargetCameras) {
            spriteBatch.setShader(camera.postProcessingEffect); // TODO: <- replace with texture region batch
            spriteBatch.begin();
            TextureRegion sceneRegion = new TextureRegion(camera.frameBuffer.getTextureAttachments().get(0));
            sceneRegion.flip(false, true);
            spriteBatch.draw(sceneRegion, -Gdx.graphics.getWidth()/2f, -Gdx.graphics.getHeight()/2f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            spriteBatch.end();
        }
        spriteBatch.setShader(null);
        if (frameBuffer != null) frameBuffer.end();
    }

    @Override
    public boolean shouldProcess(Entity entity) {
        if ((entity.componentsBitMask & RendererUtils.RENDERER_ENTITY) > 0) return true;
        if (debugMode && (entity.componentsBitMask & ComponentType.PHYSICS_2D_BODY.bitMask) > 0) return true;
        if (debugMode && (entity.componentsBitMask & ComponentType.PHYSICS_2D_JOINT.bitMask) > 0) return true;
        return false;
    }

    @Override
    public void dispose() {
        this.spriteBatch.dispose();
        this.physics2DDebugRenderer.dispose();
        this.shapeRenderer.dispose();
    }

    public void config(final Config config) {
        this.debugMode = config.debugMode;
    }
}