package com.fos.game.scenes.tests.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.fos.game.engine.core.context.ApplicationContext;
import com.fos.game.engine.core.context.Scene;
import com.fos.game.engine.core.graphics.g2d.GraphicsUtils;
import com.fos.game.engine.core.graphics.g2d.SpriteSheet;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.camera.ComponentCamera;
import com.fos.game.engine.ecs.components.physics2d.Body2DData;
import com.fos.game.engine.ecs.components.transform.ComponentTransform2D;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.systems.base.EntityContainer;

import java.util.HashMap;
import java.util.Map;

public class TestScenePhysicsJoints extends Scene {

    enum Categories {
        GAME_OBJECTS,
        CAMERA,
    }
    EntityContainer container;

    Entity eCamera1;
    Entity e1, e2, e3, e4, e5;

    public final float VIRTUAL_HEIGHT = 20;
    private int pixelsPerUnit = 53*2;

    public TestScenePhysicsJoints(final ApplicationContext context) {
        super(context);
    }

    @Override
    public void show() {
        container = new EntityContainer();

        eCamera1 = new Entity(Categories.CAMERA);
        eCamera1.attachComponents(
                context.factoryTransform2D.create2d(0, 0, 0, 1, 1, 0, false, eCamera1),
                context.factoryCamera.createCamera2D(VIRTUAL_HEIGHT * GraphicsUtils.getAspectRatio(), VIRTUAL_HEIGHT, Categories.GAME_OBJECTS)
        );

        e1 = new Entity(Categories.GAME_OBJECTS);
        e1.attachComponents(
                context.factoryTransform2D.create2d(0, 0, 0, 1, 1, 0, false),
                context.factoryFrameAnimations2D.create("atlases/test/testSpriteSheet3.atlas", "a", 1,1f, pixelsPerUnit),
                context.factoryBody2D.create(new Body2DData(BodyDef.BodyType.DynamicBody, Body2DData.Shape.RECTANGLE,
                        1, 1, new Filter(), 1,1,1, false))
        );

        e2 = new Entity(Categories.GAME_OBJECTS);
        e2.attachComponents(
                context.factoryTransform2D.create2d(-3, 0, 0, 1, 1, 0, false),
                context.factoryFrameAnimations2D.create("atlases/test/testSpriteSheet3.atlas", "b", 1,1f, pixelsPerUnit),
                context.factoryBody2D.create(new Body2DData(BodyDef.BodyType.DynamicBody, Body2DData.Shape.RECTANGLE,
                        1, 1, new Filter(), 1,1,1, false))
        );

        e3 = new Entity(Categories.GAME_OBJECTS);
        e3.attachComponents(
                context.factoryTransform2D.create2d(-6, 1, 0, 1, 1, 0, false),
                context.factoryFrameAnimations2D.create("atlases/test/testSpriteSheet3.atlas", "c", 1,1f, pixelsPerUnit),
                context.factoryBody2D.create(new Body2DData(BodyDef.BodyType.DynamicBody, Body2DData.Shape.RECTANGLE,
                        1, 1, new Filter(), 1,1,1, false))
        );

        e4 = new Entity(Categories.GAME_OBJECTS);
        e4.attachComponents(
                context.factoryTransform2D.create2d(0, -6, 0, 1, 1, 0, false),
                context.factoryFrameAnimations2D.create("atlases/test/testSpriteSheet3.atlas", "c", 1,1f, pixelsPerUnit),
                context.factoryBody2D.create(new Body2DData(BodyDef.BodyType.StaticBody, Body2DData.Shape.RECTANGLE,
                        20, 1, new Filter(), 1,1,1, false))
        );

        e5 = new Entity(Categories.GAME_OBJECTS);
        e5.attachComponents(
                context.factoryFrameAnimations2D.create("atlases/test/testSpriteSheet3.atlas", "c", 1,1f, pixelsPerUnit),
                context.factoryBody2D.create(new Body2DData(BodyDef.BodyType.StaticBody, Body2DData.Shape.RECTANGLE,
                        20, 1, new Filter(), 1,1,1, false))
        );

        //context.factoryRigidBody2D.create();


        container.addEntity(e1);
        container.addEntity(e2);
        container.addEntity(e3);
        container.addEntity(e4);


        container.addEntity(eCamera1);
    }

    @Override
    public void update(float delta) {
        container.update();

        Entity toMove = e1;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            ComponentTransform2D transform2D = (ComponentTransform2D) toMove.getComponent(ComponentType.TRANSFORM_2D);
            transform2D.x -= 0.05f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            ComponentTransform2D transform2D = (ComponentTransform2D) toMove.getComponent(ComponentType.TRANSFORM_2D);
            transform2D.x += 0.05f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            ComponentTransform2D transform2D = (ComponentTransform2D) toMove.getComponent(ComponentType.TRANSFORM_2D);
            transform2D.y += 0.05f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            ComponentTransform2D transform2D = (ComponentTransform2D) toMove.getComponent(ComponentType.TRANSFORM_2D);
            transform2D.y -= 0.05f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            ComponentTransform2D transform2D = (ComponentTransform2D) toMove.getComponent(ComponentType.TRANSFORM_2D);
            transform2D.angle += 1 * MathUtils.degreesToRadians;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            ComponentTransform2D transform2D = (ComponentTransform2D) toMove.getComponent(ComponentType.TRANSFORM_2D);
            transform2D.angle -= 1 * MathUtils.degreesToRadians;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            ComponentTransform2D transform2D = (ComponentTransform2D) toMove.getComponent(ComponentType.TRANSFORM_2D);
            transform2D.scaleX += 0.017f;
            transform2D.scaleY += 0.017f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            ComponentTransform2D transform2D = (ComponentTransform2D) toMove.getComponent(ComponentType.TRANSFORM_2D);
            transform2D.scaleX -= 0.017f;
            transform2D.scaleY -= 0.017f;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            e1.detachChild(e2);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            e1.attachChild(e2);
        }
    }

    @Override
    public void resize(int width, int height) {
        ComponentCamera camera1 = (ComponentCamera) eCamera1.components[ComponentType.GRAPHICS.ordinal()];
        camera1.lens.viewportHeight = VIRTUAL_HEIGHT;
        camera1.lens.viewportWidth = VIRTUAL_HEIGHT * width / (float) height;
        camera1.lens.update();
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
