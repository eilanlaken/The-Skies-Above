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
    ANIMATIONS_2D,
    ANIMATIONS_3D,
    SHAPE_2D,
    AUDIO,
    SIGNAL_EMITTER,
    SIGNAL_RECEIVER,
    TEXT,
    ;

    public final short bitMask;

    ComponentType() {
        this.bitMask = (short) (0b000001 << ordinal());
    }

}
