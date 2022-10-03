package com.fos.game.engine.ecs.components.transform;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.fos.game.engine.ecs.components.base.Factory;
import com.fos.game.engine.files.assets.GameAssetManager;
import com.fos.game.engine.files.serialization.JsonConverter;

public class FactoryTransform3D extends Factory {

    public FactoryTransform3D(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    @Deprecated
    public static ComponentTransform3D create() {
        return new ComponentTransform3D();
    }

    @Deprecated
    public static ComponentTransform3D create(Vector3 translation) {
        ComponentTransform3D transform3D = new ComponentTransform3D();
        transform3D.translate(translation);
        return transform3D;
    }

    @Deprecated
    public static ComponentTransform3D create(Vector3 translation, Quaternion rotation) {
        ComponentTransform3D transform3D = new ComponentTransform3D();
        transform3D.translate(translation).rotate(rotation);
        return transform3D;
    }

}
