package com.fos.game.engine.core.graphics.shaders.base;

public abstract class ShaderProgram extends com.badlogic.gdx.graphics.glutils.ShaderProgram {

    public ShaderProgram(final String vertex, final String fragment) {
        super(vertex, fragment);
        if (!isCompiled()) throw new IllegalArgumentException("Error compiling shader: " + getLog());
        cacheUniformLocations();
        init();
    }

    protected abstract void cacheUniformLocations();
    protected abstract void init();
    protected abstract void loadUniforms();
}
