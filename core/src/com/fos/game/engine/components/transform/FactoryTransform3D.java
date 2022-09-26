package com.fos.game.engine.components.transform;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class FactoryTransform3D {

    public static ComponentTransform3D create() {
        return new ComponentTransform3D();
    }

    public static ComponentTransform3D create(Vector3 translation) {
        ComponentTransform3D transform3D = new ComponentTransform3D();
        transform3D.translate(translation);
        return transform3D;
    }

    public static ComponentTransform3D create(Vector3 translation, Quaternion rotation) {
        ComponentTransform3D transform3D = new ComponentTransform3D();
        transform3D.translate(translation).rotate(rotation);
        return transform3D;
    }

}
