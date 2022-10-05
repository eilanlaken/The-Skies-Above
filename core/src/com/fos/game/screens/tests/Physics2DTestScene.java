package com.fos.game.screens.tests;

import com.fos.game.engine.context.GameContext;
import com.fos.game.engine.context.Scene;
import com.fos.game.engine.ecs.components.animations2d.SpriteSheet;
import com.fos.game.engine.ecs.components.camera.FactoryCamera;
import com.fos.game.engine.ecs.components.scripts.ComponentScripts;
import com.fos.game.engine.ecs.components.transform.FactoryTransform2D;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.entities.EntityContainer;
import com.fos.game.scripts.test.OrangeSquareScript;
import com.fos.game.scripts.test.SimpleCameraScript;

import java.util.HashMap;
import java.util.Map;

public class Physics2DTestScene extends Scene {

    // entities
    private EntityContainer container;
    private Entity camera;
    private Entity orangeSquare;

    public Physics2DTestScene(final GameContext context) {
        super(context);
    }

    @Override
    protected void start() {
        container = new EntityContainer();

        camera = new Entity();
        camera.attachComponents(
                FactoryCamera.create2DCamera(SaveEntityScene.EntityLayers.LAYER_1, SaveEntityScene.EntityLayers.LAYER_2),
                new ComponentScripts(new SimpleCameraScript(camera))
        );

        orangeSquare = new Entity();
        orangeSquare.attachComponents(
                FactoryTransform2D.create(0,0),
                context.factoryAnimation2D.create("atlases/test/testSpriteSheet.atlas", "animatedArrow"),
                new ComponentScripts(new OrangeSquareScript(orangeSquare))
        );

        container.addEntity(camera);
        container.addEntity(orangeSquare);
    }

    @Override
    protected void update(float delta) {
        delta = Math.min(1f / 30f, delta);
        container.update(delta);
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
        assetNameClassMap.put("atlases/test/testSpriteSheet.atlas", SpriteSheet.class);
        return assetNameClassMap;
    }
}
