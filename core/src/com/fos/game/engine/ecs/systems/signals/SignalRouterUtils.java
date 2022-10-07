package com.fos.game.engine.ecs.systems.signals;

import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.signals.ComponentSignalEmitter;
import com.fos.game.engine.ecs.components.signals.Signal;
import com.fos.game.engine.ecs.entities.Entity;

public class SignalRouterUtils {

    protected static final short ENTITY_SIGNAL_EMITTER_BITMASK = ComponentType.SIGNAL_EMITTER.bitMask;
    protected static final short ENTITY_SIGNAL_RECEIVER_BITMASK = ComponentType.SIGNAL_RECEIVER.bitMask;

    protected static void prepare(final Array<Entity> entities,
                                  final Array<Entity> messageEmitters,
                                  final Array<Entity> messageReceivers,
                                  final Array<Signal> signals) {
        collectMessageEmittersEntities(entities, messageEmitters);
        collectMessageReceivingEntities(entities, messageReceivers);
        collectAllSignals(messageEmitters, signals);
    }

    private static void collectMessageEmittersEntities(final Array<Entity> entities, Array<Entity> messageEmittersResult) {
        messageEmittersResult.clear();
        for (final Entity entity : entities) {
            if ((entity.componentsBitMask & ENTITY_SIGNAL_EMITTER_BITMASK) > 0) {
                messageEmittersResult.add(entity);
            }
        }
    }

    private static void collectMessageReceivingEntities(final Array<Entity> entities, Array<Entity> messageReceiversResult) {
        messageReceiversResult.clear();
        for (final Entity entity : entities) {
            if ((entity.componentsBitMask & ENTITY_SIGNAL_RECEIVER_BITMASK) > 0) {
                messageReceiversResult.add(entity);
            }
        }
    }

    private static void collectAllSignals(final Array<Entity> messageEmitters, Array<Signal> signalsResult) {
        signalsResult.clear();
        for (final Entity entity : messageEmitters) {
            final ComponentSignalEmitter signalEmitter = (ComponentSignalEmitter) entity.components[ComponentType.SIGNAL_EMITTER.ordinal()];
            signalsResult.addAll(signalEmitter.sendSignals);
        }
    }
}
