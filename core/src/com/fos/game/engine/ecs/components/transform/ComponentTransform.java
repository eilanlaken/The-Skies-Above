package com.fos.game.engine.ecs.components.transform;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

@Deprecated
public class ComponentTransform implements Component {

    public Vector3 position;
    public Vector3 scale;
    public Quaternion rotation;

    protected ComponentTransform(Vector3 position, Quaternion rotation, Vector3 scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.TRANSFORM;
    }
}
