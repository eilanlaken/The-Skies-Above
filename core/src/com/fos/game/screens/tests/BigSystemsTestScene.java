package com.fos.game.screens.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.fos.game.engine.context.GameContext;
import com.fos.game.engine.context.Scene;
import com.fos.game.engine.core.graphics.g2d.SpriteSheet;
import com.fos.game.engine.ecs.components.cameras.ComponentCamera2D;
import com.fos.game.engine.ecs.components.transform2d.ComponentTransform2D;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.entities.EntityContainer;

import java.util.HashMap;
import java.util.Map;

public class BigSystemsTestScene extends Scene {

    public enum Layers {
        CAMERA,
        BULLET,
        CHARACTER,
        INVISIBLE,
        UI,
    }

    EntityContainer container;

    // refs
    ComponentTransform2D transform2DCameraBullets;
    ComponentCamera2D camera2DBullets;

    public BigSystemsTestScene(final GameContext context) {
        super(context);
    }

    @Override
    protected void start() {
        container = new EntityContainer();

        Entity button = new Entity(Layers.UI);
        button.attachComponents(
                context.factoryTransform2D.create(0,0,-11,0,1,1),
                context.factoryAnimation2D.create("atlases/test/testSpriteSheet2.atlas", "blue")
        );
        container.addEntity(button);

        for (int i = 0; i < 5; i++) {
            Entity entity = new Entity(Layers.CHARACTER);
            entity.attachComponents(
              context.factoryTransform2D.create(-2 + i,0,0,0,1,1),
              context.factoryAnimation2D.create("atlases/test/testSpriteSheet2.atlas", "green")
            );
            container.addEntity(entity);
        }

        for (int i = 0; i < 5; i++) {
            Entity entity = new Entity(Layers.BULLET);
            entity.attachComponents(
                    context.factoryTransform2D.create(-2 + i,0,-1,0,1,1),
                    context.factoryAnimation2D.create("atlases/test/testSpriteSheet2.atlas", "orange")
            );
            container.addEntity(entity);
        }

        Entity cameraScene = new Entity(Layers.CAMERA);
        transform2DCameraBullets = context.factoryTransform2D.create(0,0,1,0,1,1);
        camera2DBullets = context.factoryCamera2D.createCamera2D(30, 30 * (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth(), Layers.CHARACTER, Layers.BULLET);
        camera2DBullets.depth = 6;
        cameraScene.attachComponents(
                transform2DCameraBullets,
                camera2DBullets
        );
        container.addEntity(cameraScene);

        Entity cameraUI = new Entity(Layers.CAMERA);
        cameraUI.attachComponents(
                context.factoryTransform2D.create(0,0,1,0,1,1),
                context.factoryCamera2D.createCamera2D(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Layers.UI)
        );
        container.addEntity(cameraUI);
    }

    @Override
    protected void update(float delta) {
        container.update();
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            transform2DCameraBullets.translate(-1,0);
            System.out.println("transform x: " + transform2DCameraBullets.transform.getPosition().x);
            System.out.println("camera lens x: " + camera2DBullets.lens.position.x);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public static Map<String, Class> getRequiredAssetsNameTypeMap() {
        HashMap<String, Class> assetNameClassMap = new HashMap<>();
        assetNameClassMap.put("atlases/test/testSpriteSheet2.atlas", SpriteSheet.class);
        return assetNameClassMap;
    }
}
