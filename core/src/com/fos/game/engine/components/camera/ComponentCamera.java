package com.fos.game.engine.components.camera;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.components.base.Component;
import com.fos.game.engine.components.base.ComponentType;
import com.fos.game.engine.renderer.shaders.postprocessing.PostProcessingEffect;
import com.fos.game.engine.renderer.system.RenderTarget;

public class ComponentCamera implements Component {

    public final int layersBitMask;
    public Camera lens;
    public RenderTarget renderTarget;
    public Array<PostProcessingEffect> postProcessingEffects;

    protected ComponentCamera(Camera lens, final int layersBitMask, final RenderTarget.RenderTargetParams renderTargetParams, final PostProcessingEffect... postProcessingEffects) {
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
