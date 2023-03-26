package com.fos.game.scenes.tests.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.fos.game.engine.core.context.ApplicationContext;
import com.fos.game.engine.core.context.Scene;
import com.fos.game.engine.core.graphics.g2d.GraphicsUtils;
import com.fos.game.engine.core.graphics.g2d.SpriteSheet;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.camera.ComponentCamera;
import com.fos.game.engine.ecs.components.physics2d.ComponentBody2D;
import com.fos.game.engine.ecs.components.physics2d.RigidBody2DData;
import com.fos.game.engine.ecs.components.transform.ComponentTransform2D;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.systems.base.EntityContainer;

import java.util.HashMap;
import java.util.Map;

public class TestSceneParentTransform extends Scene {

    enum Categories {
        GAME_OBJECTS,
        CAMERA,
    }
    EntityContainer container;

    Entity eCamera1;
    Entity e1, e2, e3, e4;

    public final float VIRTUAL_HEIGHT = 20;
    private int pixelsPerUnit = 53*2;

    public TestSceneParentTransform(final ApplicationContext context) {
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
        ComponentTransform2D transform2D_1 = context.factoryTransform2D.create2d(0, 0, 0, 1, 1, 0, false, e1);
        e1.attachComponents(
                transform2D_1,
                context.factoryFrameAnimations2D.create("atlases/test/testSpriteSheet3.atlas", "a", 1,1f, pixelsPerUnit),
                context.factoryRigidBody2D.create(new RigidBody2DData(BodyDef.BodyType.DynamicBody, RigidBody2DData.Shape.RECTANGLE,
                        1, 1, new Filter(), 1,1,1, false))
        );

        e2 = new Entity(Categories.GAME_OBJECTS);
        ComponentTransform2D transform2D_2 = context.factoryTransform2D.create2d(-3, 0, 0, 1, 1, 0, true, e2);
        //transform2D_2.parent = transform2D_1;
        e2.attachComponents(
                transform2D_2,
                context.factoryFrameAnimations2D.create("atlases/test/testSpriteSheet3.atlas", "b", 1,1f, pixelsPerUnit)
        );

        e3 = new Entity(Categories.GAME_OBJECTS);
        ComponentTransform2D transform2D_3 = context.factoryTransform2D.create2d(-6, 1, 0, 1, 1, 0, false, e3);
        //transform2D_3.parent = transform2D_2;
        e3.attachComponents(
                transform2D_3,
                context.factoryFrameAnimations2D.create("atlases/test/testSpriteSheet3.atlas", "c", 1,1f, pixelsPerUnit)
        );

        e4 = new Entity(Categories.GAME_OBJECTS);
        ComponentTransform2D transform2D_4 = context.factoryTransform2D.create2d(0, -6, 0, 1, 1, 0, false, e3);
        e4.attachComponents(
                transform2D_4,
                context.factoryFrameAnimations2D.create("atlases/test/testSpriteSheet3.atlas", "c", 1,1f, pixelsPerUnit),
                context.factoryRigidBody2D.create(new RigidBody2DData(BodyDef.BodyType.StaticBody, RigidBody2DData.Shape.RECTANGLE,
                        10, 1, new Filter(), 1,1,1, false))
        );

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

        ComponentBody2D componentBody2D = e1.getBody2D();
        Body body = componentBody2D.body;

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
            //transform2D.angle -= 1 * MathUtils.degreesToRadians;
            //body.applyForceToCenter(1,0, false);
            body.applyTorque(3, false);
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
