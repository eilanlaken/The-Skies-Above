package com.fos.game.engine.ecs.components.physics2d;

import com.fos.game.engine.core.files.assets.GameAssetManager;
import com.fos.game.engine.core.files.serialization.JsonConverter;
import com.fos.game.engine.ecs.components.base.Factory;

public class FactoryRigidBody2D extends Factory {

    public FactoryRigidBody2D(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    public ComponentBody2D create(final RigidBody2DData data) {
        return new ComponentBody2D(data);
    }

}
