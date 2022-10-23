package com.fos.game.engine.ecs.components.transform;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

// TODO: use this as the transform component in the engine version 2.
public class ComponentTransform implements Component {

    public Matrix4 matrix;
    private final Quaternion quaternion = new Quaternion(); // <- used for internal calculations.

    protected ComponentTransform(Matrix4 matrix) {
        this.matrix = matrix;
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.TRANSFORM;
    }

    public float getRotationAroundAxis(float x, float y, float z) {
        matrix.getRotation(quaternion);
        return quaternion.getAngleAroundRad(x,y,z);
    }
}
