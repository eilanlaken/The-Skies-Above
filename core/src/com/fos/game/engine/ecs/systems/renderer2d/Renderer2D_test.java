package com.fos.game.engine.ecs.systems.renderer2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.fos.game.engine.core.graphics.g2d.Physics2DDebugRenderer;
import com.fos.game.engine.core.graphics.g2d.RenderTarget;
import com.fos.game.engine.core.graphics.g2d.SpriteBatch;
import com.fos.game.engine.ecs.components.animations2d.ComponentAnimations2D;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.cameras.ComponentCamera2D;
import com.fos.game.engine.ecs.components.lights2d.ComponentLight2D;
import com.fos.game.engine.ecs.components.transform2d.ComponentTransform2D;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.systems.base.EntitiesProcessor;
import com.fos.game.engine.ecs.systems.base.SystemConfig;

import java.util.Map;

public class Renderer2D_test implements EntitiesProcessor, Disposable {

    private SpriteBatch spriteBatch;
    private Physics2DDebugRenderer physics2DDebugRenderer;
    private ShapeRenderer shapeRenderer;
    public boolean debugMode;

    private Array<ComponentCamera2D> allCameras;

    public Renderer2D_test() {
        this.spriteBatch = new SpriteBatch();
        this.physics2DDebugRenderer = new Physics2DDebugRenderer();
        this.shapeRenderer = new ShapeRenderer();
        this.debugMode = Renderer2D_test.Config.DEFAULT.debugMode;
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
            if ((entity.componentsBitMask & ComponentType.CAMERA_2D.bitMask) > 0) {
                final ComponentTransform2D transform2D = (ComponentTransform2D) entity.components[ComponentType.TRANSFORM_2D.ordinal()];
                final ComponentCamera2D camera2D = (ComponentCamera2D) entity.components[ComponentType.CAMERA_2D.ordinal()];
                Renderer2DUtils.applyTransform(transform2D, camera2D);
                allCameras.add(camera2D);
            }
            if ((entity.componentsBitMask & ComponentType.LIGHT_2D.bitMask) > 0) {
                // TODO: test
                final ComponentTransform2D transform2D = (ComponentTransform2D) entity.components[ComponentType.TRANSFORM_2D.ordinal()];
                final ComponentLight2D light2D = (ComponentLight2D) entity.components[ComponentType.LIGHT_2D.ordinal()];
                Renderer2DUtils.applyTransform(transform2D, light2D);
            }
        }

        Map<RenderTarget, Array<ComponentCamera2D>> renderTargetCamerasMap = Renderer2DUtils.getRenderTargetCamerasMap(allCameras);
        Map<ComponentCamera2D, Array<Entity>> cameraEntitiesMap = Renderer2DUtils.getCameraEntitiesMap(allCameras, entities);

        renderToCamerasInternalBuffer(allCameras, cameraEntitiesMap);
        for (Map.Entry<RenderTarget, Array<ComponentCamera2D>> renderTargetCameras : renderTargetCamerasMap.entrySet()) {
            renderToTarget(renderTargetCameras.getKey(), renderTargetCameras.getValue());
        }
    }

    protected void renderToCamerasInternalBuffer(final Array<ComponentCamera2D> allCameras, final Map<ComponentCamera2D, Array<Entity>> cameraEntitiesMap) {
        for (ComponentCamera2D camera : allCameras) {
            camera.frameBuffer.begin();
            Gdx.gl.glClearColor(0,0,0,0); // TODO: get value from camera
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT); // TODO: get value from camera
            spriteBatch.begin();
            spriteBatch.setProjectionMatrix(camera.lens.combined);
            for (Entity entity : cameraEntitiesMap.get(camera)) {
                ComponentAnimations2D animation = (ComponentAnimations2D) entity.components[ComponentType.ANIMATIONS_2D.ordinal()];
                if (animation == null || !animation.active) continue;
                ComponentTransform2D transform2D = (ComponentTransform2D) entity.components[ComponentType.TRANSFORM_2D.ordinal()];
                final float delta = Gdx.graphics.getDeltaTime();
                animation.advanceTime(delta);
                TextureAtlas.AtlasRegion atlasRegion = animation.getTextureRegion();
                spriteBatch.draw(atlasRegion, transform2D, camera.pixelsPerMeterX, camera.pixelsPerMeterY);
            }
            spriteBatch.end();

            // TODO: add physics debug renderer.
            

            camera.frameBuffer.end();
        }
    }

    // TODO: <- replace with texture region batch
    protected void renderToTarget(final RenderTarget renderTarget, final Array<ComponentCamera2D> renderTargetCameras) {
        renderTargetCameras.sort(Renderer2DUtils.camerasComparator);
        final FrameBuffer primaryFrameBuffer = renderTarget == null ? null : renderTarget.targetFrameBuffer;
        if (primaryFrameBuffer != null) primaryFrameBuffer.begin();
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        for (ComponentCamera2D camera : renderTargetCameras) {
            spriteBatch.setShader(camera.postProcessingEffect); // TODO: <- replace with texture region batch
            spriteBatch.begin();
            TextureRegion sceneRegion = new TextureRegion(camera.frameBuffer.getTextureAttachments().get(0));
            sceneRegion.flip(false, true);
            spriteBatch.draw(sceneRegion, -Gdx.graphics.getWidth()/2f, -Gdx.graphics.getHeight()/2f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            spriteBatch.end();
        }
        spriteBatch.setShader(null);
        if (primaryFrameBuffer != null) primaryFrameBuffer.end();
    }

    @Override
    public boolean shouldProcess(Entity entity) {
        if ((entity.componentsBitMask & Renderer2DUtils.ATTACHED_GRAPHICS_2D_COMPONENT) > 0) return true;
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

    public static final class Config extends SystemConfig {
        public static final Config DEFAULT = new Config(true, 1f / 2.2f);
        public final boolean debugMode;
        public final float gamma;
        public Config(final boolean debugMode, final float gamma) {
            this.debugMode = debugMode;
            this.gamma = gamma;
        }
    }

}
