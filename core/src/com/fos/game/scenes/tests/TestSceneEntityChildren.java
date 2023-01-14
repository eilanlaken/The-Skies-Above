package com.fos.game.scenes.tests;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.fos.game.engine.core.context.ApplicationContext;
import com.fos.game.engine.core.context.Scene;
import com.fos.game.engine.core.graphics.g2d.GraphicsUtils;
import com.fos.game.engine.core.graphics.g2d.ShapeBatch;
import com.fos.game.engine.core.graphics.g2d.SpriteSheet;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.camera.ComponentCamera;
import com.fos.game.engine.ecs.components.physics2d.RigidBody2DData;
import com.fos.game.engine.ecs.components.shape2d.ComponentShapes2D;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.systems.base.EntityContainer;

import java.util.HashMap;
import java.util.Map;

public class TestSceneEntityChildren extends Scene {

    enum Categories {
        GAME_OBJECT_1,
        CAMERA,
    }
    EntityContainer container;

    Entity eCamera1;
    Entity e1, e2, e3, e4, e5;

    public final float VIRTUAL_HEIGHT = 20;
    private int pixelsPerUnit = 53*2;

    public TestSceneEntityChildren(final ApplicationContext context) {
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

        e1 = new Entity(Categories.GAME_OBJECT_1);
        e1.attachComponents(
                context.factoryTransform.create2d(0, 0, 0, 1, 1, 0),
                context.factoryFrameAnimations2D.create("atlases/test/testSpriteSheet3.atlas", "a", 1,1f, pixelsPerUnit),
                context.factoryRigidBody2D.create(new RigidBody2DData(BodyDef.BodyType.DynamicBody, RigidBody2DData.Shape.RECTANGLE,
                        1, 1, new Filter(), 1,1,1, false))
        );

        e2 = new Entity(Categories.GAME_OBJECT_1);
        e2.attachComponents(
                context.factoryTransform.create2d(-5, 0, 0, 1, 1, 0),
                context.factoryFrameAnimations2D.create("atlases/test/testSpriteSheet3.atlas", "b", 1,1f, pixelsPerUnit)
        );

        e3 = new Entity(Categories.GAME_OBJECT_1);
        e3.attachComponents(
                context.factoryTransform.create2d(-5, 0, 5, 1, 1, 0),
                new ComponentShapes2D() {
                    @Override
                    public void draw(ShapeBatch batch) {
                        batch.setColor(Color.RED);
                        batch.setDefaultLineWidth(0.1f);
                        batch.line(0,0, 4,4);
                    }
                }
        );

        e4 = new Entity(Categories.GAME_OBJECT_1);
        e4.attachComponents(
                context.factoryTransform.create2d(-5, 0, 5, 1, 1, 0),
                new ComponentShapes2D() {
                    @Override
                    public void draw(ShapeBatch batch) {
                        batch.setColor(Color.RED);
                        batch.setDefaultLineWidth(0.1f);
                        batch.circle(0,0, 1);
                    }
                }
        );

        e2.attachChild(e3);
        e2.attachChild(e4);

        container.addEntity(e1);
        container.addEntity(e2);
        container.addEntity(eCamera1);
    }

    @Override
    public void update(float delta) {
        container.update();


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
