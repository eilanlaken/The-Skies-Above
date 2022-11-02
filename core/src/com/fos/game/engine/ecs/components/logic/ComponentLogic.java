package com.fos.game.engine.ecs.components.logic;

import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

public class ComponentLogic extends Array<Logic> implements Component {

    public boolean active = true;

    public ComponentLogic(Logic... logics) {
        addAll(logics);
    }

    @Override
    public final ComponentType getComponentType() {
        return ComponentType.SCRIPTS;
    }
}
