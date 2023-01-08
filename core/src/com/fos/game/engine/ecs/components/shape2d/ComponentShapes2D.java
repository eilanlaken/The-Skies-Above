package com.fos.game.engine.ecs.components.shape2d;

import com.fos.game.engine.core.graphics.g2d.ShapeBatch;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

public abstract class ComponentShapes2D implements Component {

    public abstract void draw(ShapeBatch batch);

    @Override
    public ComponentType getComponentType() {
        return ComponentType.GRAPHICS;
    }
}
