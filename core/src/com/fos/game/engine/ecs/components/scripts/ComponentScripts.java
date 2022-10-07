package com.fos.game.engine.ecs.components.scripts;

import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

public class ComponentScripts extends Array<Script> implements Component {

    public ComponentScripts(Script ...scripts) {
        addAll(scripts);
    }

    @Override
    public final ComponentType getComponentType() {
        return ComponentType.SCRIPTS;
    }
}
