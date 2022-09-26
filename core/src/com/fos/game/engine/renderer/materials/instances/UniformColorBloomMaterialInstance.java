package com.fos.game.engine.renderer.materials.instances;

import com.fos.game.engine.renderer.materials.base.FOSMaterial;
import com.fos.game.engine.renderer.shaders.base.ShadingMethod;

public class UniformColorBloomMaterialInstance extends FOSMaterial {

    public final float shineDamper;
    public final float reflectivity;
    public final float ambient;

    public final float r;
    public final float g;
    public final float b;
    public float intensity;

    public UniformColorBloomMaterialInstance(float r, float g, float b, float intensity, float shineDamper, float reflectivity, float ambient) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.intensity = intensity;
        this.shineDamper = shineDamper;
        this.reflectivity = reflectivity;
        this.ambient = ambient;
    }

    @Override
    public ShadingMethod getShadingMethod() {
        return ShadingMethod.UniformColorBloom;
    }

}
