package com.fos.game.engine.ecs.systems.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.core.graphics.g2d.Physics2DDebugRenderer;
import com.fos.game.engine.core.graphics.g2d.SpriteBatch;
import com.fos.game.engine.ecs.components.animations2d.ComponentAnimations2D;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.camera.ComponentCamera;
import com.fos.game.engine.ecs.components.physics2d.ComponentJoint2D;
import com.fos.game.engine.ecs.components.physics2d.ComponentRigidBody2D;
import com.fos.game.engine.ecs.components.transform2d.ComponentTransform2D;
import com.fos.game.engine.ecs.entities.Entity;

public class Renderer2D {

    private final Physics2DDebugRenderer physics2DDebugRenderer;

    protected Renderer2D() {
        this.physics2DDebugRenderer = new Physics2DDebugRenderer();
    }

    protected void renderToCameraInternalBuffer(final SpriteBatch spriteBatch, final ComponentCamera camera, final Array<Entity> entities, boolean debugMode) {
        camera.frameBuffer.begin();
        Gdx.gl.glClearColor(0,0,0,0); // TODO: get value from camera
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT); // TODO: get value from camera
        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(camera.lens.combined);
        for (Entity entity : entities) {
            ComponentAnimations2D animation = (ComponentAnimations2D) entity.components[ComponentType.ANIMATIONS_2D.ordinal()];
            if (animation == null || !animation.active) continue;
            ComponentTransform2D transform = (ComponentTransform2D) entity.components[ComponentType.TRANSFORM_2D.ordinal()];
            final float delta = Gdx.graphics.getDeltaTime();
            animation.advanceTime(delta);
            TextureAtlas.AtlasRegion atlasRegion = animation.getTextureRegion();
            spriteBatch.draw(atlasRegion, transform.getPosition().x, transform.getPosition().y, animation.size, animation.pixelsPerUnit);
        }
        spriteBatch.end();

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

}
