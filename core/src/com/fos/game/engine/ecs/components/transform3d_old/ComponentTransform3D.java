package com.fos.game.engine.ecs.components.transform3d_old;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

public class ComponentTransform3D implements Component {

    public Matrix4 matrix4;

    protected ComponentTransform3D(Matrix4 matrix4) {
        this.matrix4 = matrix4;
    }

    @Override
    public final ComponentType getComponentType() {
        return ComponentType.TRANSFORM_3D;
    }

    // TODO: test
    public Vector3 getPosition(Vector3 output) {
        return matrix4.getTranslation(output);
    }

    // TODO: test
    public Vector3 getDirection(Vector3 output) {
        output.x = matrix4.val[Matrix4.M02];
        output.y = matrix4.val[Matrix4.M12];
        output.z = matrix4.val[Matrix4.M22];
        return output;
    }

    // TODO: test
    public Vector3 getUp(Vector3 output) {
        output.x = matrix4.val[Matrix4.M01];
        output.y = matrix4.val[Matrix4.M11];
        output.z = matrix4.val[Matrix4.M21];
        return output;
    }

}
