package com.fos.game.engine.ecs.components.signals;

import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

public class ComponentSignalReceiver implements Component {

    public Array<Signal> receivedSignals;

    protected ComponentSignalReceiver() {
        this.receivedSignals = new Array<>();
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.SIGNAL_RECEIVER;
    }
}
