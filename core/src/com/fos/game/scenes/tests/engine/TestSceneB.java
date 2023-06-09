package com.fos.game.scenes.tests.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.fos.game.engine.core.context.ApplicationContext;
import com.fos.game.engine.core.context.Scene;
import com.fos.game.engine.core.graphics.g2d.GraphicsUtils;
import com.fos.game.engine.core.graphics.g2d.SpriteSheet;
import com.fos.game.engine.core.graphics.shaders.postprocessing.Example_PostProcessingEffectSetColor;
import com.fos.game.engine.ecs.components.rendered2d.ComponentAnimations2D;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.camera.ComponentCamera;
import com.fos.game.engine.ecs.components.physics2d.Body2DData;
import com.fos.game.engine.ecs.components.transform2d.ComponentTransform2D;
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
                context.factoryTransform2D.create2d(0, 0, 0, 1, 1, 0),
                context.factoryCamera.createCamera2D(VIRTUAL_HEIGHT * GraphicsUtils.getAspectRatio(), VIRTUAL_HEIGHT, Categories.GAME_OBJECT_1)
        );
        eCamera2 = new Entity(Categories.CAMERA);
        eCamera2.attachComponents(
                context.factoryTransform2D.create2d(0, 0, 0, 1, 1, 0),
                context.factoryCamera.createCamera2D(VIRTUAL_HEIGHT * GraphicsUtils.getAspectRatio() / 2, VIRTUAL_HEIGHT / 2, Categories.GAME_OBJECT_2)
        );

        e1 = new Entity(Categories.GAME_OBJECT_1);
        e1.attachComponents(
                context.factoryTransform2D.create2d(0, 6, 0, 3, 3, 0),
                context.factoryFrameAnimations2D.create("atlases/striders/caterpillarBody.atlas", "caterpillarBody", 1,1f, pixelsPerUnit),
                context.factoryBody2D.create(new Body2DData(BodyDef.BodyType.DynamicBody, Body2DData.Shape.RECTANGLE,
                        1, 1, new Filter(), 1,1,1, false))
        );

        e2 = new Entity(Categories.GAME_OBJECT_2);
        e2.attachComponents(
                context.factoryTransform2D.create2d(-5, 0, 0, 1, 1, 0),
                context.factoryFrameAnimations2D.create("atlases/test/testSpriteSheet3.atlas", "a", 1,1f, pixelsPerUnit)
        );

        container.addEntity(e1);
        container.addEntity(e2);
        container.addEntity(eCamera1);
        container.addEntity(eCamera2);

        Example_PostProcessingEffectSetColor postProcessingEffect = new Example_PostProcessingEffectSetColor();
    }

    @Override
    public void update(float delta) {
        container.update();

        ComponentTransform2D transformCamera = (ComponentTransform2D) eCamera1.components[ComponentType.TRANSFORM_2D.ordinal()];
        ComponentCamera camera = (ComponentCamera) eCamera1.components[ComponentType.GRAPHICS.ordinal()];
        OrthographicCamera lens = (OrthographicCamera) camera.lens;

        ComponentTransform2D transformEntity1 = (ComponentTransform2D) e1.components[ComponentType.TRANSFORM_2D.ordinal()];

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
        camera1.lens.viewportHeight = VIRTUAL_HEIGHT;
        camera1.lens.viewportWidth = VIRTUAL_HEIGHT * width / (float) height;
        camera1.lens.update();

        ComponentCamera camera2 = (ComponentCamera) eCamera2.components[ComponentType.GRAPHICS.ordinal()];
        camera2.lens.viewportHeight = VIRTUAL_HEIGHT / 2f;
        camera2.lens.viewportWidth = (VIRTUAL_HEIGHT * width / 2f) / (float) height;
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
        assetNameClassMap.put("atlases/striders/caterpillarBody.atlas", SpriteSheet.class);
        assetNameClassMap.put("atlases/test/testSpriteSheet3.atlas", SpriteSheet.class);
        assetNameClassMap.put("atlases/test/testSpriteSheet4.atlas", SpriteSheet.class);
        return assetNameClassMap;
    }

}
