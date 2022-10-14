package com.fos.game.engine.ecs.systems.renderer_old.shaders.post;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public abstract class PostProcessingEffect extends ShaderProgram {

    protected static final String vertex = Gdx.files.classpath("shaders/post/postVertexShader.glsl").readString();

    public PostProcessingEffect(final String fragment) {
        super(vertex, fragment);
        registerUniformLocations();
    }

    protected abstract void registerUniformLocations();

}
