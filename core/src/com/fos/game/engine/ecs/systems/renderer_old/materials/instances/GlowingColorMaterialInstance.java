package com.fos.game.engine.ecs.systems.renderer_old.materials.instances;

import com.fos.game.engine.ecs.systems.renderer_old.materials.base.Material;
import com.fos.game.engine.ecs.systems.renderer_old.shaders.base.ShadingMethod;

public class GlowingColorMaterialInstance extends Material {

    public float r, g, b;
    public float intensity;

    public GlowingColorMaterialInstance(float r, float g, float b, float intensity) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.intensity = intensity;
    }

    public GlowingColorMaterialInstance(float r, float g, float b) {
        this(r, g, b,1);
    }

    @Override
    public ShadingMethod getShadingMethod() {
        return ShadingMethod.UniformColorGlow;
    }
}
