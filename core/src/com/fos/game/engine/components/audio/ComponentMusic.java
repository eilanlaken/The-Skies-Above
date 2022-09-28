package com.fos.game.engine.components.audio;

import com.fos.game.engine.components.base.Component;
import com.fos.game.engine.components.base.ComponentType;

public class ComponentMusic implements Component {

    @Override
    public final ComponentType getComponentType() {
        return ComponentType.AUDIO;
    }
}
