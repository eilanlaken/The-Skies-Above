package com.fos.game.engine.core.graphics.g2d;

import com.badlogic.gdx.Gdx;

public class GraphicsUtils {

    public static float getAspectRatio() {
        return (float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
    }

}
