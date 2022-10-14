package com.fos.game.engine.ecs.systems.renderer_old.shaders.post;

import com.badlogic.gdx.Gdx;

public class PostProcessingNegative extends PostProcessingEffect {

    private static final String fragment = Gdx.files.classpath("shaders/post/postNegativeFragmentShader.glsl").readString();

    private int uniform_textureLocation;

    public PostProcessingNegative() {
        super(fragment);
        registerUniformLocations();
    }

    @Override
    protected void registerUniformLocations() {
        uniform_textureLocation = fetchUniformLocation("u_texture", false);
    }
}
