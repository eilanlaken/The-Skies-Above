package com.fos.game.engine.ecs.components.storage;

import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

public class ComponentStorage implements Component {

    @Override
    public ComponentType getComponentType() {
        return ComponentType.STORAGE;
    }

}
