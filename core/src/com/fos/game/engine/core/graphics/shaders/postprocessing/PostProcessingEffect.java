package com.fos.game.engine.core.graphics.shaders.postprocessing;


import com.fos.game.engine.core.graphics.shaders.base.Shader;

public abstract class PostProcessingEffect extends Shader {

    public PostProcessingEffect(final String vertex, final String fragment) {
        super(vertex, fragment);
    }

}
