package com.fos.game.engine.core.graphics.shaders.base;

public class ExampleShaderProgram extends Shader {

    public static final String vertex = "attribute vec4 " + com.badlogic.gdx.graphics.glutils.ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
            + "attribute vec2 " + com.badlogic.gdx.graphics.glutils.ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
            + "uniform mat4 u_projTrans;\n" //
            + "uniform mat4 u_projTrans2;\n" //
            + "varying vec2 v_texCoords;\n" //
            + "\n" //
            + "void main()\n" //
            + "{\n" //
            + "   v_texCoords = " + com.badlogic.gdx.graphics.glutils.ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
            + "   gl_Position =  u_projTrans2 *" + com.badlogic.gdx.graphics.glutils.ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
            + "}\n";

    public static final String fragment = "#ifdef GL_ES\n" //
            + "#define LOWP lowp\n" //
            + "precision mediump float;\n" //
            + "#else\n" //
            + "#define LOWP \n" //
            + "#endif\n" //
            + "struct PointLight {\n"
            + "    vec3 position;\n"
            + "    vec3 color;\n"
            + "    float intensity;\n"
            + "};\n"
            + "uniform float u_r;\n"
            + "uniform PointLight point_lights[5];\n"
            + "varying vec2 v_texCoords;\n" //
            + "uniform sampler2D u_texture;\n" //
            + "void main()\n"//
            + "{\n" //
            + "  gl_FragColor = point_lights[0].color.r * texture2D(u_texture, v_texCoords);\n" //
            + "}";

    public ExampleShaderProgram() {
        super(vertex, fragment);
    }

}
