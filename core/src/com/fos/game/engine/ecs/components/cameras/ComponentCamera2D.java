package com.fos.game.engine.ecs.components.cameras;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.systems.renderer_old.shaders.postprocessing.PostProcessingEffect;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ComponentCamera2D implements Component {

    public Set<Enum> layers;
    public int layersBitMask;
    public float depth;
    public OrthographicCamera lens;
    public float viewWorldWidth;
    public float viewWorldHeight;
    public float pixelsPerMeterX;
    public float pixelsPerMeterY;
    public RenderTarget renderTarget;
    public Array<PostProcessingEffect> postProcessingEffects;

    protected ComponentCamera2D(OrthographicCamera lens, final Enum[] layers, float depth, final RenderTarget.RenderTargetParams renderTargetParams, final PostProcessingEffect... postProcessingEffects) {
        this.layers = new HashSet<>();
        this.layers.addAll(Arrays.asList(layers));
        this.layersBitMask = UtilsCameras.computeRenderedLayersBitMask(layers);
        this.depth = depth;
        this.lens = lens;
        this.viewWorldWidth = lens.viewportWidth;
        this.viewWorldHeight = lens.viewportHeight;
        this.pixelsPerMeterX = (float) Gdx.graphics.getWidth() / lens.viewportWidth;
        this.pixelsPerMeterY = (float) Gdx.graphics.getHeight() / lens.viewportHeight;
        renderTarget = renderTargetParams == null ? null : new RenderTarget(renderTargetParams);
        this.postProcessingEffects = new Array<>(postProcessingEffects);
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
