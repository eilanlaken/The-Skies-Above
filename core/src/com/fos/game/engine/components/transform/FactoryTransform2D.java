package com.fos.game.engine.components.transform;

import com.badlogic.gdx.math.MathUtils;
import com.fos.game.engine.components.base.Factory;
import com.fos.game.engine.files.assets.GameAssetManager;

public class FactoryTransform2D extends Factory {

    public FactoryTransform2D(final GameAssetManager assetManager) {
        super(assetManager);
    }

    @Deprecated
    public static ComponentTransform2D create() {
        return new ComponentTransform2D(0,0,0,0,1, 1);
    }

    @Deprecated
    public static ComponentTransform2D create(float x, float y) {
        return new ComponentTransform2D(x, y, 0, 0, 1, 1);
    }

    @Deprecated
    public static ComponentTransform2D create(float x, float y, float angle) {
        float angleRadians = angle * MathUtils.degreesToRadians;
        return new ComponentTransform2D(x, y, angleRadians, 0, 1, 1);
    }

    @Deprecated
    public static ComponentTransform2D create(float x, float y, float z, float angle, float scaleX, float scaleY) {
        float angleRadians = angle * MathUtils.degreesToRadians;
        return new ComponentTransform2D(x, y, z, angleRadians, scaleX, scaleY);
    }

    @Deprecated
    public static ComponentTransform2D copy(final ComponentTransform2D transform2D) {
        return create(transform2D.getPosition().x, transform2D.getPosition().y, transform2D.z, transform2D.getRotation(), transform2D.scaleX, transform2D.scaleY);
    }


}
