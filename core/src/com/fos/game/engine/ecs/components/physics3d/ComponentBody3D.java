package com.fos.game.engine.ecs.components.physics3d;

import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

public class ComponentBody3D extends btRigidBody implements Component {

    protected ComponentBody3D(btRigidBody.btRigidBodyConstructionInfo constructionInfo) {
        super(constructionInfo);
    }

    @Override
    public final ComponentType getComponentType() {
        return ComponentType.PHYSICS_3D;
    }

}
