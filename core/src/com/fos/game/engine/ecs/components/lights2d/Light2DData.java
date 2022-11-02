package com.fos.game.engine.ecs.components.lights2d;

import com.badlogic.gdx.graphics.Color;

public class Light2DData {

    public final LightType type;
    public final Color color;
    public final int rays;
    public final float distance;

    public Light2DData(final LightType type, final Color color, final int rays, final float distance) {
        this.type = type;
        this.color = color;
        this.rays = rays;
        this.distance = distance;
    }

}
