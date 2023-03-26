package com.fos.game.engine.ecs.components.signals;

import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

public class ComponentSignalBox implements Component {

    public Array<Signal> signalsToSend;
    public Array<Signal> receivedSignals;
    public boolean active = true;

    protected ComponentSignalBox() {
        this.signalsToSend = new Array<>();
        this.receivedSignals = new Array<>();
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.SIGNALS;
    }
}
