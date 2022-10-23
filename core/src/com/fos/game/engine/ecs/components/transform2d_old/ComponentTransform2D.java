package com.fos.game.engine.ecs.components.transform2d_old;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Transform;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

public class ComponentTransform2D implements Component {

    public Transform transform;
    public float z;
    public float scaleX;
    public float scaleY;

    protected ComponentTransform2D(float x, float y, float z,float angle, float scaleX, float scaleY) {
        this.transform = new Transform(new Vector2(x,y), angle);
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.z = z;
    }

    public Vector2 getPosition() {
        return transform.getPosition();
    }
    public float getRotation() { return transform.getRotation(); }
    public void translate(float dx, float dy) {
        transform.vals[Transform.POS_X] += dx;
        transform.vals[Transform.POS_Y] += dy;
    }

    @Override
    public final ComponentType getComponentType() {
        return ComponentType.TRANSFORM_2D;
    }

}
