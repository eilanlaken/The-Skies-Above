package com.fos.game.engine.ecs.systems.physics2d;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.physics2d.ComponentJoint2D;
import com.fos.game.engine.ecs.components.physics2d.ComponentRigidBody2D;
import com.fos.game.engine.ecs.components.physics2d.RigidBody2DData;
import com.fos.game.engine.ecs.components.transform2d.ComponentTransform2D;
import com.fos.game.engine.ecs.entities.Entity;

public class Physics2DUtils {

    //protected static final int PHYSICS_2D_BIT_MASK = ComponentType.PHYSICS_2D_BODY.bitMask;
    protected static final int PHYSICS_2D_BIT_MASK = ComponentType.PHYSICS_2D_BODY.bitMask | ComponentType.PHYSICS_2D_JOINT.bitMask | ComponentType.LIGHT_2D.bitMask;

    protected static void prepare(final Array<Entity> entities, Array<Entity> bodiesResult, Array<Entity> jointsResult) {
        bodiesResult.clear();
        jointsResult.clear();
        for (final Entity entity : entities) {
            ComponentRigidBody2D body = (ComponentRigidBody2D) entity.components[ComponentType.PHYSICS_2D_BODY.ordinal()];
            ComponentJoint2D joint = (ComponentJoint2D) entity.components[ComponentType.PHYSICS_2D_JOINT.ordinal()];
            if (body != null) bodiesResult.add(entity);
            if (joint != null) jointsResult.add(entity);
        }
    }

    protected static void addRigidBody2D(final World world, final Entity entity, final ComponentRigidBody2D componentRigidBody2D,
                                         final ComponentTransform2D transform2D) {
        RigidBody2DData data = componentRigidBody2D.data;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = data.bodyType;
        bodyDef.position.set(transform2D.getPosition().x, transform2D.getPosition().y);
        bodyDef.angle = transform2D.transform.getRotation();
        componentRigidBody2D.body = world.createBody(bodyDef);
        componentRigidBody2D.body.setUserData(entity);
        transform2D.transform = componentRigidBody2D.body.getTransform();
        //bodyDef.fixedRotation = false;
        Shape shape = getShape(data);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = data.filter.categoryBits;
        fixtureDef.filter.maskBits = data.filter.maskBits;
        fixtureDef.filter.groupIndex = data.filter.groupIndex;
        fixtureDef.density = data.density;
        fixtureDef.isSensor = data.isSensor;
        fixtureDef.friction = data.friction;
        fixtureDef.restitution = data.restitution;
        componentRigidBody2D.body.createFixture(fixtureDef);
        shape.dispose();
    }

    protected static void addJoint(final World world, final Entity entity, final ComponentJoint2D componentJoint2D) {
        componentJoint2D.joint = world.createJoint(componentJoint2D.data.jointDef);
        componentJoint2D.joint.setUserData(entity);
    }

    private static Shape getShape(final RigidBody2DData data) {
        if (data.shape == RigidBody2DData.Shape.CIRCLE) {
            CircleShape shape = new CircleShape();
            shape.setRadius((data.width + data.height) * 0.5f); // average of width and height
            return shape;
        }
        if (data.shape == RigidBody2DData.Shape.RECTANGLE) {
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(data.width / 2, data.height / 2);
            return shape;
        }
        return null;
    }

}
