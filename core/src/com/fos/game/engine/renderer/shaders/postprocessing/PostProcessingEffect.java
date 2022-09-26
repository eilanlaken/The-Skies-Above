package com.fos.game.engine.renderer.shaders.postprocessing;

public interface PostProcessingEffect {

    PostProcessingEffect[] DEFAULT_3D_POST_PROCESSING = {
            new Bloom(Bloom.Quality.HIGH, 0.4f),
            // ...
    };

    PostProcessingEffect[] DEFAULT_2D_POST_PROCESSING = { };

}
