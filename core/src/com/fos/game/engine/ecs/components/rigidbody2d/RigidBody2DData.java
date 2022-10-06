package com.fos.game.engine.ecs.components.rigidbody2d;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

    public RigidBody2DData(BodyDef.BodyType bodyType, float positionX, float positionY, TextureRegion region, float viewportWidth, float viewportHeight, float density, float friction, float restitution, boolean isSensor) {
        this.bodyType = bodyType;
        this.positionX = positionX;
        this.positionY = positionY;
        this.shape = Shape.RECTANGLE;
        this.width = UtilsRigidBody2D.getBox2DWidth(region, viewportWidth);
        this.height = UtilsRigidBody2D.getBox2DHeight(region, viewportHeight);
        this.density = density;
        this.friction = friction;
        this.restitution = restitution;
        this.isSensor = isSensor;
    }

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
