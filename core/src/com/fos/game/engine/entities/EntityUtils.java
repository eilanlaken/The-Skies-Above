package com.fos.game.engine.entities;

import com.fos.game.engine.components.base.ComponentType;

class EntityUtils {

    protected static short computeBitMask(final Object[] components) {
        short mask = 0;
        for (int i = 0; i < components.length; i++) {
            Object component = components[i];
            if (component != null) mask |= ComponentType.values()[i].bitMask;
        }
        return mask;
    }

}
