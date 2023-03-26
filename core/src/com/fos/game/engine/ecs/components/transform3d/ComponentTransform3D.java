package com.fos.game.engine.ecs.components.transform3d;

import com.badlogic.gdx.math.Matrix4;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

public class ComponentTransform3D implements Component {

    public Matrix4 matrix4;

    protected ComponentTransform3D(Matrix4 matrix4) {
        this.matrix4 = matrix4;
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.TRANSFORM_3D;
    }

}
