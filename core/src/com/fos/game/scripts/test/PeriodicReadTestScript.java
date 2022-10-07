package com.fos.game.scripts.test;

import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.scripts.Script;
import com.fos.game.engine.ecs.components.signals.ComponentSignalReceiver;
import com.fos.game.engine.ecs.components.signals.Signal;
import com.fos.game.engine.ecs.entities.Entity;

public class PeriodicReadTestScript extends Script {

    ComponentSignalReceiver signalReceiver;

    public PeriodicReadTestScript(final Entity entity) {
        super(entity);
    }

    @Override
    public void start() {
        signalReceiver = (ComponentSignalReceiver) entity.components[ComponentType.SIGNAL_RECEIVER.ordinal()];
    }

    @Override
    public void update(float delta) {
        for (Signal signal : signalReceiver.receivedSignals) {
            System.out.println(entity + " received " + signal);
        }
    }



}
