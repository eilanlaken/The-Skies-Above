package com.fos.game.engine.ecs.systems.renderer.shaders.post;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class PostProcessingDirectionalBlurShaderProgram extends ShaderProgram {

    private static final String vertex = Gdx.files.classpath("shaders/post/fosDirectionalVertexShader.glsl").readString();
    private static final String fragment = Gdx.files.classpath("shaders/post/fosDirectionalBlurFragmentShader.glsl").readString();

    // uniforms
    private int uniform_cameraCombinedLocation;
    private int uniform_textureLocation;
    private int uniform_resolutionLocation;
    private int uniform_radiusLocation;
    private int uniform_dirLocation;

    public PostProcessingDirectionalBlurShaderProgram() {
        super(vertex, fragment);
        if (!isCompiled())
            throw new GdxRuntimeException(getLog());
        registerUniformLocations();
        bind();
        setUniformf(uniform_resolutionLocation, Gdx.graphics.getWidth());
    }

    private void registerUniformLocations() {
        uniform_cameraCombinedLocation = fetchUniformLocation("u_projTrans", false);
        uniform_textureLocation = fetchUniformLocation("u_texture", false);
        uniform_resolutionLocation = fetchUniformLocation("resolution", false);
        uniform_radiusLocation = fetchUniformLocation("radius", false);
        uniform_dirLocation = fetchUniformLocation("dir", false);
    }

    public void setCameraCombined(final Matrix4 combined) {
        setUniformMatrix(uniform_cameraCombinedLocation, combined);
    }

    public void setResolution(float resolution) {
        setUniformf(uniform_resolutionLocation, resolution);
    }

    public void setRadius(float radius) {
        setUniformf(uniform_radiusLocation, radius);
    }

    public void setDirectionToX() {
        setUniformf(uniform_dirLocation, 1, 0);
    }

    public void setDirectionToY() {
        setUniformf(uniform_dirLocation, 0, 1);
    }


}
