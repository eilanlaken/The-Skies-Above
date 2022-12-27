package com.fos.game.engine.core.graphics.shaders.postprocessing;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;

public class Example_PostProcessingEffectSetColor extends PostProcessingEffect {

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
            + "uniform sampler2D u_texture;\n" //
            + "uniform vec3 u_color;\n" //
            + "\n" //
            + "void main()\n"//
            + "{\n" //
            + "  gl_FragColor = texture2D(u_texture, vec2(0,0)) * 0 + vec4(u_color, 1.0);\n" //
            + "}";


    public Example_PostProcessingEffectSetColor() {
        super(vertex, fragment);
    }


}
