package com.fos.game.engine.ecs.systems.renderer.shaders.post;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class PostProcessingSinglePassGaussianBlurLDShaderProgram extends ShaderProgram {

    private static final String vertex = Gdx.files.classpath("shaders/post/fosSinglePassBlurLDVertexShader.glsl").readString();
    private static final String fragment = Gdx.files.classpath("shaders/post/fosSinglePassBlurLDFragmentShader.glsl").readString();

    // uniforms
    private int uniform_cameraCombinedLocation;
    private int uniform_textureLocation;
    private int uniform_screenWidthLocation;
    private int uniform_screenHeightLocation;
    private int uniform_radiusLocation;

    public PostProcessingSinglePassGaussianBlurLDShaderProgram() {
        super(vertex, fragment);
        if (!isCompiled())
            throw new GdxRuntimeException(getLog());
        registerUniformLocations();
        bind();
        setUniformf(uniform_screenWidthLocation, Gdx.graphics.getWidth());
        setUniformf(uniform_screenHeightLocation, Gdx.graphics.getHeight());
    }

    private void registerUniformLocations() {
        uniform_cameraCombinedLocation = fetchUniformLocation("u_projTrans", false);
        uniform_textureLocation = fetchUniformLocation("u_texture", false);
        uniform_screenWidthLocation = fetchUniformLocation("screen_width", false);
        uniform_screenHeightLocation = fetchUniformLocation("screen_height", false);
        uniform_radiusLocation = fetchUniformLocation("radius", false);
    }

    public void setCameraCombined(final Matrix4 combined) {
        setUniformMatrix(uniform_cameraCombinedLocation, combined);
    }

    public void setRadius(float radius) {
        setUniformf(uniform_radiusLocation, radius);
    }

    public void setScreenWidth(float screenWidth) {
        setUniformf(uniform_screenWidthLocation, screenWidth);
    }

    public void setScreenHeight(float screenHeight) {
        setUniformf(uniform_screenHeightLocation, screenHeight);
    }


}
