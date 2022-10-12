package com.fos.game.engine.ecs.components.camera2d;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.fos.game.engine.ecs.components.base.Factory;
import com.fos.game.engine.ecs.components.camera.Camera2DData;
import com.fos.game.engine.files.assets.GameAssetManager;
import com.fos.game.engine.files.serialization.JsonConverter;

public class FactoryCamera2D extends Factory {

    public FactoryCamera2D(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    public ComponentCamera2D createCamera2D(final Camera2DData data) {
        OrthographicCamera lens = new OrthographicCamera();
        lens.setToOrtho(false, data.viewportWidth, data.viewportHeight);
        lens.position.set(data.positionX, data.positionY, 0);
        lens.zoom = data.zoom;
        lens.update();
        return new ComponentCamera2D(lens, data.layers, null);
    }

    public ComponentCamera2D createCamera2D(final float viewportWidth, final float viewportHeight, final Enum ...layers) {
        OrthographicCamera lens = new OrthographicCamera();
        lens.setToOrtho(false, viewportWidth, viewportHeight);
        lens.position.set(0,0,0);
        lens.update();
        return new ComponentCamera2D(lens, layers, null);
    }

}
