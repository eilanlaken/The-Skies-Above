package com.fos.game.engine.ecs.components.modelinstance_old;

import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

public class ComponentModelInstance implements Component {

    public final ModelInstance instance;

    protected ComponentModelInstance(final ModelInstance instance) {
        this.instance = instance;
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.GRAPHICS;
    }
}
