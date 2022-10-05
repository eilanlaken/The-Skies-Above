package com.fos.game.engine.ecs.components.rigidbody2d;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class UtilsRigidBody2D {

    public static final float PPM = 32;

    public static PolygonShape createBox(final float width, final float height) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PPM,height / 2 / PPM);
        return shape;
    }

    public static FixtureDef createFixtureDef(PolygonShape shape, float density, short categoryBits, short maskBits) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.filter.categoryBits = categoryBits;
        fixtureDef.filter.maskBits = maskBits;
        return fixtureDef;
    }

}
