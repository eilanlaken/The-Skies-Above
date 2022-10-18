package com.fos.game.engine.ecs.components.cameras;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
    public RenderTarget renderTarget;

    protected ComponentCamera2D(OrthographicCamera lens, final Enum[] layers, float depth, final RenderTarget.RenderTargetParams renderTargetParams, final PostProcessingEffect postProcessingEffect) {
        this.layers = new HashSet<>();
        this.layers.addAll(Arrays.asList(layers));
        this.layersBitMask = UtilsCameras.computeRenderedLayersBitMask(layers);
        this.depth = depth;
        this.lens = lens;
        this.viewWorldWidth = lens.viewportWidth;
        this.viewWorldHeight = lens.viewportHeight;
        System.out.println("view world width and height: " + this.viewWorldWidth + "," + this.viewWorldHeight);
        this.pixelsPerMeterX = (float) Gdx.graphics.getWidth() / lens.viewportWidth;
        this.pixelsPerMeterY = (float) Gdx.graphics.getHeight() / lens.viewportHeight;
        this.renderTarget = renderTargetParams == null ? null : new RenderTarget(renderTargetParams);
        this.postProcessingEffect = postProcessingEffect;
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
