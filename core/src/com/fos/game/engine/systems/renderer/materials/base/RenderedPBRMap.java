package com.fos.game.engine.systems.renderer.materials.base;

public interface RenderedPBRMap  extends UseTextureMaterial {

    int getPBRX(float elapsedTime);
    int getPBRY(float elapsedTime);
    int getHeight(float elapsedTime);
    int getWidth(float elapsedTime);

}
