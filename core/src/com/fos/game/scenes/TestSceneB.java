package com.fos.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.fos.game.engine.core.context.ApplicationContext;
import com.fos.game.engine.core.context.Scene;
import com.fos.game.engine.core.graphics.g2d.GraphicsUtils;
import com.fos.game.engine.core.graphics.g2d.SpriteSheet;
import com.fos.game.engine.ecs.components.animations2d.ComponentAnimations2D;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.camera.ComponentCamera;
import com.fos.game.engine.ecs.components.transform.ComponentTransform2D;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.systems.base.EntityContainer;

import java.util.HashMap;
import java.util.Map;

public class TestSceneB extends Scene {

    enum Categories {
        GAME_OBJECT_1,
        GAME_OBJECT_2,
        UI,
        CAMERA,
    }
    EntityContainer container;

    Entity eCamera1, eCamera2;
    Entity e1, e2, e3;
    Entity eLight1, eLight2;

    // test:
    float angle = 0;
    float tintAlpha = 0;
    Vector3 camPosition = new Vector3();

    public final float VIRTUAL_HEIGHT = 20;
    private int pixelsPerUnit = 53*2;

    public TestSceneB(final ApplicationContext context) {
        super(context);
    }

    @Override
    public void show() {
        container = new EntityContainer();

        eCamera1 = new Entity(Categories.CAMERA);
        eCamera1.attachComponents(
                context.factoryTransform.create2d(0, 0, 0, 1, 1, 0),
                context.factoryCamera.createCamera2D(VIRTUAL_HEIGHT * GraphicsUtils.getAspectRatio(), VIRTUAL_HEIGHT, Categories.GAME_OBJECT_1)
        );
        eCamera2 = new Entity(Categories.CAMERA);
        eCamera2.attachComponents(
                context.factoryTransform.create2d(0, 0, 0, 1, 1, 0),
                context.factoryCamera.createCamera2D(VIRTUAL_HEIGHT * GraphicsUtils.getAspectRatio() / 2, VIRTUAL_HEIGHT / 2, Categories.GAME_OBJECT_2)
        );

        e1 = new Entity(Categories.GAME_OBJECT_1);
        e1.attachComponents(
                context.factoryTransform.create2d(0, 0, 0, 1, 1, 0),
                context.factoryFrameAnimations2D.create("atlases/test/testSpriteSheet3.atlas", "a", 1,1f, pixelsPerUnit)
        );

        e2 = new Entity(Categories.GAME_OBJECT_2);
        e2.attachComponents(
                context.factoryTransform.create2d(-5, 0, 0, 1, 1, 0),
                context.factoryFrameAnimations2D.create("atlases/test/testSpriteSheet3.atlas", "a", 1,1f, pixelsPerUnit)
        );

        container.addEntity(e1);
        container.addEntity(e2);
        container.addEntity(eCamera1);
        container.addEntity(eCamera2);
    }

    @Override
    public void update(float delta) {
        container.update();

        ComponentTransform2D transformCamera = (ComponentTransform2D) eCamera1.components[ComponentType.TRANSFORM.ordinal()];
        ComponentCamera camera = (ComponentCamera) eCamera1.components[ComponentType.GRAPHICS.ordinal()];
        OrthographicCamera lens = (OrthographicCamera) camera.lens;

        ComponentTransform2D transformEntity1 = (ComponentTransform2D) e1.components[ComponentType.TRANSFORM.ordinal()];

        tintAlpha += delta;

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            lens.zoom += 0.1f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            lens.zoom -= 0.1f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            transformEntity1.angle += 2 * MathUtils.degreesToRadians;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.T)) {
            ComponentAnimations2D animation = (ComponentAnimations2D) e1.components[ComponentType.GRAPHICS.ordinal()];
            animation.tint.set(1,0,0, MathUtils.sin(tintAlpha));
        }
    }

    @Override
    public void resize(int width, int height) {
        ComponentCamera camera1 = (ComponentCamera) eCamera1.components[ComponentType.GRAPHICS.ordinal()];
        camera1.buildFrameBuffer();
        camera1.lens.viewportWidth = camera1.viewWorldWidth;
        camera1.lens.viewportHeight = camera1.viewWorldWidth * (float) height / width;
        camera1.lens.update();

        ComponentCamera camera2 = (ComponentCamera) eCamera1.components[ComponentType.GRAPHICS.ordinal()];
        camera2.buildFrameBuffer();
        camera2.lens.viewportWidth = camera2.viewWorldWidth;
        camera2.lens.viewportHeight = camera2.viewWorldWidth * (float) height / width;
        camera2.lens.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    // TODO: add spine
    public static Map<String, Class> getRequiredAssetsNameTypeMap() {
        HashMap<String, Class> assetNameClassMap = new HashMap<>();
        assetNameClassMap.put("atlases/test/testSpriteSheet3.atlas", SpriteSheet.class);
        assetNameClassMap.put("atlases/test/testSpriteSheet4.atlas", SpriteSheet.class);
        return assetNameClassMap;
    }

}
