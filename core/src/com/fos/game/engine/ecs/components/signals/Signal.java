package com.fos.game.engine.ecs.components.signals;

import com.fos.game.engine.ecs.entities.Entity;

public abstract class Signal {

    public final Entity source;
    public final Object message;

    protected Signal(final Entity source, final Object message) {
        this.source = source;
        this.message = message;
    }

    public abstract boolean isTarget(final Entity entity);
}
