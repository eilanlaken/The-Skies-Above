package com.fos.game.engine.ecs.components.text;

import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

// TODO: implement.
public class ComponentText implements Component {

    @Override
    public final ComponentType getComponentType() {
        return ComponentType.GRAPHICS;
    }
}
