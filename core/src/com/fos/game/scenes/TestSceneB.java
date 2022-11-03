package com.fos.game.scenes;

import com.fos.game.engine.core.context.ApplicationContext;
import com.fos.game.engine.core.context.Scene;
import com.fos.game.engine.core.graphics.g2d.GraphicsUtils;
import com.fos.game.engine.core.graphics.g2d.SpriteSheet;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.camera.ComponentCamera;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.systems.base.EntityContainer;

import java.util.HashMap;
import java.util.Map;

public class TestSceneB extends Scene {

    enum Categories {
        GAME_OBJECT,
        UI,
        CAMERA,
    }
    EntityContainer container;

    Entity eCamera;
    Entity e1, e2, e3;
    Entity eLight1, eLight2;

    public final float VIRTUAL_HEIGHT = 20;
    private int pixelsPerUnit = 53*2;

    public TestSceneB(final ApplicationContext context) {
        super(context);


    }

    @Override
    public void show() {
        container = new EntityContainer();

        eCamera = new Entity(Categories.CAMERA);
        eCamera.attachComponents(
                context.factoryTransform.create2d(0, 0, 0, 0, 1, 1),
                context.factoryCamera.createCamera2D(VIRTUAL_HEIGHT * GraphicsUtils.getAspectRatio(), VIRTUAL_HEIGHT, Categories.GAME_OBJECT)
        );

        e1 = new Entity(Categories.GAME_OBJECT);
        e1.attachComponents(
                context.factoryTransform.create2d(0, 0, 0, 0, 1, 1),
                context.factoryFrameAnimations2D.create("atlases/test/testSpriteSheet3.atlas", "a", 1,0.5f, pixelsPerUnit)
        );

        container.addEntity(e1);
        container.addEntity(eCamera);
    }

    @Override
    public void update(float delta) {
        container.update();
    }

    @Override
    public void resize(int width, int height) {
        ComponentCamera camera = (ComponentCamera) eCamera.components[ComponentType.GRAPHICS.ordinal()];
        camera.buildFrameBuffer();
        camera.lens.viewportWidth = camera.viewWorldWidth;
        camera.lens.viewportHeight = camera.viewWorldWidth * (float) height / width;
        camera.lens.update();
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
