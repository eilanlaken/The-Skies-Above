package com.fos.game.engine.components.transform;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Transform;
import com.fos.game.engine.components.base.Component;
import com.fos.game.engine.components.base.ComponentType;

public class ComponentTransform2D implements Component {

    public Transform transform;
    public float z;
    public float scaleX;
    public float scaleY;

    protected ComponentTransform2D(float x, float y, float z, float angle, float scaleX, float scaleY) {
        this.transform = new Transform(new Vector2(x,y), angle);
        this.z = z;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override
    public final ComponentType getComponentType() {
        return ComponentType.TRANSFORM_2D;
    }

    public Vector2 getPosition() {
        return transform.getPosition();
    }

    public float getRotation() {
        return transform.getRotation();
    }

}
