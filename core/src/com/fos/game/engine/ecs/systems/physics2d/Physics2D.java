package com.fos.game.engine.ecs.systems.physics2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.fos.game.engine.ecs.components.physics2d.ComponentJoint2D;
import com.fos.game.engine.ecs.components.physics2d.ComponentRigidBody2D;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.systems.base.EntitiesProcessor;

public class Physics2D implements EntitiesProcessor, Disposable {

    public World world;

    public Physics2D() {
        this.world = new World(new Vector2(), true);
    }

    @Override
    public void process(final Array<Entity> entities) {
        final float delta = Math.min(1f / 30f, Gdx.graphics.getDeltaTime());
        this.world.step(delta, 6, 2);
    }

    public void addBody(final ComponentRigidBody2D componentRigidBody2D) {
        Physics2DUtils.addRigidBody2D(world, componentRigidBody2D);
    }

    public void addJoint(final ComponentJoint2D componentJoint2D) {
        Physics2DUtils.addJoint(world, componentJoint2D);
    }

    @Override
    public boolean shouldProcess(Entity entity) {
        return (entity.componentsBitMask & Physics2DUtils.PHYSICS_2D_BIT_MASK) > 0;
    }

    @Override
    public void dispose() {
        world.dispose();
    }
}
