package com.fos.game.engine.renderer.materials.base;

public interface RenderedRoughnessMap extends UseTextureMaterial {

    int getRoughnessX(float elapsedTime);
    int getRoughnessY(float elapsedTime);
    int getHeight(float elapsedTime);
    int getWidth(float elapsedTime);

}
