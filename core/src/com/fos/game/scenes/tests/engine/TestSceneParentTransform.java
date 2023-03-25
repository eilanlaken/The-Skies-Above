package com.fos.game.scenes.tests.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.fos.game.engine.core.context.ApplicationContext;
import com.fos.game.engine.core.context.Scene;
import com.fos.game.engine.core.graphics.g2d.GraphicsUtils;
import com.fos.game.engine.core.graphics.g2d.SpriteSheet;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.camera.ComponentCamera;
import com.fos.game.engine.ecs.components.transform.ComponentTransform2D;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.systems.base.EntityContainer;

import java.util.HashMap;
import java.util.Map;

public class TestSceneParentTransform extends Scene {

    enum Categories {
        GAME_OBJECT_1,
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
                context.factoryTransform.create2d(0, 0, 0, 1, 1, 0),
                context.factoryCamera.createCamera2D(VIRTUAL_HEIGHT * GraphicsUtils.getAspectRatio(), VIRTUAL_HEIGHT, Categories.GAME_OBJECT_1)
        );

        e1 = new Entity(Categories.GAME_OBJECT_1);
        e1.attachComponents(
                context.factoryTransform.create2d(0, 0, 0, 1, 1, 0),
                context.factoryFrameAnimations2D.create("atlases/test/testSpriteSheet3.atlas", "a", 1,1f, pixelsPerUnit)
        );

        e2 = new Entity(Categories.GAME_OBJECT_1);
        e2.attachComponents(
                context.factoryTransform.create2d(-5, 0, 0, 1, 1, 0),
                context.factoryFrameAnimations2D.create("atlases/test/testSpriteSheet3.atlas", "b", 1,1f, pixelsPerUnit)
        );


        e1.attachChild(e2);

        container.addEntity(e1);
        container.addEntity(eCamera1);
    }

    @Override
    public void update(float delta) {
        container.update();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            ComponentTransform2D transform2D = (ComponentTransform2D) e1.getComponent(ComponentType.TRANSFORM);
            transform2D.x -= 0.05f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            ComponentTransform2D transform2D = (ComponentTransform2D) e1.getComponent(ComponentType.TRANSFORM);
            transform2D.x += 0.05f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            ComponentTransform2D transform2D = (ComponentTransform2D) e1.getComponent(ComponentType.TRANSFORM);
            transform2D.y += 0.05f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            ComponentTransform2D transform2D = (ComponentTransform2D) e1.getComponent(ComponentType.TRANSFORM);
            transform2D.y -= 0.05f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            ComponentTransform2D transform2D = (ComponentTransform2D) e1.getComponent(ComponentType.TRANSFORM);
            transform2D.angle += 1 * MathUtils.degreesToRadians;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            ComponentTransform2D transform2D = (ComponentTransform2D) e1.getComponent(ComponentType.TRANSFORM);
            transform2D.angle -= 1 * MathUtils.degreesToRadians;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            ComponentTransform2D transform2D = (ComponentTransform2D) e1.getComponent(ComponentType.TRANSFORM);
            transform2D.scaleY += 1 * MathUtils.degreesToRadians;
            transform2D.scaleX += 1 * MathUtils.degreesToRadians;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            ComponentTransform2D transform2D = (ComponentTransform2D) e1.getComponent(ComponentType.TRANSFORM);
            transform2D.scaleY -= 1 * MathUtils.degreesToRadians;
            transform2D.scaleX -= 1 * MathUtils.degreesToRadians;
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
