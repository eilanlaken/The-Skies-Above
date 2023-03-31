package com.fos.game.engine.ecs.components.transform;

import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

public class ComponentTransform2D implements Component {

    // public final Entity entity;
    // public ComponentTransform2D parent;
    public final boolean isStatic;

    public float x, y, z;
    public float scaleX, scaleY;
    public float angle;

    public float worldX, worldY, worldZ;
    public float worldScaleX, worldScaleY;
    public float worldAngle;
    public boolean updated;

    protected ComponentTransform2D(float x, float y, float z, float scaleX, float scaleY, float angle, boolean isStatic) {
        //this.entity = entity;
        //this.parent = null;
        this.isStatic = isStatic;

        this.x = x;
        this.y = y;
        this.z = z;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.angle = angle;

        this.worldX = x;
        this.worldY = y;
        this.worldZ = z;
        this.worldScaleX = scaleX;
        this.worldScaleY = scaleY;
        this.worldAngle = angle;
        this.updated = false;
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.TRANSFORM_2D;
    }

}
