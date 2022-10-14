package com.fos.game.engine.ecs.components.shapes;

import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

public abstract class ComponentShape2D implements Component {

    final ShapeData shapeData;

    protected ComponentShape2D(ShapeData shapeData) {
        this.shapeData = shapeData;
        // TODO: implement.
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.SHAPE_2D;
    }

}
