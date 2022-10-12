package com.fos.game.engine.ecs.components.camera2d;

public class UtilsCameras2D {


    public static int computeRenderedLayersBitMask(final Enum... layers) {
        int bitMask = 0;
        for (Enum value : layers) {
            bitMask |= (int) Math.pow(2, value.ordinal());
        }
        return bitMask;
    }

}
