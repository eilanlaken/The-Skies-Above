package com.fos.game.engine.ecs.systems.renderer_old.materials.base;

public interface RenderedBloomMap extends UseTextureMaterial {

    int getBloomX(float elapsedTime);
    int getBloomY(float elapsedTime);
    int getHeight(float elapsedTime);
    int getWidth(float elapsedTime);

}
