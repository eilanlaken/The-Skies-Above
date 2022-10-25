package com.fos.game.engine.ecs.components.transform;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

public class ComponentTransform implements Component {

    public Vector3 position;
    public Vector3 scale;
    public Quaternion rotation;

    @Override
    public ComponentType getComponentType() {
        return ComponentType.TRANSFORM;
    }
}
