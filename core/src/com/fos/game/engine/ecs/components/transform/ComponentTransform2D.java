package com.fos.game.engine.ecs.components.transform;

import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

public class ComponentTransform2D implements Component {

    public float x,y,z;
    public float scaleX, scaleY;
    public float angle;

    protected ComponentTransform2D(float x, float y, float z, float scaleX, float scaleY, float angle) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.angle = angle;
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.TRANSFORM;
    }
}
