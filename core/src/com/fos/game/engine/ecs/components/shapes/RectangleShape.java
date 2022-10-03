package com.fos.game.engine.ecs.components.shapes;

import com.badlogic.gdx.graphics.Color;

public class RectangleShape extends ComponentShape2D {

    public float width;
    public float height;

    protected RectangleShape(Color color, float width, float height) {
        super(ShapeType.RECT, color);
        this.width = width;
        this.height = height;
    }

}
