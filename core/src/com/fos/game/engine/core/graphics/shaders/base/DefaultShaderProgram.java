package com.fos.game.engine.core.graphics.shaders.base;

import com.badlogic.gdx.graphics.Camera;

public class DefaultShaderProgram extends Shader {

    private int projTransUniformLocation;
    private int textureUniformLocation;

    public Camera currentCamera;

    private static final String vertex = "attribute vec4 " + com.badlogic.gdx.graphics.glutils.ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
            + "attribute vec2 " + com.badlogic.gdx.graphics.glutils.ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
            + "uniform mat4 u_projTrans;\n" //
            + "varying vec2 v_texCoords;\n" //
            + "\n" //
            + "void main()\n" //
            + "{\n" //
            + "   v_texCoords = " + com.badlogic.gdx.graphics.glutils.ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
            + "   gl_Position =  u_projTrans * " + com.badlogic.gdx.graphics.glutils.ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
            + "}\n";

    private static final String fragment = "#ifdef GL_ES\n" //
            + "#define LOWP lowp\n" //
            + "precision mediump float;\n" //
            + "#else\n" //
            + "#define LOWP \n" //
            + "#endif\n" //
            + "varying vec2 v_texCoords;\n" //
            + "uniform sampler2D u_texture;\n" //
            + "void main()\n"//
            + "{\n" //
            + "  gl_FragColor = texture2D(u_texture, v_texCoords);\n" //
            + "}";

    public DefaultShaderProgram() {
        super(vertex, fragment);
    }

    protected void cacheUniformLocations() {
        projTransUniformLocation = fetchUniformLocation("u_projTrans", false);
        textureUniformLocation = fetchUniformLocation("u_texture", false);
    }

    @Override
    protected void init() {
        bind();
        setUniformi(textureUniformLocation, 0);
    }

    @Override
    public void loadUniforms() {
        setUniformMatrix(projTransUniformLocation, currentCamera.combined);
    }
}
