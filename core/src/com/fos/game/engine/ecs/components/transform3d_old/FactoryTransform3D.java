package com.fos.game.engine.ecs.components.transform3d_old;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.fos.game.engine.ecs.components.base.Factory;
import com.fos.game.engine.core.files.assets.GameAssetManager;
import com.fos.game.engine.core.files.serialization.JsonConverter;

public class FactoryTransform3D extends Factory {

    public FactoryTransform3D(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    @Deprecated
    public static ComponentTransform3D create() {
        return new ComponentTransform3D(new Matrix4());
    }

    @Deprecated
    public static ComponentTransform3D create(Vector3 translation) {
        Matrix4 matrix4 = new Matrix4();
        matrix4.setTranslation(translation);
        ComponentTransform3D transform3D = new ComponentTransform3D(matrix4);
        return transform3D;
    }

    @Deprecated
    public static ComponentTransform3D create(Vector3 translation, Quaternion rotation) {
        Matrix4 matrix4 = new Matrix4();
        matrix4.setTranslation(translation);
        matrix4.rotate(rotation);
        ComponentTransform3D transform3D = new ComponentTransform3D(matrix4);
        return transform3D;
    }

}
