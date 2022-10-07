package com.fos.game.scripts.test;

import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.scripts.Script;
import com.fos.game.engine.ecs.components.signals.ComponentSignalBox;
import com.fos.game.engine.ecs.components.signals.Signal;
import com.fos.game.engine.ecs.entities.Entity;

public class PeriodicReadTestScript extends Script {

    ComponentSignalBox signalBox;

    public PeriodicReadTestScript(final Entity entity) {
        super(entity);
    }

    @Override
    public void start() {
        signalBox = (ComponentSignalBox) entity.components[ComponentType.SIGNAL_BOX.ordinal()];
    }

    @Override
    public void update(float delta) {
        for (Signal signal : signalBox.receivedSignals) {
            System.out.println(entity + " received " + signal);
        }
    }



}
