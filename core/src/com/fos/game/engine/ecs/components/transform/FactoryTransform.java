package com.fos.game.engine.ecs.components.transform;

import com.badlogic.gdx.math.Matrix4;
import com.fos.game.engine.core.files.assets.GameAssetManager;
import com.fos.game.engine.core.files.serialization.JsonConverter;
import com.fos.game.engine.ecs.components.base.Factory;

public class FactoryTransform extends Factory {

    public FactoryTransform(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    public ComponentTransform create2d(float x, float y, float z, float angleRad) {
        Matrix4 matrix4 = new Matrix4();
        matrix4.translate(x,y,z);
        matrix4.rotateRad(0,0,1,angleRad);
        return new ComponentTransform(matrix4);
    }

    public ComponentTransform create2d(float x, float y, float z, float angleRad, float scaleX, float scaleY) {
        Matrix4 matrix4 = new Matrix4();
        matrix4.translate(x,y,z);
        matrix4.rotateRad(0,0,1,angleRad);
        matrix4.scale(scaleX, scaleY, 1);
        return new ComponentTransform(matrix4);
    }

}
