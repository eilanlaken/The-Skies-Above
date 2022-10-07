package com.fos.game.scripts.test;

import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.scripts.Script;
import com.fos.game.engine.ecs.components.signals.ComponentSignalBox;
import com.fos.game.engine.ecs.components.signals.Signal;
import com.fos.game.engine.ecs.entities.Entity;

public class PeriodicSendTestScript extends Script {

    ComponentSignalBox signalBox;

    float elapsedTime = 0;

    public PeriodicSendTestScript(final Entity entity) {
        super(entity);
    }

    @Override
    public void start() {
        signalBox = (ComponentSignalBox) entity.components[ComponentType.SIGNAL_BOX.ordinal()];
    }

    @Override
    public void update(float delta) {
        elapsedTime += delta;
        if (elapsedTime >= 3) {
            elapsedTime = 0;
            signalBox.signalsToSend.add(new Signal("Hello" + elapsedTime) {
                @Override
                public boolean isTarget(Entity other) {
                    return  other != entity;
                }
            });
        }
    }



}
