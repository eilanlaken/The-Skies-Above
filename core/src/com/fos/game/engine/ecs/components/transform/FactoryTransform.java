package com.fos.game.engine.ecs.components.transform;

import com.fos.game.engine.core.files.assets.GameAssetManager;
import com.fos.game.engine.core.files.serialization.JsonConverter;
import com.fos.game.engine.ecs.components.base.Factory;

public class FactoryTransform extends Factory {

    public FactoryTransform(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    public ComponentTransform2D create2d(float x, float y, float z, float scaleX, float scaleY, float angle) {
        return new ComponentTransform2D(x, y, z, scaleX, scaleY, angle);
    }

}
