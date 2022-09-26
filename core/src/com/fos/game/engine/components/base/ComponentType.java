package com.fos.game.engine.components.base;

public enum ComponentType {

    TRANSFORM_2D,
    TRANSFORM_3D,
    LIGHT,
    MODEL_INSTANCE,
    PHYSICS_BODY_3D,
    PHYSICS_BODY_2D,
    SCRIPTS,
    CAMERA,
    ANIMATION,
    SHAPE_2D,
    SOUNDS,
    SIGNAL_EMITTER,
    SIGNAL_RECEIVER,
    ;

    public final short bitMask;

    ComponentType() {
        this.bitMask = (short) (0b000001 << ordinal());
    }

}
