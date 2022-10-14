package com.fos.game.engine.ecs.systems.renderer_old.materials.base;

public interface RenderedParallaxMap extends UseTextureMaterial {

    int getParallaxX(float elapsedTime);
    int getParallaxY(float elapsedTime);
    int getHeight(float elapsedTime);
    int getWidth(float elapsedTime);

}
