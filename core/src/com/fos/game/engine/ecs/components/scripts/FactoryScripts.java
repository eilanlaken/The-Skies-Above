package com.fos.game.engine.ecs.components.scripts;

import com.fos.game.engine.ecs.components.base.Factory;
import com.fos.game.engine.files.assets.GameAssetManager;
import com.fos.game.engine.files.serialization.JsonConverter;

public class FactoryScripts extends Factory {

    public FactoryScripts(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    public ComponentScripts create(final Script ...scripts) {
        return new ComponentScripts(scripts);
    }

}
