package com.fos.game.scenes.tests.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.fos.game.engine.core.context.ApplicationContext;
import com.fos.game.engine.core.context.Scene;
import com.fos.game.engine.core.graphics.g2d.GraphicsUtils;
import com.fos.game.engine.core.graphics.g2d.SpriteSheet;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.camera.ComponentCamera;
import com.fos.game.engine.ecs.components.physics2d.Body2DData;
import com.fos.game.engine.ecs.components.physics2d.ComponentBody2D;
import com.fos.game.engine.ecs.components.transform2d.ComponentTransform2D;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.systems.base.EntityContainer;

import java.util.HashMap;
import java.util.Map;

public class TestScenePhysicsJoints2 extends Scene {

    enum Categories {
        GAME_OBJECTS,
        CAMERA,
    }
    EntityContainer container;

    Entity eCamera1;
    Entity e1, e2, e3, e4, e5;

    public final float VIRTUAL_HEIGHT = 20;
    private int pixelsPerUnit = 53*2;

    Joint joint;

    public TestScenePhysicsJoints2(final ApplicationContext context) {
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
        ComponentBody2D body2D = toMove.getBody2D();
        Body body = body2D.body;

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
            //ComponentTransform2D transform2D = (ComponentTransform2D) toMove.getComponent(ComponentType.TRANSFORM_2D);
            //transform2D.angle += 1 * MathUtils.degreesToRadians;
            body.applyTorque(0.1f, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            //ComponentTransform2D transform2D = (ComponentTransform2D) toMove.getComponent(ComponentType.TRANSFORM_2D);
            //transform2D.angle -= 1 * MathUtils.degreesToRadians;
            body.applyTorque(-0.1f, true);
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
            if (e1.active && e2.active) {
                WeldJointDef weldJointDef = new WeldJointDef();
                weldJointDef.initialize(e1.getBody2D().body, e2.getBody2D().body, new Vector2(3, 3));
                joint = container.dynamics2D.createJoint(weldJointDef);
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            container.dynamics2D.destroyJoint(joint);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            container.removeEntity(e2);
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
