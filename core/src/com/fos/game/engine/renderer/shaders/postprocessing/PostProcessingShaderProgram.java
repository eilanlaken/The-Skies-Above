package com.fos.game.engine.renderer.shaders.postprocessing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class PostProcessingShaderProgram extends ShaderProgram {

    protected static final String vertex = Gdx.files.classpath("shaders/postprocessing/postVertexShader.glsl").readString();
    protected static final String fragment = Gdx.files.classpath("shaders/postprocessing/postFragmentShader.glsl").readString();

    // common uniforms
    private int uniform_cameraCombinedLocation;
    private int uniform_textureLocation;
    private int uniform_screenWidthLocation;
    private int uniform_screenHeightLocation;

    // bloom HD effect
    private int bloom_location;
    private int uniform_radiusLocation;

    // radial blur (volumetric lighting)
    private int radial_blur_location;

    // negative effect
    private int negative_location;

    // grey scale effect
    private int grey_scale_location;

    // tint
    private int tint_location;

    // gamma correction
    private int gamma_correction_location;

    public PostProcessingShaderProgram() {
        super(vertex, fragment);
        if (!isCompiled())
            throw new GdxRuntimeException(getLog());
        registerUniformLocations();
        bind();
        setUniformf(uniform_screenWidthLocation, Gdx.graphics.getWidth());
        setUniformf(uniform_screenHeightLocation, Gdx.graphics.getHeight());
        setUniformf(uniform_radiusLocation, 8); // default value
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

    public void setParams(final PostProcessingEffect... postProcessingEffects) {

    }

}
