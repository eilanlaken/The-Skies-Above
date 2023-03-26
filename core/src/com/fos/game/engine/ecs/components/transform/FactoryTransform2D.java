package com.fos.game.engine.ecs.components.transform;

import com.fos.game.engine.core.files.assets.GameAssetManager;
import com.fos.game.engine.core.files.serialization.JsonConverter;
import com.fos.game.engine.ecs.components.base.Factory;
import com.fos.game.engine.ecs.entities.Entity;

public class FactoryTransform2D extends Factory {

    public FactoryTransform2D(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    public ComponentTransform2D create2d(float x, float y, float z, float scaleX, float scaleY, float angle) {
        return new ComponentTransform2D(x, y, z, scaleX, scaleY, angle, false, null);
    }

    public ComponentTransform2D create2d(float x, float y, float z, float scaleX, float scaleY, float angle, boolean isStatic, Entity entity) {
        return new ComponentTransform2D(x, y, z, scaleX, scaleY, angle, isStatic, entity);
    }

}
