package com.fos.game.engine.ecs.systems.renderer_old.shaders.postprocessing;

public class Bloom implements PostProcessingEffect {

    public enum Quality {
        LOW, HIGH, ULTRA_HIGH
    }

    public Quality quality;
    public float intensity;

    public Bloom(Quality quality, float intensity) {
        this.quality = quality;
        this.intensity = intensity;
    }

}