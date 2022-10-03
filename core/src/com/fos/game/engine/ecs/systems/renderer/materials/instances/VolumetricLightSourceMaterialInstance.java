package com.fos.game.engine.ecs.systems.renderer.materials.instances;

import com.fos.game.engine.ecs.systems.renderer.materials.base.Material;
import com.fos.game.engine.ecs.systems.renderer.shaders.base.ShadingMethod;

public class VolumetricLightSourceMaterialInstance extends Material {

    public final float r, g, b;

    public VolumetricLightSourceMaterialInstance(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public ShadingMethod getShadingMethod() {
        return ShadingMethod.VolumetricLightSourceColor;
    }
}
