package com.fos.game.engine.ecs.systems.signals;

import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.signals.ComponentSignalBox;
import com.fos.game.engine.ecs.components.signals.Signal;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.systems.base.EntitiesProcessor;

public class SignalRouter implements EntitiesProcessor {

    private final Array<Signal> signals;

    public SignalRouter() {
        this.signals = new Array<>();
    }

    @Override
    public void process(final Array<Entity> entities) {
        //System.out.println("entities: " + entities.size);

        SignalRouterUtils.collectAllUnsentSignals(entities, signals);
        // flush the send and receive queues for all entities
        for (final Entity entity : entities) {
            final ComponentSignalBox signalBox = (ComponentSignalBox) entity.components[ComponentType.SIGNAL_BOX.ordinal()];
            signalBox.signalsToSend.clear();
            signalBox.receivedSignals.clear();
        }
        // deliver all signals to their targets
        for (final Signal signal : signals) {
            for (final Entity entity : entities) {
                if (!signal.isTarget(entity)) continue;
                final ComponentSignalBox signalBox = (ComponentSignalBox) entity.components[ComponentType.SIGNAL_BOX.ordinal()];
                signalBox.receivedSignals.add(signal);
            }
        }
    }

    @Override
    public boolean shouldProcess(Entity entity) {
        return ((entity.componentsBitMask & SignalRouterUtils.ENTITY_SIGNALING_BITMASK) > 0);
    }
}
