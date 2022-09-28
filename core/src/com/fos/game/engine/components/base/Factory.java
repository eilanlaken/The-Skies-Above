package com.fos.game.engine.components.base;

import com.fos.game.engine.files.assets.GameAssetManager;

public abstract class Factory {

    protected final GameAssetManager assetManager;

    protected Factory(final GameAssetManager assetManager) {
        this.assetManager = assetManager;
    }

}
