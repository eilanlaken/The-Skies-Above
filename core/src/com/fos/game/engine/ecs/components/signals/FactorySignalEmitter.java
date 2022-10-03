package com.fos.game.engine.ecs.components.signals;

import com.fos.game.engine.ecs.components.base.Factory;
import com.fos.game.engine.files.assets.GameAssetManager;
import com.fos.game.engine.files.serialization.JsonConverter;

public class FactorySignalEmitter extends Factory {

    public FactorySignalEmitter(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    public ComponentSignalEmitter create() {
        return new ComponentSignalEmitter();
    }

}
