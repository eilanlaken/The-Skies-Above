package com.fos.game.engine.ecs.components.base;

public enum ComponentType {

    TRANSFORM,
    TRANSFORM_2D, // <- TODO: deprecate later
    TRANSFORM_3D, // <- TODO: deprecate later
    PHYSICS_2D_BODY,
    PHYSICS_2D_JOINT,
    PHYSICS_3D_BODY,
    SCRIPTS,
    CAMERA,
    @Deprecated CAMERA_2D,
    @Deprecated CAMERA_3D,
    GRAPHICS,
    AUDIO,
    SIGNAL_BOX,
    TEXT,
    ;

    public final int bitMask;

    ComponentType() {
        this.bitMask = 0b000001 << ordinal();
    }

}
