package com.fos.game.engine.ecs.systems.physics2d;

import com.badlogic.gdx.physics.box2d.*;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.physics2d.ComponentJoint2D;
import com.fos.game.engine.ecs.components.physics2d.ComponentRigidBody2D;
import com.fos.game.engine.ecs.components.physics2d.RigidBody2DData;

public class Physics2DUtils {

    protected static final short PHYSICS_2D_BIT_MASK = ComponentType.PHYSICS_2D_BODY.bitMask;

    protected static void addRigidBody2D(final World world, final ComponentRigidBody2D componentRigidBody2D) {
        RigidBody2DData data = componentRigidBody2D.data;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = data.bodyType;
        bodyDef.position.set(data.positionX, data.positionY);
        componentRigidBody2D.body = world.createBody(bodyDef);
        bodyDef.fixedRotation = false;
        Shape shape = getShape(data);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = data.density;
        fixtureDef.isSensor = data.isSensor;
        fixtureDef.friction = data.friction;
        fixtureDef.restitution = data.restitution;
        componentRigidBody2D.body.createFixture(fixtureDef);
        shape.dispose();
    }

    protected static void addJoint(final World world, final ComponentJoint2D componentJoint2D) {
        componentJoint2D.joint = world.createJoint(componentJoint2D.data.jointDef);
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
