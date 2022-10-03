package com.fos.game.engine.ecs.systems.renderer.shaders.base;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

public abstract class GameShader implements Shader {

    protected ShaderProgram shaderProgram;

    protected GameShader(final String vertex, final String fragment) {
        shaderProgram = new ShaderProgram(vertex, fragment);
        if (!shaderProgram.isCompiled())
            throw new GdxRuntimeException(shaderProgram.getLog());
        init();
    }

    @Override
    public void init() {
        registerUniformLocations();
    }

    @Override
    public int compareTo(Shader shader) {
        return 0;
    }

    protected abstract void registerUniformLocations();

    @Override
    public void end() {}

    @Override
    public void dispose() {
        shaderProgram.dispose();
    }

    @Override
    public boolean canRender(Renderable renderable) {
        return false;
    }

}
