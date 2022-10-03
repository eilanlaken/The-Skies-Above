package com.fos.game.engine.ecs.components.lights;

public class ComponentPointLight extends Light {

    public ComponentPointLight setPosition(float x, float y, float z) {
        worldPosition.set(x, y, z);
        return this;
    }

    public ComponentPointLight setOffset(float x, float y, float z) {
        localPosition.set(x, y, z);
        return this;
    }

    public ComponentPointLight setColor(float r, float g, float b) {
        color.set(r,g,b,1);
        return this;
    }

    public ComponentPointLight setIntensity(float intensity) {
        this.intensity = intensity;
        return this;
    }

}
