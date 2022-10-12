package com.fos.game.engine.ecs.components.camera2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.systems.renderer.base.RenderTarget;
import com.fos.game.engine.ecs.systems.renderer.shaders.postprocessing.PostProcessingEffect;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ComponentCamera2D implements Component {

    public final Set<Enum> layers;
    public final int layersBitMask;
    public OrthographicCamera lens;
    public final float viewWorldWidth;
    public final float viewWorldHeight;
    public final float pixelsPerMeterX;
    public final float pixelsPerMeterY;
    public RenderTarget renderTarget;
    public Array<PostProcessingEffect> postProcessingEffects;

    protected ComponentCamera2D(OrthographicCamera lens, final Enum[] layers, final RenderTarget.RenderTargetParams renderTargetParams, final PostProcessingEffect... postProcessingEffects) {
        this.layers = new HashSet<>();
        this.layers.addAll(Arrays.asList(layers));
        this.layersBitMask = UtilsCameras2D.computeRenderedLayersBitMask(layers);
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
