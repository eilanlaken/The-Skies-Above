package com.fos.game.engine.ecs.systems.renderer2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.fos.game.engine.core.graphics.g2d.Physics2DDebugRenderer;
import com.fos.game.engine.core.graphics.g2d.RenderTarget;
import com.fos.game.engine.core.graphics.g2d.SpriteBatch;
import com.fos.game.engine.ecs.components.animations2d.ComponentFrameAnimations2D;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.cameras_old.ComponentCamera2D;
import com.fos.game.engine.ecs.components.lights2d.ComponentLight2D;
import com.fos.game.engine.ecs.components.physics2d.ComponentJoint2D;
import com.fos.game.engine.ecs.components.physics2d.ComponentRigidBody2D;
import com.fos.game.engine.ecs.components.transform2d_old.ComponentTransform2D;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.systems.base.EntitiesProcessor;

import java.util.Map;

public class Renderer2D_test2 implements EntitiesProcessor, Disposable {

    private SpriteBatch spriteBatch;
    private Physics2DDebugRenderer physics2DDebugRenderer;
    private ShapeRenderer shapeRenderer;
    public boolean debugMode;

    private Array<ComponentCamera2D> allCameras;

    public Renderer2D_test2() {
        this.spriteBatch = new SpriteBatch();
        this.physics2DDebugRenderer = new Physics2DDebugRenderer();
        this.shapeRenderer = new ShapeRenderer();
        this.debugMode = Config.DEFAULT.debugMode;
        this.allCameras = new Array<>();
    }

    @Override
    public void process(Array<Entity> entities) {
        this.allCameras.clear();
        for (Entity entity : entities) {
            if ((entity.componentsBitMask & ComponentType.GRAPHICS.bitMask) > 0) {
                ComponentFrameAnimations2D animation = (ComponentFrameAnimations2D) entity.components[ComponentType.GRAPHICS.ordinal()];
                final float delta = Gdx.graphics.getDeltaTime();
                animation.advanceTime(delta);
            }
            if ((entity.componentsBitMask & ComponentType.CAMERA_2D.bitMask) > 0) {
                final ComponentTransform2D transform2D = (ComponentTransform2D) entity.components[ComponentType.TRANSFORM_2D.ordinal()];
                final ComponentCamera2D camera2D = (ComponentCamera2D) entity.components[ComponentType.CAMERA_2D.ordinal()];
                Renderer2DUtils.applyTransform(transform2D, camera2D);
                allCameras.add(camera2D);
            }
            if ((entity.componentsBitMask & ComponentType.GRAPHICS.bitMask) > 0) {
                // TODO: test
                final ComponentTransform2D transform2D = (ComponentTransform2D) entity.components[ComponentType.TRANSFORM_2D.ordinal()];
                final ComponentLight2D light2D = (ComponentLight2D) entity.components[ComponentType.GRAPHICS.ordinal()];
                Renderer2DUtils.applyTransform(transform2D, light2D);
            }
        }

        RuntimeException exception = Renderer2DUtils.checkForCamerasErrors(allCameras);
        if (exception != null) throw new RuntimeException();

        Map<RenderTarget, Array<ComponentCamera2D>> renderTargetCamerasMap = Renderer2DUtils.getRenderTargetCamerasMap(allCameras);
        Map<ComponentCamera2D, Array<Entity>> cameraEntitiesMap = Renderer2DUtils.getCameraEntitiesMap(allCameras, entities);

        for (Map.Entry<RenderTarget, Array<ComponentCamera2D>> renderTargetCameras : renderTargetCamerasMap.entrySet()) {
            final RenderTarget renderTarget = renderTargetCameras.getKey();
            final Array<ComponentCamera2D> camerasForRenderTarget = renderTargetCameras.getValue();
            camerasForRenderTarget.sort(Renderer2DUtils.camerasComparator);
            renderToTarget(renderTarget, camerasForRenderTarget, cameraEntitiesMap);
        }
    }

    private void renderToTarget(final RenderTarget renderTarget, final Array<ComponentCamera2D> cameras, final Map<ComponentCamera2D, Array<Entity>> cameraEntitiesMap) {
        final FrameBuffer primaryFrameBuffer = renderTarget == null ? null : renderTarget.targetFrameBuffer;
        if (primaryFrameBuffer != null) primaryFrameBuffer.begin();
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        // TODO: replace this section
        spriteBatch.begin();
        for (final ComponentCamera2D camera : cameras) {
            spriteBatch.setProjectionMatrix(camera.lens.combined);
            for (Entity entity : cameraEntitiesMap.get(camera)) {
                ComponentFrameAnimations2D animation = (ComponentFrameAnimations2D) entity.components[ComponentType.GRAPHICS.ordinal()];
                if (animation == null || !animation.active) continue;
                ComponentTransform2D transform2D = (ComponentTransform2D) entity.components[ComponentType.TRANSFORM_2D.ordinal()];
                final float delta = Gdx.graphics.getDeltaTime();
                animation.advanceTime(delta);
                TextureAtlas.AtlasRegion atlasRegion = animation.getTextureRegion();
                spriteBatch.draw(atlasRegion, transform2D, animation.size, animation.pixelsPerUnit);
            }
        }
        spriteBatch.end();

        if (debugMode) {
            physics2DDebugRenderer.begin();
            for (final ComponentCamera2D camera : cameras) {
                physics2DDebugRenderer.setProjectionMatrix(camera.lens.combined);
                for (Entity entity : cameraEntitiesMap.get(camera)) {
                    ComponentRigidBody2D rigidBody2D = (ComponentRigidBody2D) entity.components[ComponentType.PHYSICS_2D_BODY.ordinal()];
                    ComponentJoint2D joint2D = (ComponentJoint2D) entity.components[ComponentType.PHYSICS_2D_JOINT.ordinal()];
                    if (rigidBody2D != null) physics2DDebugRenderer.drawBody(rigidBody2D.body);
                    if (joint2D != null) physics2DDebugRenderer.drawJoint(joint2D.joint);
                }
            }
            physics2DDebugRenderer.end();
        }

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

}
