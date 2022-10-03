package com.fos.game.engine.ecs.components.audio;

import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

public class ComponentMusic implements Component {

    @Override
    public final ComponentType getComponentType() {
        return ComponentType.AUDIO;
    }
}
