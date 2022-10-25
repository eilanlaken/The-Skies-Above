package com.fos.game.engine.ecs.components.transform;

import com.badlogic.gdx.math.Matrix4;

public class UtilsTransform {

    public static Matrix4 getMatrix(Matrix4 matrix4, final ComponentTransform transform) {
        matrix4.idt();
        matrix4.translate(transform.position);
        matrix4.scl(transform.scale);
        matrix4.rotate(transform.rotation);
        return matrix4;
    }

}
