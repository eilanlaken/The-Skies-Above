package com.fos.game.engine.components.signals;

import com.fos.game.engine.components.base.Factory;
import com.fos.game.engine.files.assets.GameAssetManager;
import com.fos.game.engine.files.serialization.JsonConverter;

public class FactorySignalReceiver extends Factory {

    public FactorySignalReceiver(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    public ComponentSignalReceiver create() {
        return new ComponentSignalReceiver();
    }

}
