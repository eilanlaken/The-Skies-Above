package com.fos.game.engine.ecs.components.base;

public enum ComponentType {

    TRANSFORM_2D,
    TRANSFORM_3D,
    LIGHT_2D,
    LIGHT_3D,
    MODEL_INSTANCE,
    PHYSICS_2D_BODY,
    PHYSICS_2D_JOINT,
    PHYSICS_3D_BODY,
    SCRIPTS,
    @Deprecated CAMERA,
    CAMERA_2D,
    CAMERA_3D,
    ANIMATIONS_2D,
    ANIMATIONS_3D,
    SHAPE_2D,
    AUDIO,
    SIGNAL_BOX,
    TEXT,
    ;

    public final int bitMask;

    ComponentType() {
        this.bitMask = (short) (0b000001 << ordinal());
    }

}
