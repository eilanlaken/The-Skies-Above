package com.fos.game.engine.components.lights;

public class PointLight extends ComponentLight {

    public PointLight setPosition(float x, float y, float z) {
        worldPosition.set(x, y, z);
        return this;
    }

    public PointLight setOffset(float x, float y, float z) {
        localPosition.set(x, y, z);
        return this;
    }

    public PointLight setColor(float r, float g, float b) {
        color.set(r,g,b,1);
        return this;
    }

    public PointLight setIntensity(float intensity) {
        this.intensity = intensity;
        return this;
    }

}
