package com.fos.game.engine.ecs.components.physics2d;

import com.fos.game.engine.core.files.assets.GameAssetManager;
import com.fos.game.engine.core.files.serialization.JsonConverter;
import com.fos.game.engine.ecs.components.base.Factory;

public class FactoryJoint2D extends Factory {

    public FactoryJoint2D(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    public ComponentJoint2D create(final Joint2DData data) {
        return new ComponentJoint2D(data);
    }

}
