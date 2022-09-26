package com.fos.game.engine.components.lights;

import com.badlogic.gdx.math.Vector3;

public class SpotLight extends ComponentLight {

    public Vector3 direction = new Vector3();
    public float cutoffAngle;
    public float exponent;

    public SpotLight setPosition(float x, float y, float z) {
        worldPosition.set(x, y, z);
        return this;
    }

    public SpotLight setOffset(float x, float y, float z) {
        localPosition.set(x, y, z);
        return this;
    }

    public SpotLight setDirection(float x, float y, float z) {
        direction.set(x, y, z).nor();
        return this;
    }

    public SpotLight setColor(float r, float g, float b) {
        color.set(r,g,b,1);
        return this;
    }

    public SpotLight setIntensity(float intensity) {
        this.intensity = intensity;
        return this;
    }

    public SpotLight setCutoffAngle(float cutoffAngle) {
        this.cutoffAngle = cutoffAngle;
        return this;
    }

    public SpotLight setExponent(float exponent) {
        this.exponent = exponent;
        return this;
    }

}
