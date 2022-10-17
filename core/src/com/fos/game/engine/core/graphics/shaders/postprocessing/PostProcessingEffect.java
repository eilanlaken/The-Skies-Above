package com.fos.game.engine.core.graphics.shaders.postprocessing;


import com.fos.game.engine.core.graphics.shaders.base.Shader;

/** PostProcessingEffect(s) are unique shaders - the must contain
 a [uniform mat4 u_projTrans] and a [uniform sampler2D u_texture].
 */
public abstract class PostProcessingEffect extends Shader {

    public PostProcessingEffect(final String vertex, final String fragment) {
        super(vertex, fragment);
    }

}
