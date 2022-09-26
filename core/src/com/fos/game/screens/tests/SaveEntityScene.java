package com.fos.game.screens.tests;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.fos.game.engine.components.animation.FactoryAnimation;
import com.fos.game.engine.components.camera.FactoryCamera;
import com.fos.game.engine.components.scripts.ComponentScripts;
import com.fos.game.engine.components.transform.FactoryTransform2D;
import com.fos.game.engine.context.GameContext;
import com.fos.game.engine.entities.Entity;
import com.fos.game.engine.entities.EntityContainer;
import com.fos.game.engine.files.GameAssetManager;
import com.fos.game.engine.files.Serializer;
import com.fos.game.engine.renderer.system.Renderer;
import com.fos.game.engine.screens.GameScreen;
import com.fos.game.scripts.common.ParentTransform2D;
import com.fos.game.scripts.test.OrangeSquareScript;
import com.fos.game.scripts.test.SimpleCameraScript;

import java.util.HashMap;
import java.util.Map;

public class SaveEntityScene extends GameScreen {

    private final GameAssetManager assetManager;

    // models
    private TextureAtlas testSpriteSheet;
    private EntityContainer container;

    // rendering
    private Renderer renderer;

    // entities
    private Entity camera;
    private Entity orangeSquare, greenSquare;

    // serialization
    private Serializer serializer;

    public enum EntityLayers {
        LAYER_1,
        LAYER_2,
    }

    public SaveEntityScene(final GameContext context) {
        super(context);
        this.serializer = new Serializer();
        this.assetManager = context.assetManager;
        this.renderer = new Renderer(false);
    }

    @Override
    public void show() {
        setupAssets();
        setupScene();
    }

    private void setupAssets() {
        testSpriteSheet = assetManager.get("atlases/test/testSpriteSheet.atlas", TextureAtlas.class);
    }

    private void setupScene() {
        container = new EntityContainer(false, true);

        camera = new Entity();
        camera.attachComponents(
                FactoryCamera.create2DCamera(SimpleScene.EntityLayers.LAYER_1, SimpleScene.EntityLayers.LAYER_2),
                new ComponentScripts(new SimpleCameraScript(camera))
        );

        orangeSquare = new Entity();
        orangeSquare.attachComponents(
                FactoryTransform2D.create(0,0),
                FactoryAnimation.create(testSpriteSheet, "testArrowOrange"),
                new ComponentScripts(new OrangeSquareScript(orangeSquare))
        );

        greenSquare = new Entity(
                FactoryTransform2D.create(50,50),
                FactoryAnimation.create(testSpriteSheet, "testArrowGreen"),
                new ComponentScripts(new ParentTransform2D(orangeSquare))
        );


        Entity entity = new Entity(
                FactoryTransform2D.create(50,50),
                FactoryAnimation.create(testSpriteSheet, "testArrowGreen")
        );

        String json = serializer.gson.toJson(entity);
        System.out.println(json);
        Entity entity1 = serializer.gson.fromJson(json, Entity.class);
        System.out.println("entity1 " + entity1);
        String json2 = serializer.gson.toJson(entity1);
        System.out.println(json2);

        container.addEntity(camera);
        container.addEntity(orangeSquare);
        container.addEntity(greenSquare);
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);

        renderer.render(container);
    }

    @Override
    public void update(float deltaTime) {
        final float delta = Math.min(1f / 30f, deltaTime);
        container.update(delta);
    }

    public static Map<String, Class> getRequiredAssetsNameTypeMap() {
        HashMap<String, Class> assetNameClassMap = new HashMap<>();
        assetNameClassMap.put("atlases/test/testSpriteSheet.atlas", TextureAtlas.class);
        return assetNameClassMap;
    }
}
