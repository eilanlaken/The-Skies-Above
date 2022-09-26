package com.fos.game.engine.components.transform;

import com.badlogic.gdx.math.Matrix4;
import com.fos.game.engine.components.base.Component;
import com.fos.game.engine.components.base.ComponentType;

public class ComponentTransform3D extends Matrix4 implements Component {

    protected ComponentTransform3D() {
        super();
    }

    protected ComponentTransform3D(Matrix4 matrix4) {
        super(matrix4);
    }

    @Override
    public final ComponentType getComponentType() {
        return ComponentType.TRANSFORM_3D;
    }



}
