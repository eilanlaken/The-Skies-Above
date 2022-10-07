package com.fos.game.engine.ecs.components.lights2d;

import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

public class ComponentLight2D implements Component {

    @Override
    public ComponentType getComponentType() {
        return ComponentType.LIGHT_2D;
    }
}
