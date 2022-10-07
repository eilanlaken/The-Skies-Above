package com.fos.game.scripts.test;

import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.scripts.Script;
import com.fos.game.engine.ecs.components.signals.ComponentSignalEmitter;
import com.fos.game.engine.ecs.components.signals.Signal;
import com.fos.game.engine.ecs.entities.Entity;

public class PeriodicSendTestScript extends Script {

    ComponentSignalEmitter signalEmitter;

    float elapsedTime = 0;

    public PeriodicSendTestScript(final Entity entity) {
        super(entity);
    }

    @Override
    public void start() {
        signalEmitter = (ComponentSignalEmitter) entity.components[ComponentType.SIGNAL_EMITTER.ordinal()];
    }

    @Override
    public void update(float delta) {
        elapsedTime += delta;
        if (elapsedTime >= 3) {
            elapsedTime = 0;
            signalEmitter.sendSignals.add(new Signal("Hello" + elapsedTime) {
                @Override
                public boolean isTarget(Entity other) {
                    return  other != entity;
                }
            });
        }
    }



}
