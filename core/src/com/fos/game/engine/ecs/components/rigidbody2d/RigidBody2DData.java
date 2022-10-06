package com.fos.game.engine.ecs.components.rigidbody2d;

import com.badlogic.gdx.physics.box2d.BodyDef;

public class RigidBody2DData {

    public enum Shape {
        CIRCLE, RECTANGLE
    }

    public final BodyDef.BodyType bodyType;
    public final float positionX;
    public final float positionY;
    public final Shape shape;
    public final float width;
    public final float height;
    public final float density;
    public final float friction;
    public final float restitution;
    public final boolean isSensor;

    public RigidBody2DData(BodyDef.BodyType bodyType, float positionX, float positionY, Shape shape, float width, float height, float density, float friction, float restitution, boolean isSensor) {
        this.bodyType = bodyType;
        this.positionX = positionX;
        this.positionY = positionY;
        this.shape = shape;
        this.width = width;
        this.height = height;
        this.density = density;
        this.friction = friction;
        this.restitution = restitution;
        this.isSensor = isSensor;
    }
}

/*
BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 0);
        body1 = world.createBody(bodyDef);
        CircleShape circle = new CircleShape();
        circle.setRadius(1f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit
        body1.createFixture(fixtureDef);
        circle.dispose();
 */