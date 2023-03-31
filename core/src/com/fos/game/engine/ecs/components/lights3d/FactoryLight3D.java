package com.fos.game.engine.ecs.components.lights3d;

import com.fos.game.engine.core.files.assets.GameAssetManager;
import com.fos.game.engine.core.files.serialization.JsonConverter;
import com.fos.game.engine.ecs.components.base.Factory;

public class FactoryLight3D extends Factory {

    public FactoryLight3D(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
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
