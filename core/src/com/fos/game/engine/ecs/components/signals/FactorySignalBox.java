package com.fos.game.engine.ecs.components.signals;

import com.fos.game.engine.ecs.components.base.Factory;
import com.fos.game.engine.files.assets.GameAssetManager;
import com.fos.game.engine.files.serialization.JsonConverter;

public class FactorySignalBox extends Factory {

    public FactorySignalBox(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    public ComponentSignalBox create() {
        return new ComponentSignalBox();
    }

}
