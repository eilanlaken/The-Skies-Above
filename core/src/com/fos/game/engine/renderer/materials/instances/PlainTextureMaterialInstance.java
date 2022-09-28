package com.fos.game.engine.renderer.materials.instances;

import com.badlogic.gdx.graphics.Texture;
import com.fos.game.engine.renderer.materials.base.Material;
import com.fos.game.engine.renderer.materials.base.UseTextureMaterial;
import com.fos.game.engine.renderer.shaders.base.ShadingMethod;

public class PlainTextureMaterialInstance extends Material implements UseTextureMaterial {

    public final Texture texture;

    public PlainTextureMaterialInstance(final Texture texture) {
        this.texture = texture;
    }

    @Override
    public ShadingMethod getShadingMethod() {
        return null;
    }

    @Override
    public int getGLTextureHandle() {
        return texture.getTextureObjectHandle();
    }
}
