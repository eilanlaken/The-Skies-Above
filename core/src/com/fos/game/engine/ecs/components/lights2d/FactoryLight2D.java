package com.fos.game.engine.ecs.components.lights2d;

import com.badlogic.gdx.graphics.Color;
import com.fos.game.engine.ecs.components.base.Factory;
import com.fos.game.engine.core.files.assets.GameAssetManager;
import com.fos.game.engine.core.files.serialization.JsonConverter;

public class FactoryLight2D extends Factory {

    public FactoryLight2D(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    public ComponentLight2D create(final Light2DData data) {
        return null;
    }

    public ComponentLight2D createPointLight() {
        return null;
    }

    public ComponentLight2D createConeLight() {
        return null;
    }

    public ComponentLight2D createDirectionalLight() {
        return null;
    }

    public ComponentLight2D createAmbientLight() {
        return null;
    }

    @Deprecated // for minor test only
    public ComponentLight2D create() {
        Light2DData data = new Light2DData(LightType.POINT, Color.CYAN, 10, 5);
        return new ComponentLight2D(data);
    }

}
