package com.fos.game.engine.core.graphics.shaders.base;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public abstract class Shader extends ShaderProgram {



    public Shader(final String vertex, final String fragment) {
        super(vertex, fragment);

        if (!isCompiled()) throw new IllegalArgumentException("Error compiling shader: " + getLog());
        cacheUniformLocations();
        init();
    }

    private void cacheUniformLocations() {
        String[] uniforms = getUniforms();
    }

    protected abstract void init();
    public abstract void loadUniforms();
}
