package com.fos.game.engine.context;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;

import java.nio.IntBuffer;

public class SystemQueries {

    public void getMaxTextureSize() {
        IntBuffer buffer = BufferUtils.newIntBuffer(16);
        Gdx.gl.glGetIntegerv(GL20.GL_MAX_TEXTURE_SIZE, buffer);
    }

}
