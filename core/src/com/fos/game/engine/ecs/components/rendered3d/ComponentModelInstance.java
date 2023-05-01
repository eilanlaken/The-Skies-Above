package com.fos.game.engine.ecs.components.rendered3d;

import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

public class ComponentModelInstance implements Component {

    @Override
    public ComponentType getComponentType() {
        return ComponentType.GRAPHICS;
    }
}
