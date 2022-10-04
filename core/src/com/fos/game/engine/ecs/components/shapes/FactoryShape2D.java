package com.fos.game.engine.ecs.components.shapes;

import com.badlogic.gdx.graphics.Color;
import com.fos.game.engine.ecs.components.base.Factory;
import com.fos.game.engine.files.assets.GameAssetManager;
import com.fos.game.engine.files.serialization.JsonConverter;

public class FactoryShape2D extends Factory {

    public FactoryShape2D(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    @Deprecated
    public static CircleShape create(float radius) {
        return new CircleShape(Color.WHITE, radius);
    }

    @Deprecated
    public static CircleShape create(Color color, float radius) {
        return new CircleShape(color, radius);
    }

    @Deprecated
    public static RectangleShape create(float width, float height) {
        return new RectangleShape(Color.WHITE, width, height);
    }

    @Deprecated
    public static RectangleShape create(Color color, float width, float height) {
        return new RectangleShape(color, width, height);
    }

}