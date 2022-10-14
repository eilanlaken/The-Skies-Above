package com.fos.game.engine.ecs.components.camera;

@Deprecated
public class Camera2DData {

    public final Enum[] layers;
    public final float positionX;
    public final float positionY;
    public final float zoom;
    public final float viewportWidth;
    public final float viewportHeight;

    public Camera2DData(Enum[] layers, float positionX, float positionY, float zoom, float viewportWidth, float viewportHeight) {
        this.layers = layers;
        this.positionX = positionX;
        this.positionY = positionY;
        this.zoom = zoom;
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
    }
}
