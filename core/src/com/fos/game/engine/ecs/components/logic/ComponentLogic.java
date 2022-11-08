package com.fos.game.engine.ecs.components.logic;

import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

// TODO: change to get rid of the extends.
public class ComponentLogic implements Component {

    public Array<Logic> logic;
    public boolean active = true;

    public ComponentLogic(Logic... logic) {
        this.logic = new Array<>();
        this.logic.addAll(logic);
    }

    @Override
    public final ComponentType getComponentType() {
        return ComponentType.LOGIC;
    }
}
