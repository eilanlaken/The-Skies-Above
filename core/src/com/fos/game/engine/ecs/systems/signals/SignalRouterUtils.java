package com.fos.game.engine.ecs.systems.signals;

import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.signals.ComponentSignalBox;
import com.fos.game.engine.ecs.components.signals.Signal;
import com.fos.game.engine.ecs.entities.Entity;

public class SignalRouterUtils {

    protected static final int ENTITY_SIGNALING_BITMASK = ComponentType.SIGNALS.bitMask;

    protected static void collectAllUnsentSignals(final Array<Entity> entities, final Array<Signal> signalsResult) {
        signalsResult.clear();
        for (final Entity entity : entities) {
            final ComponentSignalBox signalBox = (ComponentSignalBox) entity.components[ComponentType.SIGNALS.ordinal()];
            if (!signalBox.active) continue;
            signalsResult.addAll(signalBox.signalsToSend);
        }
    }

}
