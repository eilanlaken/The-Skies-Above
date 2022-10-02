package com.fos.game.engine.systems.renderer.materials.base;

public interface RenderedAlbedoMap extends UseTextureMaterial {

    int getAlbedoX(float elapsedTime);
    int getAlbedoY(float elapsedTime);
    int getHeight(float elapsedTime);
    int getWidth(float elapsedTime);

}
