package com.fos.game.engine.ecs.components.transform3d;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class UtilsTransform3D {

    public static Matrix4 getMatrix(Matrix4 matrix4, final Vector3 position, Vector3 scale, Quaternion rotation) {
        matrix4.idt();
        matrix4.translate(position);
        matrix4.scl(scale);
        matrix4.rotate(rotation);
        return matrix4;
    }

}
