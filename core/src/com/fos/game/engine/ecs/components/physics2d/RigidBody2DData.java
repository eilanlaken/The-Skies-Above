package com.fos.game.engine.ecs.components.physics2d;

import com.badlogic.gdx.physics.box2d.BodyDef;

public class RigidBody2DData {

    public enum Shape {
        CIRCLE, RECTANGLE
    }

    public final BodyDef.BodyType bodyType;
    public final Shape shape;
    public final float width;
    public final float height;
    public final float density;
    public final float friction;
    public final float restitution;
    public final boolean isSensor;

    public RigidBody2DData(BodyDef.BodyType bodyType, Shape shape, float width, float height, float density, float friction, float restitution, boolean isSensor) {
        this.bodyType = bodyType;
        this.shape = shape;
        this.width = width;
        this.height = height;
        this.density = density;
        this.friction = friction;
        this.restitution = restitution;
        this.isSensor = isSensor;
    }

}
