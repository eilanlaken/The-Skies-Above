package com.fos.game.engine.core.graphics.g2d;

import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.GLFrameBuffer;

public class RenderTarget {

    public final FrameBuffer targetFrameBuffer;

    public RenderTarget(final RenderTargetParams params) {
        final GLFrameBuffer.FrameBufferBuilder primaryFrameBufferBuilder = new GLFrameBuffer.FrameBufferBuilder(params.resolutionX, params.resolutionY);
        primaryFrameBufferBuilder.addColorTextureAttachment(GL30.GL_RGBA8, GL30.GL_RGBA, GL30.GL_UNSIGNED_BYTE); // <- regular
        primaryFrameBufferBuilder.addDepthTextureAttachment(GL30.GL_DEPTH_COMPONENT, GL30.GL_UNSIGNED_BYTE); // <- depth
        targetFrameBuffer = primaryFrameBufferBuilder.build();
        for (Texture attached : targetFrameBuffer.getTextureAttachments()) {
            attached.setWrap(params.textureWrapX, params.textureWrapY);
            attached.setFilter(params.textureFilterX, params.textureFilterY);
        }
    }

    public Texture getTexture() {
        return targetFrameBuffer.getColorBufferTexture();
    }

    public static class RenderTargetParams {

        public final int resolutionX;
        public final int resolutionY;
        public final Texture.TextureWrap textureWrapX;
        public final Texture.TextureWrap textureWrapY;
        public final Texture.TextureFilter textureFilterX;
        public final Texture.TextureFilter textureFilterY;

        public RenderTargetParams(final int resolutionX,
                                  final int resolutionY,
                                  final Texture.TextureWrap textureWrapX,
                                  final Texture.TextureWrap textureWrapY,
                                  final Texture.TextureFilter textureFilterX,
                                  final Texture.TextureFilter textureFilterY) {
            this.resolutionX = resolutionX;
            this.resolutionY = resolutionY;
            this.textureWrapX = textureWrapX;
            this.textureWrapY = textureWrapY;
            this.textureFilterX = textureFilterX;
            this.textureFilterY = textureFilterY;
        }

    }

}
