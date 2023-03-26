package com.fos.game.engine.ecs.components.base;

public enum ComponentType {

    TRANSFORM_2D,
    TRANSFORM_3D,
    PHYSICS_2D,
    PHYSICS_3D,
    LOGIC,
    GRAPHICS,
    AUDIO,
    SIGNALS,
    STORAGE,
    ;

    public final int bitMask;

    ComponentType() {
        this.bitMask = 0b000001 << ordinal();
    }

}
