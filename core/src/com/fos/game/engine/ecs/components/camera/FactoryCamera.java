package com.fos.game.engine.ecs.components.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.fos.game.engine.core.files.assets.GameAssetManager;
import com.fos.game.engine.core.files.serialization.JsonConverter;
import com.fos.game.engine.ecs.components.base.Factory;

public class FactoryCamera extends Factory {

    public FactoryCamera(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    public ComponentCamera createCamera2D(final float viewportWidth, final float viewportHeight, final Enum ...layers) {
        OrthographicCamera lens = new OrthographicCamera();
        lens.setToOrtho(false, viewportWidth, viewportHeight);
        lens.position.set(0,0,0);
        lens.update();
        return new ComponentCamera(lens, layers, 0, null, null);
    }

}
