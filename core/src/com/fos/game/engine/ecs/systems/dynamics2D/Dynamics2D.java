package com.fos.game.engine.ecs.systems.dynamics2D;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.physics2d.ComponentBody2D;
import com.fos.game.engine.ecs.components.physics2d.ComponentJoint2D;
import com.fos.game.engine.ecs.components.transform.ComponentTransform2D;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.systems.base.EntitiesProcessor;

public class Dynamics2D implements EntitiesProcessor, Disposable {

    protected final World world;
    protected final RayHandler rayHandler;

    public Dynamics2D() {
        this.world = new World(new Vector2(0,-1f), true);
        this.world.setContactListener(new Dynamics2DCollisionResolver());
        this.rayHandler = new RayHandler(this.world);
    }

    @Override
    public void process(Array<Entity> entities) {
        final float delta = Math.min(1f / 30f, Gdx.graphics.getDeltaTime());
        this.world.step(delta, 6, 2);

        for (Entity entity : entities) {
            ComponentTransform2D transform = entity.getTransform2D();
            transform.updated = false;
        }
        for (Entity entity : entities) {
            updateWorldTransform(entity);
        }
    }

    private void updateWorldTransform(Entity entity) {
        ComponentTransform2D transform = entity.getTransform2D();
        if (transform.updated) return;
        ComponentBody2D componentBody2D = entity.getBody2D();
        if (componentBody2D != null) {
            Body body = componentBody2D.body;
            if (componentBody2D.active != body.isActive()) body.setActive(componentBody2D.active);
            transform.worldX = body.getPosition().x;
            transform.worldY = body.getPosition().y;
            transform.worldAngle = body.getAngle();
            transform.updated = true;
            return;
        }
        if (transform.isStatic) {
            transform.updated = true;
            return;
        }
        if (entity.parent == null) {
            transform.worldX = transform.x;
            transform.worldY = transform.y;
            transform.worldZ = transform.z;
            transform.worldScaleX = transform.scaleX;
            transform.worldScaleY = transform.scaleY;
            transform.worldAngle = transform.angle;
            transform.updated = true;
            return;
        }
        if (entity.parent != null) {
            updateWorldTransform(entity.parent);
            ComponentTransform2D parentTransform = entity.parent.getTransform2D();
            float cos = MathUtils.cos(parentTransform.worldAngle);
            float sin = MathUtils.sin(parentTransform.worldAngle);
            float x = transform.x * cos - transform.y * sin;
            float y = transform.x * sin + transform.y * cos;
            transform.worldX = parentTransform.worldX + x * parentTransform.worldScaleX;
            transform.worldY = parentTransform.worldY + y * parentTransform.worldScaleY;
            transform.worldZ = parentTransform.worldZ;
            transform.worldScaleX = transform.scaleX * parentTransform.worldScaleX;
            transform.worldScaleY = transform.scaleY * parentTransform.worldScaleY;
            transform.worldAngle  = transform.angle + parentTransform.worldAngle;
        }
    }

    public void parent(Entity entity) {
        /*
        ComponentTransform2D transform = entity.getTransform2D();
        if (transform == null) return;
        ComponentTransform2D transformParent = entity.parent.getTransform2D();
        if (transformParent == null) return;
        Vector2 compute = new Vector2();
        compute.set(-transformParent.worldX + transform.x, -transformParent.worldY + transform.y);
        compute.rotateRad(-transformParent.worldAngle);
        compute.scl(1 / transformParent.worldScaleX, 1 / transformParent.worldScaleY);
        transform.x = compute.x;
        transform.y = compute.y;
        transform.angle = -transformParent.worldAngle + transform.angle;
        transform.scaleX = transform.scaleX / transformParent.worldScaleX;
        transform.scaleY = transform.scaleY / transformParent.worldScaleY;
        transform.updated = true;

         */
    }

    public void unparent(Entity entity) {
        /*
        ComponentTransform2D transform = entity.getTransform2D();
        if (transform == null) return;
        transform.x = transform.worldX;
        transform.y = transform.worldY;
        transform.z = transform.worldZ;
        transform.scaleX = transform.worldScaleX;
        transform.scaleY = transform.worldScaleY;
        transform.angle = transform.worldAngle;
        transform.updated = true;

         */
    }

    public void addPhysics(final Entity entity) {
        Component physics2d = (Component) entity.components[ComponentType.PHYSICS_2D.ordinal()];
        if (physics2d == null) return;
        if (physics2d instanceof ComponentBody2D) {
            ComponentTransform2D transform = (ComponentTransform2D) entity.components[ComponentType.TRANSFORM_2D.ordinal()];
            ComponentBody2D body2D = (ComponentBody2D) physics2d;
            Dynamics2DUtils.addRigidBody2D(world, entity, body2D, transform);
        } else {
            ComponentJoint2D joint2D = (ComponentJoint2D) physics2d;
            Dynamics2DUtils.addJoint2D(world, entity, joint2D);
        }
    }

    public void destroyPhysics(final Entity entity) {
        Component physics2d = (Component) entity.components[ComponentType.PHYSICS_2D.ordinal()];
        if (physics2d == null) return;
        if (physics2d instanceof ComponentBody2D) {
            ComponentBody2D body2D = (ComponentBody2D) physics2d;
            Dynamics2DUtils.destroyRigidBody2D(world, body2D);
        } else {
            ComponentJoint2D joint2D = (ComponentJoint2D) physics2d;
            Dynamics2DUtils.destroyJoint2D(world, joint2D);
        }
    }

    @Override
    public boolean shouldProcess(Entity entity) {
        if ((entity.componentsBitMask & Dynamics2DUtils.PHYSICS_2D_BIT_MASK) > 0) return true;
        ComponentTransform2D transform2D = entity.getTransform2D();
        return transform2D != null && !transform2D.isStatic;
    }

    @Override
    public void dispose() {
        world.dispose();
    }
}
