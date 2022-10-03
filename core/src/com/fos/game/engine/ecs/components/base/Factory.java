package com.fos.game.engine.ecs.components.base;

import com.fos.game.engine.files.assets.GameAssetManager;
import com.fos.game.engine.files.serialization.JsonConverter;

public abstract class Factory {

    protected final GameAssetManager assetManager;
    protected final JsonConverter jsonConverter;

    protected Factory(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        this.assetManager = assetManager;
        this.jsonConverter = jsonConverter;
    }

}
