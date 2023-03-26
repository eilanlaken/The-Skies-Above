package com.fos.game.engine.ecs.systems.physics2d;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.physics2d.ComponentJoint2D;
import com.fos.game.engine.ecs.components.physics2d.ComponentBody2D;
import com.fos.game.engine.ecs.components.transform.ComponentTransform2D;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.systems.base.EntitiesProcessor;

@Deprecated
public class Physics2D implements EntitiesProcessor, Disposable {

    protected World world;
    protected RayHandler rayHandler;

    private final Array<Entity> bodies = new Array<>();
    private final Array<Entity> joints = new Array<>();

    public Physics2D() {
        this.world = new World(new Vector2(0,0), true);
        this.rayHandler = new RayHandler(this.world);
    }

    @Override
    public void process(final Array<Entity> entities) {
        Physics2DUtils.prepare(entities, bodies, joints);
        final float delta = Math.min(1f / 30f, Gdx.graphics.getDeltaTime());
        this.world.step(delta, 6, 2);
        for (Entity entity : bodies) {
            ComponentTransform2D transform = (ComponentTransform2D) entity.components[ComponentType.TRANSFORM_2D.ordinal()];
            ComponentBody2D componentBody2D = (ComponentBody2D) entity.components[ComponentType.PHYSICS_2D.ordinal()];
            Body body = componentBody2D.body;
            if (componentBody2D.active != body.isActive()) body.setActive(componentBody2D.active);
            transform.worldX = body.getPosition().x;
            transform.worldY = body.getPosition().y;
            transform.worldAngle = body.getAngle();
        }
    }

    public void addPhysics(final Entity entity) {
        Component physics2d = (Component) entity.components[ComponentType.PHYSICS_2D.ordinal()];
        if (physics2d == null) return;
        if (physics2d instanceof ComponentBody2D) {
            ComponentTransform2D transform = (ComponentTransform2D) entity.components[ComponentType.TRANSFORM_2D.ordinal()];
            ComponentBody2D body2D = (ComponentBody2D) physics2d;
            Physics2DUtils.addRigidBody2D(world, entity, body2D, transform);
        } else {
            ComponentJoint2D joint2D = (ComponentJoint2D) physics2d;
            Physics2DUtils.addJoint2D(world, entity, joint2D);
        }
    }

    public void destroyPhysics(final Entity entity) {
        Component physics2d = (Component) entity.components[ComponentType.PHYSICS_2D.ordinal()];
        if (physics2d == null) return;
        if (physics2d instanceof ComponentBody2D) {
            ComponentBody2D body2D = (ComponentBody2D) physics2d;
            Physics2DUtils.destroyRigidBody2D(world, body2D);
        } else {
            ComponentJoint2D joint2D = (ComponentJoint2D) physics2d;
            Physics2DUtils.destroyJoint2D(world, joint2D);
        }
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
