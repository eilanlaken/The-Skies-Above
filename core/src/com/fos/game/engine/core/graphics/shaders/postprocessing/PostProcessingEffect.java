package com.fos.game.engine.core.graphics.shaders.postprocessing;


import com.fos.game.engine.core.graphics.shaders.base.ShaderProgram;

public abstract class PostProcessingEffect extends ShaderProgram {

    public PostProcessingEffect(final String vertex, final String fragment) {
        super(vertex, fragment);
    }

}
