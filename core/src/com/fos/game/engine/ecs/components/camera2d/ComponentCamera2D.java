package com.fos.game.engine.ecs.components.camera2d;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.systems.renderer.base.RenderTarget;
import com.fos.game.engine.ecs.systems.renderer.shaders.postprocessing.PostProcessingEffect;

public class ComponentCamera2D implements Component {

    public final int layersBitMask;
    public OrthographicCamera lens;
    public RenderTarget renderTarget;
    public Array<PostProcessingEffect> postProcessingEffects;

    protected ComponentCamera2D(OrthographicCamera lens, final int layersBitMask, final RenderTarget.RenderTargetParams renderTargetParams, final PostProcessingEffect... postProcessingEffects) {
        this.layersBitMask = layersBitMask;
        this.lens = lens;
        renderTarget = renderTargetParams == null ? null : new RenderTarget(renderTargetParams);
        this.postProcessingEffects = new Array<>(postProcessingEffects);
    }

    public boolean renderToTexture() {
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
