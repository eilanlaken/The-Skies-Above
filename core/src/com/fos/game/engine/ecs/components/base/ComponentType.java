package com.fos.game.engine.ecs.components.base;

public enum ComponentType {

    TRANSFORM,
    PHYSICS_2D_BODY,
    PHYSICS_2D_JOINT,
    PHYSICS_3D_BODY,
    SCRIPTS,
    GRAPHICS,
    AUDIO,
    SIGNAL_BOX,
    STORAGE,
    ;

    public final int bitMask;

    ComponentType() {
        this.bitMask = 0b000001 << ordinal();
    }

}
