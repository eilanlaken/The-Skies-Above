package com.fos.game.engine.systems.renderer.shaders.post;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class PostProcessingGodRaysShaderProgram extends ShaderProgram {

    private static final String vertex = Gdx.files.classpath("shaders/post/fosGodRaysVertexShader.glsl").readString();
    private static final String fragment = Gdx.files.classpath("shaders/post/fosGodRaysFragmentShader.glsl").readString();

    // uniforms
    private int uniform_lightPositionLocation;
    private int uniform_cameraCombinedLocation;
    private int uniform_textureLocation;

    public PostProcessingGodRaysShaderProgram() {
        super(vertex, fragment);
        registerUniformLocations();
    }

    public void bindLightPosition(final Vector3 position) {
        setUniformf(uniform_lightPositionLocation, position);
    }

    public void bindCameraCombined(final Matrix4 combined) {
        setUniformMatrix(uniform_cameraCombinedLocation, combined);
    }

    private void registerUniformLocations() {
        uniform_lightPositionLocation = fetchUniformLocation("light_position", false);
        uniform_cameraCombinedLocation = fetchUniformLocation("camera_combined", false);
        uniform_textureLocation = fetchUniformLocation("u_texture", false);
    }


}
