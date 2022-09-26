package com.fos.game.engine.signals;

import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.components.base.ComponentType;
import com.fos.game.engine.components.signals.ComponentSignalEmitter;
import com.fos.game.engine.components.signals.Signal;
import com.fos.game.engine.entities.Entity;

public class SignalRouterUtils {

    private static final short ENTITY_SIGNAL_EMITTER_BITMASK = ComponentType.SIGNAL_EMITTER.bitMask;
    private static final short ENTITY_SIGNAL_RECEIVER_BITMASK = ComponentType.SIGNAL_RECEIVER.bitMask;

    protected static void prepare(final Array<Entity> allEntities,
                                  final Array<Entity> messageEmitters,
                                  final Array<Entity> messageReceivers,
                                  final Array<Signal> signals) {
        collectMessageEmittersEntities(allEntities, messageEmitters);
        collectMessageReceivingEntities(allEntities, messageReceivers);
        collectAllSignals(messageEmitters, signals);
    }

    private static void collectMessageEmittersEntities(final Array<Entity> allEntities, Array<Entity> messageEmittersResult) {
        messageEmittersResult.clear();
        for (final Entity entity : allEntities) {
            if ((entity.componentsBitMask & ENTITY_SIGNAL_EMITTER_BITMASK) > 0) {
                messageEmittersResult.add(entity);
            }
        }
    }

    private static void collectMessageReceivingEntities(final Array<Entity> allEntities, Array<Entity> messageReceiversResult) {
        messageReceiversResult.clear();
        for (final Entity entity : allEntities) {
            if ((entity.componentsBitMask & ENTITY_SIGNAL_RECEIVER_BITMASK) > 0) {
                messageReceiversResult.add(entity);
            }
        }
    }

    private static void collectAllSignals(final Array<Entity> messageEmitters, Array<Signal> signalsResult) {
        for (final Entity entity : messageEmitters) {
            final ComponentSignalEmitter signalEmitter = (ComponentSignalEmitter) entity.components[ComponentType.SIGNAL_EMITTER.ordinal()];
            signalsResult.addAll(signalEmitter.sendQueue);
        }
    }
}
