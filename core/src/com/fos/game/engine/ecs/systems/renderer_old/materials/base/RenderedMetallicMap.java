package com.fos.game.engine.ecs.systems.renderer_old.materials.base;

public interface RenderedMetallicMap extends UseTextureMaterial {

    int getMetallicX(float elapsedTime);
    int getMetallicY(float elapsedTime);
    int getHeight(float elapsedTime);
    int getWidth(float elapsedTime);

}
