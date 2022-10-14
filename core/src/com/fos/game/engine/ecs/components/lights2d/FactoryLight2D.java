package com.fos.game.engine.ecs.components.lights2d;

import com.badlogic.gdx.graphics.Color;
import com.fos.game.engine.ecs.components.base.Factory;
import com.fos.game.engine.core.files.assets.GameAssetManager;
import com.fos.game.engine.core.files.serialization.JsonConverter;

public class FactoryLight2D extends Factory {

    public FactoryLight2D(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    @Deprecated // for minor test only
    public ComponentLight2D create() {
        Light2DData data = new Light2DData(Color.CYAN, 10, 5);
        return new ComponentLight2D(data);
    }

}
