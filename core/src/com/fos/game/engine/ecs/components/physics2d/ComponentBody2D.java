package com.fos.game.engine.ecs.components.physics2d;

import com.badlogic.gdx.physics.box2d.Body;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

public class ComponentBody2D implements Component {

    public Body2DData data;
    public Body body; // will be set from the system when entity is inserted.
    public boolean active = true;

    protected ComponentBody2D(final Body2DData data) {
        this.data = data;
    }

    @Deprecated
    protected ComponentBody2D(final Body body) {
        this.body = body;
    }

    @Override
    public final ComponentType getComponentType() {
        return ComponentType.PHYSICS_2D;
    }
}
