package com.fos.game.engine.ecs.components.shapes;

import com.badlogic.gdx.graphics.Color;

public class CircleShape extends ComponentShape2D {

    public float radius;

    public CircleShape(Color color, float radius) {
        super(ShapeType.CIRCLE, color);
        this.radius = radius;
    }

}
