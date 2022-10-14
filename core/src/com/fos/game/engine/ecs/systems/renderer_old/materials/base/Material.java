package com.fos.game.engine.ecs.systems.renderer_old.materials.base;

import com.fos.game.engine.ecs.systems.renderer_old.shaders.base.ShadingMethod;

public abstract class Material implements Comparable<Material> {

    @Override
    public int compareTo(Material o) {
        if (this.getShadingMethod().ordinal() > o.getShadingMethod().ordinal()) {
            return 1;
        } else if (this.getShadingMethod().ordinal() < o.getShadingMethod().ordinal()) {
            return -1;
        }
        return 0;
    }

    public abstract ShadingMethod getShadingMethod();

}