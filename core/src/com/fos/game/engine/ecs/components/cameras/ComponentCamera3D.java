package com.fos.game.engine.ecs.components.cameras;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.systems.renderer_old.base.RenderTarget;
import com.fos.game.engine.ecs.systems.renderer_old.shaders.postprocessing.PostProcessingEffect;

public class ComponentCamera3D implements Component {

    public final int layersBitMask;
    public PerspectiveCamera lens;
    public RenderTarget renderTarget;
    public Array<PostProcessingEffect> postProcessingEffects;

    protected ComponentCamera3D(PerspectiveCamera lens, final int layersBitMask, final RenderTarget.RenderTargetParams renderTargetParams, final PostProcessingEffect... postProcessingEffects) {
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
        return ComponentType.CAMERA;
    }

}
