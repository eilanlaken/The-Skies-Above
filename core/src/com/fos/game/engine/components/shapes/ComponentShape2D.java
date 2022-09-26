package com.fos.game.engine.components.shapes;

import com.badlogic.gdx.graphics.Color;
import com.fos.game.engine.components.base.Component;
import com.fos.game.engine.components.base.ComponentType;

public abstract class ComponentShape2D implements Component {

    public ShapeType shapeType;
    public Color color;

    protected ComponentShape2D(ShapeType shapeType, Color color) {
        this.shapeType = shapeType;
        this.color = color;
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.SHAPE_2D;
    }

    public enum ShapeType {
        RECT, CIRCLE
    }

}
