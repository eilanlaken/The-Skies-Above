package com.fos.game.engine.ecs.components.physics2d;

import com.badlogic.gdx.physics.box2d.Joint;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

public class ComponentJoint2D implements Component {

    public Joint2DData data;
    public Joint joint; // will be set from the system when entity is inserted.

    protected ComponentJoint2D(final Joint2DData data) {
        this.data = data;
    }

    @Override
    public final ComponentType getComponentType() {
        return ComponentType.PHYSICS_2D;
    }

}
