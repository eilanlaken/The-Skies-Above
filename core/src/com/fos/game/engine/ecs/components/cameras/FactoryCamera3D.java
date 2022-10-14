package com.fos.game.engine.ecs.components.cameras;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.fos.game.engine.ecs.components.base.Factory;
import com.fos.game.engine.core.files.assets.GameAssetManager;
import com.fos.game.engine.core.files.serialization.JsonConverter;

public class FactoryCamera3D extends Factory {

    public static final int RENDER_ALL_LAYERS_MASK = 0b11111111111111111111111111111111;
    public static final float DEFAULT_FOV = 75;
    public static final float DEFAULT_NEAR = 0.3f;
    public static final float DEFAULT_FAR = 30000;

    public FactoryCamera3D(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    // TODO: implement.
    public ComponentCamera3D createCamera3D(final Camera3DData data) {
        return null;
    }

    @Deprecated
    public ComponentCamera3D create3DCamera(final Enum ...categories) {
        PerspectiveCamera camera = new PerspectiveCamera(DEFAULT_FOV, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.near = DEFAULT_NEAR;
        camera.far = DEFAULT_FAR;
        camera.update();
        return new ComponentCamera3D(camera, UtilsCameras.computeRenderedLayersBitMask(categories), null);
    }

    @Deprecated
    public ComponentCamera3D create3DCamera(final float fov, final float near, final float far, final Enum ...categories) {
        PerspectiveCamera camera = new PerspectiveCamera(fov, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.near = near;
        camera.far = far;
        camera.update();
        return new ComponentCamera3D(camera, UtilsCameras.computeRenderedLayersBitMask(categories), null);
    }

}
