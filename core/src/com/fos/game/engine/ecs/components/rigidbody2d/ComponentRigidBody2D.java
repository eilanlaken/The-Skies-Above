package com.fos.game.engine.ecs.components.rigidbody2d;

import com.badlogic.gdx.physics.box2d.Body;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

public class ComponentRigidBody2D implements Component {

    public RigidBody2DData data;
    public Body body;

    protected ComponentRigidBody2D(final RigidBody2DData data) {
        this.data = data;
    }

    @Deprecated
    protected ComponentRigidBody2D(final Body body) {
        this.body = body;
    }

    @Override
    public final ComponentType getComponentType() {
        return ComponentType.PHYSICS_2D;
    }
}
