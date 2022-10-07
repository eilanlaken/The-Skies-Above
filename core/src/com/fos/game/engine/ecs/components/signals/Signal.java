package com.fos.game.engine.ecs.components.signals;

import com.fos.game.engine.ecs.entities.Entity;

public abstract class Signal {

    public final Object message;

    protected Signal(final Object message) {
        this.message = message;
    }

    public abstract boolean isTarget(final Entity entity);
}
