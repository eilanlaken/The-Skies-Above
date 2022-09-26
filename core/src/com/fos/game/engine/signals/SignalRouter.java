package com.fos.game.engine.signals;

import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.components.base.ComponentType;
import com.fos.game.engine.components.signals.ComponentSignalEmitter;
import com.fos.game.engine.components.signals.ComponentSignalReceiver;
import com.fos.game.engine.components.signals.Signal;
import com.fos.game.engine.entities.Entity;
import com.fos.game.engine.entities.EntityContainer;

public class SignalRouter {

    private final Array<Entity> signalEmitters;
    private final Array<Entity> signalReceivers;
    private final Array<Signal> signals;

    public SignalRouter() {
        this.signalEmitters = new Array<>();
        this.signalReceivers = new Array<>();
        this.signals = new Array<>();
    }

    public void routeSignals(final EntityContainer entityContainer) {
        SignalRouterUtils.prepare(entityContainer.entities, signalEmitters, signalReceivers, signals);
        // flush the receive queue for all signal receivers
        for (final Entity entity : signalReceivers) {
            final ComponentSignalReceiver signalReceiver = (ComponentSignalReceiver) entity.components[ComponentType.SIGNAL_RECEIVER.ordinal()];
            signalReceiver.receivedQueue.clear();
        }
        // refill receivers' signals array with updated signals
        for (final Signal signal : signals) {
            for (final Entity entity : signalReceivers) {
                if (!signal.isTarget(entity)) continue;
                final ComponentSignalReceiver signalReceiver = (ComponentSignalReceiver) entity.components[ComponentType.SIGNAL_RECEIVER.ordinal()];
                signalReceiver.receivedQueue.add(signal);
            }
        }
        // flush the send queue for all signal emitters
        for (final Entity entity : signalEmitters) {
            final ComponentSignalEmitter signalEmitter = (ComponentSignalEmitter) entity.components[ComponentType.SIGNAL_EMITTER.ordinal()];
            signalEmitter.sendQueue.clear();
        }
    }

}
