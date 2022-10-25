package com.fos.game.engine.ecs.components.transform;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.fos.game.engine.core.files.assets.GameAssetManager;
import com.fos.game.engine.core.files.serialization.JsonConverter;
import com.fos.game.engine.ecs.components.base.Factory;

public class FactoryTransform extends Factory {

    public FactoryTransform(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    public ComponentTransform create2d(float x, float y, float z, float angle, float scaleX, float scaleY) {
        Vector3 position = new Vector3(x,y,z);
        Quaternion rotation = new Quaternion(new Vector3(0,0,1), angle);
        Vector3 scale = new Vector3(scaleX, scaleY, 1);
        return new ComponentTransform(position, rotation, scale);
    }

}
