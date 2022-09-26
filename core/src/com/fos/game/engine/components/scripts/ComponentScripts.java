package com.fos.game.engine.components.scripts;

import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.components.base.Component;
import com.fos.game.engine.components.base.ComponentType;

public class ComponentScripts extends Array<Script> implements Component {

    public ComponentScripts(Script ...scripts) {
        super();
        addAll(scripts);
    }

    @Override
    public final ComponentType getComponentType() {
        return ComponentType.SCRIPTS;
    }
}
