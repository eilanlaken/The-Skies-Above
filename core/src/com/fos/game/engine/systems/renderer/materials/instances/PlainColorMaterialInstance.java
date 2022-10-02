package com.fos.game.engine.systems.renderer.materials.instances;

import com.fos.game.engine.systems.renderer.materials.base.Material;
import com.fos.game.engine.systems.renderer.shaders.base.ShadingMethod;

public class PlainColorMaterialInstance extends Material {

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
