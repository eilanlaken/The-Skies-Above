package com.fos.game.engine.renderer.materials.base;

public interface RenderedDiffuseMap extends UseTextureMaterial {

    int getDiffuseX(float elapsedTime);
    int getDiffuseY(float elapsedTime);
    int getHeight(float elapsedTime);
    int getWidth(float elapsedTime);

}
