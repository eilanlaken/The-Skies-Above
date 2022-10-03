package com.fos.game.engine.ecs.systems.renderer.materials.instances;

import com.fos.game.engine.ecs.systems.renderer.materials.base.Material;
import com.fos.game.engine.ecs.systems.renderer.shaders.base.ShadingMethod;

public class UniformColorMaterialInstance extends Material {

    public final float shineDamper;
    public final float reflectivity;
    public final float ambient;

    public final float r;
    public final float g;
    public final float b;

    public UniformColorMaterialInstance(float r, float g, float b, float shineDamper, float reflectivity, float ambient) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.shineDamper = shineDamper;
        this.reflectivity = reflectivity;
        this.ambient = ambient;
    }

    @Override
    public ShadingMethod getShadingMethod() {
        return ShadingMethod.UniformColor;
    }

}
