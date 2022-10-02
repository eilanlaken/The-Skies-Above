package com.fos.game.engine.systems.renderer.shaders.postprocessing;

public class RadialBlur implements PostProcessingEffect {

    public int samples;

    public RadialBlur(int samples) {
        this.samples = samples;
    }
}
