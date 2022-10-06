package com.fos.game.engine.ecs.components.rigidbody2d;

import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

public class ComponentJoint2D implements Component {

    public Joint joint;

    protected ComponentJoint2D(final JointDef jointDef) {
        //World world;
        //joint = world.createJoint(jointDef);
    }

    @Override
    public final ComponentType getComponentType() {
        return ComponentType.PHYSICS_2D;
    }

}
