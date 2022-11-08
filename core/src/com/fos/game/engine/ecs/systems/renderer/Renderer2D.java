package com.fos.game.engine.ecs.systems.renderer;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.fos.game.engine.core.graphics.g2d.Physics2DDebugRenderer;
import com.fos.game.engine.core.graphics.g2d.PolygonSpriteBatch;
import com.fos.game.engine.core.graphics.spine.SkeletonRenderer;
import com.fos.game.engine.core.graphics.spine.SkeletonRendererDebug;
import com.fos.game.engine.ecs.components.animations2d.ComponentBoneAnimations2D;
import com.fos.game.engine.ecs.components.animations2d.ComponentAnimations2D;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.camera.ComponentCamera;
import com.fos.game.engine.ecs.components.lights2d.ComponentLight2D;
import com.fos.game.engine.ecs.components.lights2d.LightType;
import com.fos.game.engine.ecs.components.physics2d.ComponentJoint2D;
import com.fos.game.engine.ecs.components.physics2d.ComponentRigidBody2D;
import com.fos.game.engine.ecs.components.transform.ComponentTransform2D;
import com.fos.game.engine.ecs.entities.Entity;

public class Renderer2D implements Disposable {

    private final PolygonSpriteBatch polygonSpriteBatch;
    private final SkeletonRenderer skeletonRenderer;
    private final SkeletonRendererDebug skeletonRendererDebug;
    private final ShapeRenderer shapeRenderer;
    private final Physics2DDebugRenderer physics2DDebugRenderer;

    private final Array<ComponentLight2D> lights = new Array<>();

    protected Renderer2D() {
        this.polygonSpriteBatch = new PolygonSpriteBatch();
        this.skeletonRenderer = new SkeletonRenderer();
        this.skeletonRenderer.setPremultipliedAlpha(true);
        this.skeletonRendererDebug = new SkeletonRendererDebug();
        this.skeletonRendererDebug.setMeshTriangles(false);
        this.skeletonRendererDebug.setRegionAttachments(false);
        this.skeletonRendererDebug.setMeshHull(false);
        this.shapeRenderer = new ShapeRenderer();
        this.physics2DDebugRenderer = new Physics2DDebugRenderer();
    }

    protected void renderToCameraInternalBuffer(final ComponentCamera camera, final Array<Entity> entities, boolean debugMode) {
        lights.clear();
        camera.frameBuffer.begin();
        Gdx.gl.glClearColor(0,0,0,0); // TODO: get value from camera
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT); // TODO: get value from camera
        polygonSpriteBatch.begin();
        polygonSpriteBatch.setColor(1,1,1,1);
        polygonSpriteBatch.setProjectionMatrix(camera.lens.combined);
        for (Entity entity : entities) {
            ComponentTransform2D transform = (ComponentTransform2D) entity.components[ComponentType.TRANSFORM.ordinal()];
            Component graphics = (Component) entity.components[ComponentType.GRAPHICS.ordinal()];
            if (graphics instanceof ComponentAnimations2D) renderFrameAnimation(transform, (ComponentAnimations2D) graphics);
            if (graphics instanceof ComponentBoneAnimations2D) renderBoneAnimation((ComponentBoneAnimations2D) graphics);
            if (graphics instanceof ComponentLight2D) lights.add((ComponentLight2D) graphics);
        }
        polygonSpriteBatch.end();
        // renderLights(camera.lens); //TODO: <-- test.

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

    private void renderFrameAnimation(final ComponentTransform2D transform, final ComponentAnimations2D frameAnimation) {
        if (!frameAnimation.active) return;
        TextureAtlas.AtlasRegion atlasRegion = frameAnimation.getTextureRegion();
        polygonSpriteBatch.setColor(frameAnimation.tint);
        polygonSpriteBatch.draw(atlasRegion, transform.x, transform.y, transform.angle, transform.scaleX, transform.scaleY, frameAnimation.size, frameAnimation.pixelsPerUnit);
    }

    private void renderBoneAnimation(final ComponentBoneAnimations2D boneAnimation) {
        skeletonRenderer.draw(polygonSpriteBatch, boneAnimation.skeleton);
    }


    // TODO: test
    private void renderLights(final Camera camera) {
        float ambientR = 0, ambientG = 0, ambientB = 0, ambientA = 0;
        RayHandler rayHandler = lights.first().rayHandler;
        rayHandler.update();
        // turn on relevant lights, collect ambient lights
        for (ComponentLight2D light2D : lights) {
            if (light2D.light2DData.type == LightType.AMBIENT) {
                ambientR += light2D.light2DData.color.r;
                ambientG += light2D.light2DData.color.g;
                ambientB += light2D.light2DData.color.b;
                ambientA += light2D.light2DData.color.a;
            }
            else {
                light2D.box2DLight.setActive(true);
            }
        }
        // render RayHandler with selected "on" lights and computed ambient
        rayHandler.setAmbientLight(ambientR, ambientG, ambientB, ambientA);
        rayHandler.setCombinedMatrix((OrthographicCamera) camera);
        rayHandler.render();
        // turn lights back to "off"
        for (ComponentLight2D light2D : lights) {
            if (light2D.box2DLight != null) {
                light2D.box2DLight.setActive(false);
            }
        }
    }

    @Override
    public void dispose() {
        polygonSpriteBatch.dispose();
        shapeRenderer.dispose();
        physics2DDebugRenderer.dispose();
    }
}
