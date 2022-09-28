package com.fos.game.engine.components.signals;

import com.fos.game.engine.components.base.Factory;
import com.fos.game.engine.files.assets.GameAssetManager;

public class FactorySignalReceiver extends Factory {

    public FactorySignalReceiver(final GameAssetManager assetManager) {
        super(assetManager);
    }

    public ComponentSignalReceiver create() {
        return new ComponentSignalReceiver();
    }

}
