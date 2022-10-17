package com.fos.game.engine.ecs.systems.renderer2d;

import com.badlogic.gdx.Gdx;
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

        Renderer2DUtils.renderToCamerasInternalBuffer(spriteBatch, allCameras, cameraEntitiesMap);
        for (Map.Entry<RenderTarget, Array<ComponentCamera2D>> renderTargetCameras : renderTargetCamerasMap.entrySet()) {
            Renderer2DUtils.renderToTarget(spriteBatch, renderTargetCameras.getKey(), renderTargetCameras.getValue());
        }
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