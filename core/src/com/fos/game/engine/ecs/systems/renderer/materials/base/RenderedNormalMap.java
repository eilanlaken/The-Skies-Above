package com.fos.game.engine.ecs.systems.renderer.materials.base;

public interface RenderedNormalMap extends UseTextureMaterial {

    int getNormalX(float elapsedTime);
    int getNormalY(float elapsedTime);
    int getHeight(float elapsedTime);
    int getWidth(float elapsedTime);

}