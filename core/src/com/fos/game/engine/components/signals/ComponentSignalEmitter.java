package com.fos.game.engine.components.signals;

import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.components.base.Component;
import com.fos.game.engine.components.base.ComponentType;

public class ComponentSignalEmitter implements Component {

    public Array<Signal> sendQueue;

    protected ComponentSignalEmitter() {
        this.sendQueue = new Array<>();
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.SIGNAL_EMITTER;
    }
}
