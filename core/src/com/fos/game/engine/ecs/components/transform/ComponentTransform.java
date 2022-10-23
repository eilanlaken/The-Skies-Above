package com.fos.game.engine.ecs.components.transform;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

// TODO: use this as the transform component in the engine version 2.
public class ComponentTransform implements Component {

    public Matrix4 matrix;
    private final Quaternion quaternion = new Quaternion(); // <- used for internal calculations.
    private final Vector3 vector3 = new Vector3(); // <- used for internal calculations

    protected ComponentTransform(Matrix4 matrix) {
        this.matrix = matrix;
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.TRANSFORM;
    }

    public float getPoxitionX() {
        return matrix.val[Matrix4.M03];
    }

    public float getPositionY() {
        return matrix.val[Matrix4.M13];
    }

    public float getPositionZ() {
        return matrix.val[Matrix4.M23];
    }

    public float getRotationAroundAxis(float x, float y, float z) {
        matrix.getRotation(quaternion);
        return quaternion.getAngleAroundRad(x,y,z);
    }

    // TODO: test
    public Vector3 getDirection(Vector3 output) {
        output.x = matrix.val[Matrix4.M02];
        output.y = matrix.val[Matrix4.M12];
        output.z = matrix.val[Matrix4.M22];
        return output;
    }

    // TODO: test
    public Vector3 getUp(Vector3 output) {
        output.x = matrix.val[Matrix4.M01];
        output.y = matrix.val[Matrix4.M11];
        output.z = matrix.val[Matrix4.M21];
        return output;
    }
}
