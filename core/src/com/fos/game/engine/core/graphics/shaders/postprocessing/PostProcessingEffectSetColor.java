package com.fos.game.engine.core.graphics.shaders.postprocessing;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;

public class PostProcessingEffectSetColor extends PostProcessingEffect {

    private static final String vertex = "attribute vec4 " + com.badlogic.gdx.graphics.glutils.ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
            + "uniform mat4 u_projTrans;\n" //
            + "\n" //
            + "void main()\n" //
            + "{\n" //
            + "   gl_Position =  u_projTrans * " + com.badlogic.gdx.graphics.glutils.ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
            + "}\n";

    private static final String fragment = "#ifdef GL_ES\n" //
            + "#define LOWP lowp\n" //
            + "precision mediump float;\n" //
            + "#else\n" //
            + "#define LOWP \n" //
            + "#endif\n" //
            + "uniform vec3 u_color;\n" //
            + "\n" //
            + "void main()\n"//
            + "{\n" //
            + "  gl_FragColor = vec4(color, 1.0);\n" //
            + "}";

    // uniforms
    private int projTransUniformLocation;
    private int textureUniformLocation;
    private int colorUniformLocation;

    // java values that will be loaded into the shader uniforms
    public Camera camera;
    public Color color;

    public PostProcessingEffectSetColor(Camera camera, Color color) {
        super(vertex, fragment);
        this.camera = camera;
        this.color = color;
    }

    @Override
    protected void cacheUniformLocations() {
        projTransUniformLocation = fetchUniformLocation("u_projTrans", false);
        textureUniformLocation = fetchUniformLocation("u_texture", false);
        colorUniformLocation = fetchUniformLocation("u_color", false);
    }

    @Override
    protected void init() {
        bind();
        setUniformi(textureUniformLocation, 0);
    }

    @Override
    protected void loadUniforms() {
        setUniformMatrix(projTransUniformLocation, camera.combined);
        setUniformf(colorUniformLocation, color.r, color.g, color.b);
    }
}
