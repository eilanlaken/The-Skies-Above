package com.fos.game.screens.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.fos.game.engine.context.GameContext;
import com.fos.game.engine.context.Scene_old;
import com.fos.game.engine.core.graphics.g2d.SpriteSheet;
import com.fos.game.engine.ecs.components.animations2d.ComponentAnimations2D;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.cameras.ComponentCamera2D;
import com.fos.game.engine.ecs.components.transform2d.ComponentTransform2D;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.entities.EntityManager;
import com.fos.game.scripts.test.PeriodicReadTestScript;
import com.fos.game.scripts.test.PeriodicSendTestScript;

import java.util.HashMap;
import java.util.Map;

public class BigSystemsTestScene2 extends Scene_old {

    public enum Layers {
        CAMERA,
        BULLET,
        CHARACTER,
        UI,
        MOUSE,
    }

    EntityManager container;

    // refs
    ComponentTransform2D transform2DCameraBullets;
    ComponentCamera2D camera2DBullets;

    Entity button;

    public BigSystemsTestScene2(final GameContext context) {
        super(context);
    }

    @Override
    protected void start() {
        container = new EntityManager();

        button = new Entity(Layers.UI);
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

        // TODO: implement
        Entity mouse = new Entity(Layers.MOUSE);
        mouse.attachComponents(
                context.factorySignalBox.create(),
                context.factoryAnimation2D.create("atlases/test/testSpriteSheet2.atlas", "purple"),
                context.factoryScripts.create(new PeriodicSendTestScript(mouse))
        );
        container.addEntity(mouse);

        Entity cameraScene = new Entity(Layers.CAMERA);
        transform2DCameraBullets = context.factoryTransform2D.create(0,0,1,0,1,1);
        camera2DBullets = context.factoryCamera2D.createCamera2D(30, 30 * (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth(), Layers.CHARACTER, Layers.BULLET);
        camera2DBullets.depth = -1;
        cameraScene.attachComponents(
                transform2DCameraBullets,
                camera2DBullets,
                context.factorySignalBox.create(),
                context.factoryScripts.create(new PeriodicReadTestScript(cameraScene))
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

        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            ComponentAnimations2D animations2D = (ComponentAnimations2D) button.getComponent(ComponentType.ANIMATIONS_2D);
            animations2D.active = !animations2D.active;
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
