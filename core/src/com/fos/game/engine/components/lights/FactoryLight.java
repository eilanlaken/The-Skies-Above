package com.fos.game.engine.components.lights;

import com.fos.game.engine.components.base.Factory;
import com.fos.game.engine.files.assets.GameAssetManager;

public class FactoryLight extends Factory {

    public FactoryLight(final GameAssetManager assetManager) {
        super(assetManager);
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
