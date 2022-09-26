package com.fos.game.engine.components.lights;

import com.badlogic.gdx.math.Vector3;

public class DirectionalLight extends ComponentLight {

    public Vector3 localDirection = new Vector3();
    public Vector3 worldDirection = new Vector3();

    public DirectionalLight setPosition(float x, float y, float z) {
        worldPosition.set(x, y, z);
        return this;
    }

    public DirectionalLight setOffset(float x, float y, float z) {
        localPosition.set(x, y, z);
        return this;
    }

    public DirectionalLight setDirection(float x, float y, float z) {
        worldDirection.set(x, y, z).nor();
        return this;
    }

    public DirectionalLight setColor(float r, float g, float b) {
        color.set(r,g,b,1);
        return this;
    }

    public DirectionalLight setIntensity(float intensity) {
        this.intensity = intensity;
        return this;
    }

}
