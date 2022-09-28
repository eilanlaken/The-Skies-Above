package com.fos.game.engine.components.signals;

import com.fos.game.engine.components.base.Factory;
import com.fos.game.engine.files.assets.GameAssetManager;

public class FactorySignalEmitter extends Factory {

    public FactorySignalEmitter(final GameAssetManager assetManager) {
        super(assetManager);
    }

    public ComponentSignalEmitter create() {
        return new ComponentSignalEmitter();
    }

}
