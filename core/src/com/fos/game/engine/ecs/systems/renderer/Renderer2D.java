package com.fos.game.engine.ecs.systems.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.core.graphics.g2d.Physics2DDebugRenderer;
import com.fos.game.engine.core.graphics.g2d.PolygonSpriteBatch;
import com.fos.game.engine.core.graphics.spine.SkeletonRenderer;
import com.fos.game.engine.core.graphics.spine.SkeletonRendererDebug;
import com.fos.game.engine.ecs.components.animations2d.ComponentBoneAnimations2D;
import com.fos.game.engine.ecs.components.animations2d.ComponentFrameAnimations2D;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.camera.ComponentCamera;
import com.fos.game.engine.ecs.components.lights2d.ComponentLight2D;
import com.fos.game.engine.ecs.components.physics2d.ComponentJoint2D;
import com.fos.game.engine.ecs.components.physics2d.ComponentRigidBody2D;
import com.fos.game.engine.ecs.components.transform.ComponentTransform;
import com.fos.game.engine.ecs.entities.Entity;

public class Renderer2D {

    private final PolygonSpriteBatch polygonSpriteBatch;
    private final SkeletonRenderer skeletonRenderer;
    private final SkeletonRendererDebug skeletonRendererDebug;
    private final Physics2DDebugRenderer physics2DDebugRenderer;

    protected Renderer2D() {
        this.polygonSpriteBatch = new PolygonSpriteBatch();
        this.skeletonRenderer = new SkeletonRenderer();
        this.skeletonRenderer.setPremultipliedAlpha(true);
        this.skeletonRendererDebug = new SkeletonRendererDebug();
        this.skeletonRendererDebug.setMeshTriangles(false);
        this.skeletonRendererDebug.setRegionAttachments(false);
        this.skeletonRendererDebug.setMeshHull(false);
        this.physics2DDebugRenderer = new Physics2DDebugRenderer();
    }

    protected void renderToCameraInternalBuffer(final ComponentCamera camera, final Array<Entity> entities, boolean debugMode) {
        camera.frameBuffer.begin();
        Gdx.gl.glClearColor(0,0,0,0); // TODO: get value from camera
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT); // TODO: get value from camera
        polygonSpriteBatch.begin();
        polygonSpriteBatch.setColor(1,1,1,1);
        polygonSpriteBatch.setProjectionMatrix(camera.lens.combined);
        for (Entity entity : entities) {
            ComponentTransform transform = (ComponentTransform) entity.components[ComponentType.TRANSFORM_2D.ordinal()];
            Component graphics = (Component) entity.components[ComponentType.GRAPHICS.ordinal()];
            if (graphics instanceof ComponentFrameAnimations2D) renderFrameAnimation(transform, (ComponentFrameAnimations2D) graphics);
            if (graphics instanceof ComponentBoneAnimations2D) renderBoneAnimation((ComponentBoneAnimations2D) graphics);
            if (graphics instanceof ComponentLight2D) renderLight(); // TODO: implement
        }
        polygonSpriteBatch.end();

        if (debugMode) {
            physics2DDebugRenderer.begin();
            physics2DDebugRenderer.setProjectionMatrix(camera.lens.combined);
            for (Entity entity : entities) {
                ComponentRigidBody2D rigidBody2D = (ComponentRigidBody2D) entity.components[ComponentType.PHYSICS_2D_BODY.ordinal()];
                ComponentJoint2D joint2D = (ComponentJoint2D) entity.components[ComponentType.PHYSICS_2D_JOINT.ordinal()];
                if (rigidBody2D != null) physics2DDebugRenderer.drawBody(rigidBody2D.body);
                if (joint2D != null) physics2DDebugRenderer.drawJoint(joint2D.joint);
            }
            physics2DDebugRenderer.end();
        }

        camera.frameBuffer.end();
    }

    private void renderFrameAnimation(final ComponentTransform transform, final ComponentFrameAnimations2D frameAnimation) {
        if (!frameAnimation.active) return;
        TextureAtlas.AtlasRegion atlasRegion = frameAnimation.getTextureRegion();
        polygonSpriteBatch.setColor(frameAnimation.tint);
        polygonSpriteBatch.draw(atlasRegion, transform.position.x, transform.position.y, transform.rotation.getAngleAround(0,0,1), transform.scale.x, transform.scale.y, frameAnimation.size, frameAnimation.pixelsPerUnit);
    }

    private void renderBoneAnimation(final ComponentBoneAnimations2D boneAnimation) {
        skeletonRenderer.draw(polygonSpriteBatch, boneAnimation.skeleton);
    }


    private void renderLight() {

    }
}
