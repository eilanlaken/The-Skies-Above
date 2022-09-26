package com.fos.game.engine.renderer.materials.base;

public interface RenderedSpecularMap extends UseTextureMaterial {

    int getSpecularX(float elapsedTime);
    int getSpecularY(float elapsedTime);
    int getHeight(float elapsedTime);
    int getWidth(float elapsedTime);

}
