package com.fos.game.engine.renderer.materials.instances;

import com.fos.game.engine.renderer.materials.base.FOSMaterial;
import com.fos.game.engine.renderer.shaders.base.ShadingMethod;

public class PlainColorMaterialInstance extends FOSMaterial {

    public final float r, g, b;

    public PlainColorMaterialInstance(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public ShadingMethod getShadingMethod() {
        return ShadingMethod.PlainColor;
    }
}
