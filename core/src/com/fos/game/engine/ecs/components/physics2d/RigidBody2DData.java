package com.fos.game.engine.ecs.components.physics2d;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;

public class RigidBody2DData {

    // TODO: extend to polygon shapes.
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
    public final Filter filter;
    public final boolean isSensor;

    public RigidBody2DData(BodyDef.BodyType bodyType, Shape shape, float width, float height, Filter filter, float density, float friction, float restitution, boolean isSensor) {
        this.bodyType = bodyType;
        this.shape = shape;
        this.width = width;
        this.height = height;
        this.filter = filter;
        this.density = density;
        this.friction = friction;
        this.restitution = restitution;
        this.isSensor = isSensor;
    }

}
