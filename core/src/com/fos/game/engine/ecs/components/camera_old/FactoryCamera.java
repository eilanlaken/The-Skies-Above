package com.fos.game.engine.ecs.components.camera_old;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.fos.game.engine.ecs.components.base.Factory;
import com.fos.game.engine.core.files.assets.GameAssetManager;
import com.fos.game.engine.core.files.serialization.JsonConverter;
import com.fos.game.engine.ecs.systems.renderer_old.shaders.postprocessing.PostProcessingEffect;
import com.fos.game.engine.ecs.systems.renderer_old.base.RenderTarget;

@Deprecated
public class FactoryCamera extends Factory {

    public static final int RENDER_ALL_LAYERS_MASK = 0b11111111111111111111111111111111;
    public static final float DEFAULT_FOV = 75;
    public static final float DEFAULT_NEAR = 0.3f;
    public static final float DEFAULT_FAR = 30000;

    public FactoryCamera(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    public ComponentCamera createCamera2D(final Camera2DData data) {
        OrthographicCamera lens = new OrthographicCamera();
        lens.setToOrtho(false, data.viewportWidth, data.viewportHeight);
        lens.position.set(data.positionX, data.positionY, 0);
        lens.zoom = data.zoom;
        lens.update();
        return new ComponentCamera(lens, UtilsCameras.computeRenderedLayersBitMask(data.layers), null);
    }

    public ComponentCamera createCamera2D(final float viewportWidth, final float viewportHeight, final Enum ...layers) {
        OrthographicCamera lens = new OrthographicCamera();
        lens.setToOrtho(false, viewportWidth, viewportHeight);
        lens.position.set(0,0,0);
        lens.update();
        return new ComponentCamera(lens, UtilsCameras.computeRenderedLayersBitMask(layers), null);
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
