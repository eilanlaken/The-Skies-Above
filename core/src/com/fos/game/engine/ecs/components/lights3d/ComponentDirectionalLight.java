package com.fos.game.engine.ecs.components.lights3d;

import com.badlogic.gdx.math.Vector3;

public class ComponentDirectionalLight extends Light {

    public Vector3 localDirection = new Vector3();
    public Vector3 worldDirection = new Vector3();

    public ComponentDirectionalLight setPosition(float x, float y, float z) {
        worldPosition.set(x, y, z);
        return this;
    }

    public ComponentDirectionalLight setOffset(float x, float y, float z) {
        localPosition.set(x, y, z);
        return this;
    }

    public ComponentDirectionalLight setDirection(float x, float y, float z) {
        worldDirection.set(x, y, z).nor();
        return this;
    }

    public ComponentDirectionalLight setColor(float r, float g, float b) {
        color.set(r,g,b,1);
        return this;
    }

    public ComponentDirectionalLight setIntensity(float intensity) {
        this.intensity = intensity;
        return this;
    }

}
