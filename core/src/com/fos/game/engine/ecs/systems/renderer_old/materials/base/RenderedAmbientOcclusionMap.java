package com.fos.game.engine.ecs.systems.renderer_old.materials.base;

public interface RenderedAmbientOcclusionMap extends UseTextureMaterial {

    int getAmbientOcclusionX(float elapsedTime);
    int getAmbientOcclusionY(float elapsedTime);
    int getHeight(float elapsedTime);
    int getWidth(float elapsedTime);

}
