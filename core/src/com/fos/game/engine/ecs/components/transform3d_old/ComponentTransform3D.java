package com.fos.game.engine.ecs.components.transform3d_old;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

@Deprecated
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

    // TODO: test
    public Vector3 getPosition(Vector3 output) {
        return super.getTranslation(output);
    }

    // TODO: test
    public Vector3 getDirection(Vector3 output) {
        output.x = val[M02];
        output.y = val[M12];
        output.z = val[M22];
        return output;
    }

    // TODO: test
    public Vector3 getUp(Vector3 output) {
        output.x = val[M01];
        output.y = val[M11];
        output.z = val[M21];
        return output;
    }

}
