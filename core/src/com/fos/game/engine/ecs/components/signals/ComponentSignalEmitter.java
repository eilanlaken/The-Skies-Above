package com.fos.game.engine.ecs.components.signals;

import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

public class ComponentSignalEmitter implements Component {

    public Array<Signal> sendSignals;

    protected ComponentSignalEmitter() {
        this.sendSignals = new Array<>();
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.SIGNAL_EMITTER;
    }
}
