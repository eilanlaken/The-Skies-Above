package com.fos.game.engine.components.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.fos.game.engine.components.base.Factory;
import com.fos.game.engine.files.assets.GameAssetManager;
import com.fos.game.engine.files.serialization.JsonConverter;
import com.fos.game.engine.systems.renderer.shaders.postprocessing.PostProcessingEffect;
import com.fos.game.engine.systems.renderer.base.RenderTarget;

public class FactoryCamera extends Factory {

    public static final int RENDER_ALL_LAYERS_MASK = 0b11111111111111111111111111111111;
    public static final float DEFAULT_FOV = 75;
    public static final float DEFAULT_NEAR = 0.3f;
    public static final float DEFAULT_FAR = 30000;

    public FactoryCamera(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    @Deprecated
    public static ComponentCamera create2DCamera(final Enum ...categories) {
        OrthographicCamera lens = new OrthographicCamera();
        lens.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        lens.position.set(0,0,0);
        lens.update();
        return new ComponentCamera(lens, UtilsCameras.computeRenderedLayersBitMask(categories), null);
    }

    @Deprecated
    public static ComponentCamera create2DCamera(float viewportWidth, float viewportHeight, final Enum ...categories) {
        OrthographicCamera lens = new OrthographicCamera();
        lens.setToOrtho(false, viewportWidth, viewportHeight);
        lens.position.set(0,0,0);
        lens.update();
        return new ComponentCamera(lens, UtilsCameras.computeRenderedLayersBitMask(categories), null);
    }

    @Deprecated
    public static ComponentCamera create3DCamera(final Enum ...categories) {
        PerspectiveCamera camera = new PerspectiveCamera(DEFAULT_FOV, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.near = DEFAULT_NEAR;
        camera.far = DEFAULT_FAR;
        camera.update();
        return new ComponentCamera(camera, UtilsCameras.computeRenderedLayersBitMask(categories), null);
    }

    @Deprecated
    public static ComponentCamera create3DCamera(final float fov, final float near, final float far, final Enum ...categories) {
        PerspectiveCamera camera = new PerspectiveCamera(fov, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.near = near;
        camera.far = far;
        camera.update();
        return new ComponentCamera(camera, UtilsCameras.computeRenderedLayersBitMask(categories), null);
    }

    @Deprecated
    public static ComponentCamera create3DCamera(final float fov, final float near, final float far, final int renderLayersBitMask, final RenderTarget.RenderTargetParams renderTargetParams, PostProcessingEffect... postProcessingEffects) {
        PerspectiveCamera lens = new PerspectiveCamera(fov, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        lens.near = near;
        lens.far = far;
        lens.update();
        return new ComponentCamera(lens, renderLayersBitMask, renderTargetParams, postProcessingEffects);
    }
}
