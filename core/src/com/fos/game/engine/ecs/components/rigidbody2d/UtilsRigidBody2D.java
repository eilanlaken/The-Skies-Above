package com.fos.game.engine.ecs.components.rigidbody2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class UtilsRigidBody2D {

    public static float getBox2DWidth(final TextureRegion region, final float viewportWidth) {
        return region.getRegionWidth() * viewportWidth / Gdx.graphics.getWidth();
    }

    public static float getBox2DHeight(final TextureRegion region, final float viewportHeight) {
        return region.getRegionHeight() * viewportHeight / Gdx.graphics.getHeight();
    }

}
