package com.fos.game.engine.ecs.components.cameras;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.GLFrameBuffer;
import com.fos.game.engine.core.graphics.g2d.RenderTarget;
import com.fos.game.engine.core.graphics.shaders.postprocessing.PostProcessingEffect;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

// TODO: add Camera2DData
public class ComponentCamera2D implements Component {

    public Set<Enum> layers;
    public int layersBitMask;
    public float depth;
    public OrthographicCamera lens;
    public float viewWorldWidth;
    public float viewWorldHeight;
    public float pixelsPerMeterX;
    public float pixelsPerMeterY;
    public PostProcessingEffect postProcessingEffect;

    public FrameBuffer frameBuffer; // <- internal hidden FrameBuffer. Only cameras with post processing effect (s) will use it.
    public RenderTarget renderTarget;

    protected ComponentCamera2D(OrthographicCamera lens, final Enum[] layers, float depth, final RenderTarget.RenderTargetParams renderTargetParams, final PostProcessingEffect postProcessingEffect) {
        this.layers = new HashSet<>();
        this.layers.addAll(Arrays.asList(layers));
        this.layersBitMask = UtilsCameras.computeRenderedLayersBitMask(layers);
        this.depth = depth;
        this.lens = lens;
        this.viewWorldWidth = lens.viewportWidth;
        this.viewWorldHeight = lens.viewportHeight;
        this.pixelsPerMeterX = (float) Gdx.graphics.getWidth() / lens.viewportWidth;
        this.pixelsPerMeterY = (float) Gdx.graphics.getHeight() / lens.viewportHeight;
        this.renderTarget = renderTargetParams == null ? null : new RenderTarget(renderTargetParams);
        this.postProcessingEffect = postProcessingEffect;

        buildFrameBuffer();
    }

    private void buildFrameBuffer() {
        final GLFrameBuffer.FrameBufferBuilder frameBufferBuilderScene = new GLFrameBuffer.FrameBufferBuilder(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        frameBufferBuilderScene.addColorTextureAttachment(GL30.GL_RGBA8, GL30.GL_RGBA, GL30.GL_UNSIGNED_BYTE); // <- regular
        //frameBufferBuilderScene.addDepthTextureAttachment(GL30.GL_DEPTH_COMPONENT , GL30.GL_UNSIGNED_BYTE);
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

    @Override
    public final ComponentType getComponentType() {
        return ComponentType.CAMERA_2D;
    }

}
