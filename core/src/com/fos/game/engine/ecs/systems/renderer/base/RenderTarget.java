package com.fos.game.engine.ecs.systems.renderer.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.GLFrameBuffer;
import com.fos.game.engine.ecs.systems.renderer.shaders.postprocessing.PostProcessingShaderProgram;

public class RenderTarget {

    // TODO: change to protected.
    public final FrameBuffer secondaryFrameBuffer;
    public final FrameBuffer primaryFrameBuffer;
    public final PostProcessingShaderProgram postProcessingShaderProgram;

    public RenderTarget(final RenderTargetParams params) {
        // build "behind the scenes" frame buffer
        final GLFrameBuffer.FrameBufferBuilder secondaryFrameBufferBuilder = new GLFrameBuffer.FrameBufferBuilder(params.resolutionX, params.resolutionY);
        secondaryFrameBufferBuilder.addColorTextureAttachment(GL30.GL_RGBA8, GL30.GL_RGBA, GL30.GL_UNSIGNED_BYTE); // <- regular
        secondaryFrameBufferBuilder.addColorTextureAttachment(GL30.GL_RGBA8, GL30.GL_RGBA, GL30.GL_UNSIGNED_BYTE); // <- bloom
        secondaryFrameBufferBuilder.addColorTextureAttachment(GL30.GL_RGBA8, GL30.GL_RGBA, GL30.GL_UNSIGNED_BYTE); // <- black silhouette
        secondaryFrameBufferBuilder.addDepthTextureAttachment(GL30.GL_DEPTH_COMPONENT, GL30.GL_UNSIGNED_BYTE); // <- depth
        secondaryFrameBuffer = secondaryFrameBufferBuilder.build();
        for (Texture attached : secondaryFrameBuffer.getTextureAttachments()) {
            attached.setWrap(params.textureWrapX, params.textureWrapY);
            attached.setFilter(params.textureFilterX, params.textureFilterY);
        }

        // build primary frame buffer - the final image will be drawn to this frame buffer
        final GLFrameBuffer.FrameBufferBuilder primaryFrameBufferBuilder = new GLFrameBuffer.FrameBufferBuilder(params.resolutionX, params.resolutionY);
        primaryFrameBufferBuilder.addColorTextureAttachment(GL30.GL_RGBA8, GL30.GL_RGBA, GL30.GL_UNSIGNED_BYTE); // <- regular
        primaryFrameBuffer = primaryFrameBufferBuilder.build();
        for (Texture attached : primaryFrameBuffer.getTextureAttachments()) {
            attached.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
            attached.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        }

        postProcessingShaderProgram = new PostProcessingShaderProgram();
    }

    public Texture getTexture() {
        return primaryFrameBuffer.getColorBufferTexture();
    }

    public static class RenderTargetParams {

        public static final Texture.TextureWrap DEFAULT_TEXTURE_WRAP = Texture.TextureWrap.ClampToEdge;
        public static final Texture.TextureFilter DEFAULT_TEXTURE_FILTER = Texture.TextureFilter.Linear;
        public static final RenderTargetParams DEFAULT_RENDER_TARGET_PARAMS = new RenderTargetParams(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), DEFAULT_TEXTURE_WRAP, DEFAULT_TEXTURE_WRAP, DEFAULT_TEXTURE_FILTER, DEFAULT_TEXTURE_FILTER);

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
