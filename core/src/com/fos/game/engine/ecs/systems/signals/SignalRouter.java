package com.fos.game.engine.ecs.systems.signals;

import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.signals.ComponentSignalEmitter;
import com.fos.game.engine.ecs.components.signals.ComponentSignalReceiver;
import com.fos.game.engine.ecs.components.signals.Signal;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.systems.base.EntitiesProcessor;

public class SignalRouter implements EntitiesProcessor {

    private final Array<Entity> signalEmitters;
    private final Array<Entity> signalReceivers;
    private final Array<Signal> signals;

    public SignalRouter() {
        this.signalEmitters = new Array<>();
        this.signalReceivers = new Array<>();
        this.signals = new Array<>();
    }

    @Override
    public void process(final Array<Entity> entities) {
        SignalRouterUtils.prepare(entities, signalEmitters, signalReceivers, signals);
        // flush the receive queue for all signal receivers
        for (final Entity entity : signalReceivers) {
            final ComponentSignalReceiver signalReceiver = (ComponentSignalReceiver) entity.components[ComponentType.SIGNAL_RECEIVER.ordinal()];
            signalReceiver.receivedSignals.clear();
        }
        // refill receivers' signals array with updated signals
        for (final Signal signal : signals) {
            for (final Entity entity : signalReceivers) {
                if (!signal.isTarget(entity)) continue;
                final ComponentSignalReceiver signalReceiver = (ComponentSignalReceiver) entity.components[ComponentType.SIGNAL_RECEIVER.ordinal()];
                signalReceiver.receivedSignals.add(signal);
            }
        }
        // flush the send queue for all signal emitters
        for (final Entity entity : signalEmitters) {
            final ComponentSignalEmitter signalEmitter = (ComponentSignalEmitter) entity.components[ComponentType.SIGNAL_EMITTER.ordinal()];
            signalEmitter.sendSignals.clear();
        }
    }

    @Override
    // TODO: fix to make more efficient using bitwise operations.
    public boolean shouldProcess(Entity entity) {
        if ((entity.componentsBitMask & SignalRouterUtils.ENTITY_SIGNAL_EMITTER_BITMASK) > 0) return true;
        if ((entity.componentsBitMask & SignalRouterUtils.ENTITY_SIGNAL_RECEIVER_BITMASK) > 0) return true;
        return false;
    }
}
