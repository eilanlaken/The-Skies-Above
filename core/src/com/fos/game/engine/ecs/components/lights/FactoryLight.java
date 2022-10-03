package com.fos.game.engine.ecs.components.lights;

import com.fos.game.engine.ecs.components.base.Factory;
import com.fos.game.engine.files.assets.GameAssetManager;
import com.fos.game.engine.files.serialization.JsonConverter;

public class FactoryLight extends Factory {

    public FactoryLight(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    @Deprecated
    public static ComponentPointLight createPointLight() {
        return new ComponentPointLight();
    }

    @Deprecated
    public static ComponentDirectionalLight createDirectionalLight() {
        return new ComponentDirectionalLight();
    }

}
