package com.fos.game.engine.components.shapes;

import com.badlogic.gdx.graphics.Color;

public class FactoryShape2D {

    public static CircleShape create(float radius) {
        return new CircleShape(Color.WHITE, radius);
    }

    public static CircleShape create(Color color, float radius) {
        return new CircleShape(color, radius);
    }

    public static RectangleShape create(float width, float height) {
        return new RectangleShape(Color.WHITE, width, height);
    }

    public static RectangleShape create(Color color, float width, float height) {
        return new RectangleShape(color, width, height);
    }

}
