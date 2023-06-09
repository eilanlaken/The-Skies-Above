package com.fos.game.engine.ecs.entities;

import com.fos.game.engine.ecs.components.base.ComponentType;

class EntityUtils {

    protected static int computeBitMask(final Object[] components) {
        int mask = 0;
        for (int i = 0; i < components.length; i++) {
            Object component = components[i];
            if (component != null) mask |= ComponentType.values()[i].bitMask;
        }
        return mask;
    }

}
