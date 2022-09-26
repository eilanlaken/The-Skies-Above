package com.fos.game.engine.components.modelinstance;

import com.fos.game.engine.components.base.Component;
import com.fos.game.engine.components.base.ComponentType;

public class ComponentModelInstance implements Component {

    public final ModelInstance instance;

    protected ComponentModelInstance(final ModelInstance instance) {
        this.instance = instance;
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.MODEL_INSTANCE;
    }
}
