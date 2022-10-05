package com.fos.game.engine.ecs.components.rigidbody3d;

import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

public class ComponentRigidBody3D extends btRigidBody implements Component {

    protected ComponentRigidBody3D(btRigidBody.btRigidBodyConstructionInfo constructionInfo) {
        super(constructionInfo);
    }

    @Override
    public final ComponentType getComponentType() {
        return ComponentType.PHYSICS_BODY_3D;
    }

}
