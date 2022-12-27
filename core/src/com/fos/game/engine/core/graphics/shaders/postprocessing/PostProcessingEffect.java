package com.fos.game.engine.core.graphics.shaders.postprocessing;


import com.fos.game.engine.core.graphics.shaders.base.Shader;

import java.util.HashMap;
import java.util.Set;

/**
 * PostProcessingEffect(s) are unique shaders - the must contain
 a [uniform mat4 u_projTrans] and a [uniform sampler2D u_texture].
 */
public class PostProcessingEffect extends Shader {

    private static final String[] REQUIRED_UNIFORMS = {"u_projTrans", "u_texture"};

    public PostProcessingEffect(final String vertex, final String fragment) {
        super(vertex, fragment);
        Set<String> uniformNames = uniformLocations.keySet();
        System.out.println(uniformNames);
        for (String requiredUniformName : REQUIRED_UNIFORMS) {
            if (!uniformNames.contains(requiredUniformName)) {
                throw new IllegalArgumentException("Error creating post processing effect shader: shader MUST include the following" +
                        " uniforms declarations: [sampler2D u_texture] and [mat4 u_projTrans]. Note: a declaration must be" +
                        "used to be registered.");
            }
        }
    }

}
