package com.fos.game.engine.ecs.components.physics2d;

import com.badlogic.gdx.physics.box2d.JointDef;

public class Joint2DData {

    public final JointDef jointDef;

    public Joint2DData(JointDef jointDef) {
        this.jointDef = jointDef;
    }

}
