package com.fos.game.engine.ecs.systems.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.fos.game.engine.core.graphics.g2d.Physics2DDebugRenderer;
import com.fos.game.engine.core.graphics.g2d.PolygonSpriteBatch;
import com.fos.game.engine.core.graphics.g2d.ShapeBatch;
import com.fos.game.engine.core.graphics.spine.SkeletonRenderer;
import com.fos.game.engine.core.graphics.spine.SkeletonRendererDebug;
import com.fos.game.engine.ecs.components.rendered2d.ComponentAnimations2D;
import com.fos.game.engine.ecs.components.rendered2d.ComponentBoneAnimations2D;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.camera.ComponentCamera;
import com.fos.game.engine.ecs.components.shape2d.ComponentShapes2D;
import com.fos.game.engine.ecs.components.transform2d.ComponentTransform2D;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.systems.base.EntityContainer;

public class Renderer2D implements Disposable {

    private final EntityContainer container;
    private final PolygonSpriteBatch polygonSpriteBatch;
    private final SkeletonRenderer skeletonRenderer;
    private final SkeletonRendererDebug skeletonRendererDebug;
    private final ShapeBatch shapeBatch;
    private final ShapeRenderer shapeRenderer; // TODO: replace with ShapeDrawer entirely
    private final Physics2DDebugRenderer physics2DDebugRenderer;

    protected Renderer2D(final EntityContainer container) {
        this.container = container;
        this.polygonSpriteBatch = new PolygonSpriteBatch();
        this.skeletonRenderer = new SkeletonRenderer();
        this.skeletonRenderer.setPremultipliedAlpha(true);
        this.skeletonRendererDebug = new SkeletonRendererDebug();
        this.skeletonRendererDebug.setMeshTriangles(false);
        this.skeletonRendererDebug.setRegionAttachments(false);
        this.skeletonRendererDebug.setMeshHull(false);
        this.shapeRenderer = new ShapeRenderer();
        this.physics2DDebugRenderer = new Physics2DDebugRenderer();
        this.shapeBatch = new ShapeBatch(polygonSpriteBatch);
    }

    protected void renderToCameraInternalBuffer(final ComponentCamera camera, final Array<Entity> entities, boolean debugMode) {
        camera.frameBuffer.begin();
        Gdx.gl.glClearColor(1,1,1,0); // TODO: get value from camera
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT); // TODO: get value from camera
        polygonSpriteBatch.begin();
        polygonSpriteBatch.setColor(1,1,1,1);
        polygonSpriteBatch.setProjectionMatrix(camera.lens.combined);
        shapeBatch.update();
        for (Entity entity : entities) {
            ComponentTransform2D transform = (ComponentTransform2D) entity.components[ComponentType.TRANSFORM_2D.ordinal()];
            Component graphics = (Component) entity.components[ComponentType.GRAPHICS.ordinal()];
            if (graphics instanceof ComponentAnimations2D) renderFrameAnimation(transform, (ComponentAnimations2D) graphics);
            if (graphics instanceof ComponentShapes2D) renderShape((ComponentShapes2D) graphics);
            if (graphics instanceof ComponentBoneAnimations2D) renderBoneAnimation((ComponentBoneAnimations2D) graphics);
        }
        polygonSpriteBatch.end();

        // expensive operations, should be enabled only during debugging.
        if (debugMode) {
            physics2DDebugRenderer.begin();
            physics2DDebugRenderer.setProjectionMatrix(camera.lens.combined);
            Array<Body> bodies = container.dynamics2D.getBodies();
            for (Body body : bodies) {
                physics2DDebugRenderer.drawBody(body);
            }
            Array<Joint> joints = container.dynamics2D.getJoints();
            for (Joint joint : joints) {
                physics2DDebugRenderer.drawJoint(joint);
            }
            physics2DDebugRenderer.end();
        }
        camera.frameBuffer.end();
    }

    private void renderFrameAnimation(final ComponentTransform2D transform, final ComponentAnimations2D frameAnimation) {
        if (!frameAnimation.active) return;
        TextureAtlas.AtlasRegion atlasRegion = frameAnimation.getTextureRegion();
        polygonSpriteBatch.setColor(frameAnimation.tint);
        polygonSpriteBatch.draw(atlasRegion, transform.worldX, transform.worldY, transform.worldAngle, transform.worldScaleX, transform.worldScaleY, frameAnimation.size, frameAnimation.pixelsPerUnit);
    }

    private void renderBoneAnimation(final ComponentBoneAnimations2D boneAnimation) {
        skeletonRenderer.draw(polygonSpriteBatch, boneAnimation.skeleton);
    }

    private void renderShape(final ComponentShapes2D shape2D) {
        shape2D.draw(shapeBatch);
    }


    // TODO: test
    private void renderLights(final Camera camera) {
        // implement maybe in the future. Star Contract does not use box2D lights.
    }

    @Override
    public void dispose() {
        polygonSpriteBatch.dispose();
        shapeRenderer.dispose();
        physics2DDebugRenderer.dispose();
    }
}
