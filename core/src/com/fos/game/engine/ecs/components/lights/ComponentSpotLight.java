package com.fos.game.engine.ecs.components.lights;

import com.badlogic.gdx.math.Vector3;

public class ComponentSpotLight extends Light {

    public Vector3 direction = new Vector3();
    public float cutoffAngle;
    public float exponent;

    public ComponentSpotLight setPosition(float x, float y, float z) {
        worldPosition.set(x, y, z);
        return this;
    }

    public ComponentSpotLight setOffset(float x, float y, float z) {
        localPosition.set(x, y, z);
        return this;
    }

    public ComponentSpotLight setDirection(float x, float y, float z) {
        direction.set(x, y, z).nor();
        return this;
    }

    public ComponentSpotLight setColor(float r, float g, float b) {
        color.set(r,g,b,1);
        return this;
    }

    public ComponentSpotLight setIntensity(float intensity) {
        this.intensity = intensity;
        return this;
    }

    public ComponentSpotLight setCutoffAngle(float cutoffAngle) {
        this.cutoffAngle = cutoffAngle;
        return this;
    }

    public ComponentSpotLight setExponent(float exponent) {
        this.exponent = exponent;
        return this;
    }

}
