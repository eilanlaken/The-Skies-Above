package com.fos.game.engine.renderer.materials.base;

import com.fos.game.engine.renderer.shaders.base.ShadingMethod;

public abstract class FOSMaterial implements Comparable<FOSMaterial> {

    @Override
    public int compareTo(FOSMaterial o) {
        if (this.getShadingMethod().ordinal() > o.getShadingMethod().ordinal()) {
            return 1;
        } else if (this.getShadingMethod().ordinal() < o.getShadingMethod().ordinal()) {
            return -1;
        }
        return 0;
    }

    public abstract ShadingMethod getShadingMethod();

}
