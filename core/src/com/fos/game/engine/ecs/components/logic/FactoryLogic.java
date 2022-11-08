package com.fos.game.engine.ecs.components.logic;

import com.fos.game.engine.ecs.components.base.Factory;
import com.fos.game.engine.core.files.assets.GameAssetManager;
import com.fos.game.engine.core.files.serialization.JsonConverter;

public class FactoryLogic extends Factory {

    public FactoryLogic(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    public ComponentLogic create(final Logic... logic) {
        return new ComponentLogic(logic);
    }

}
