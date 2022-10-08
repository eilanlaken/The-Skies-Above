package com.fos.game.engine.ecs.components.camera3d;

import com.badlogic.gdx.utils.Array;

public class UtilsCameras3D {

    public static int computeRenderedLayersBitMask(final Array<Enum> layers) {
        int bitMask = 0;
        for (Enum value : layers) {
            bitMask |= (int) Math.pow(2, value.ordinal());
        }
        return bitMask;
    }

    public static int computeRenderedLayersBitMask(final Enum... layers) {
        int bitMask = 0;
        for (Enum value : layers) {
            bitMask |= (int) Math.pow(2, value.ordinal());
        }
        return bitMask;
    }

}
