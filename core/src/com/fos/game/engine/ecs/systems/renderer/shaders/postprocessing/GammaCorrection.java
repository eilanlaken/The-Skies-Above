package com.fos.game.engine.ecs.systems.renderer.shaders.postprocessing;

public class GammaCorrection implements PostProcessingEffect {

    public float gamma;

    public GammaCorrection(float gamma) {
        this.gamma = gamma;
    }

}
