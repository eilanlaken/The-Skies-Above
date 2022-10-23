package com.fos.game.engine.ecs.components.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.GLFrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.fos.game.engine.core.graphics.g2d.RenderTarget;
import com.fos.game.engine.core.graphics.shaders.postprocessing.PostProcessingEffect;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

public class ComponentCamera implements Component {

    public Enum[] layers;
    public int layersBitMask;
    public float depth;
    public Camera lens;
    public float viewWorldWidth;
    public float viewWorldHeight;
    public PostProcessingEffect postProcessingEffect;
    public RenderTarget renderTarget;
    public FrameBuffer frameBuffer;

    protected ComponentCamera(Camera lens, final Enum[] layers, float depth, final RenderTarget.RenderTargetParams renderTargetParams, final PostProcessingEffect postProcessingEffect) {
        this.layers = layers;
        this.layersBitMask = UtilsCamera.computeRenderedLayersBitMask(layers);
        this.depth = depth;
        this.lens = lens;
        this.viewWorldWidth = lens.viewportWidth;
        this.viewWorldHeight = lens.viewportHeight;
        this.renderTarget = renderTargetParams == null ? null : new RenderTarget(renderTargetParams);
        this.postProcessingEffect = postProcessingEffect;
        buildFrameBuffer();
    }

    public void buildFrameBuffer() {
        final GLFrameBuffer.FrameBufferBuilder frameBufferBuilderScene = new GLFrameBuffer.FrameBufferBuilder(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        frameBufferBuilderScene.addColorTextureAttachment(GL30.GL_RGBA8, GL30.GL_RGBA, GL30.GL_UNSIGNED_BYTE);
        frameBuffer = frameBufferBuilderScene.build();
        for (Texture attached : frameBuffer.getTextureAttachments()) {
            attached.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
            attached.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
    }

    public boolean isRenderToTexture() {
        return renderTarget != null;
    }

    public Texture getRenderTargetTexture() {
        return renderTarget.getTexture();
    }

    public void setTransform(final Matrix4 transform) {

    }

    @Override
    public final ComponentType getComponentType() {
        return ComponentType.CAMERA_2D;
    }

}
