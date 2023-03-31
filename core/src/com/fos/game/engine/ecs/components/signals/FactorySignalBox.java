package com.fos.game.engine.ecs.components.signals;

import com.fos.game.engine.core.files.assets.GameAssetManager;
import com.fos.game.engine.core.files.serialization.JsonConverter;
import com.fos.game.engine.ecs.components.base.Factory;

public class FactorySignalBox extends Factory {

    public FactorySignalBox(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    public ComponentSignalBox create() {
        return new ComponentSignalBox();
    }

}
