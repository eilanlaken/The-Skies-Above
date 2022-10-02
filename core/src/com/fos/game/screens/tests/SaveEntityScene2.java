package com.fos.game.screens.tests;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.components.animation.AnimationData;
import com.fos.game.engine.components.animation.ComponentAnimations2D;
import com.fos.game.engine.components.animation.FactoryAnimation;
import com.fos.game.engine.components.animation.SpriteSheet;
import com.fos.game.engine.components.audio.ComponentSoundEffects;
import com.fos.game.engine.components.audio.SoundEffect;
import com.fos.game.engine.components.camera.FactoryCamera;
import com.fos.game.engine.components.scripts.ComponentScripts;
import com.fos.game.engine.components.transform.FactoryTransform2D;
import com.fos.game.engine.context.GameContext;
import com.fos.game.engine.context.GameScreen;
import com.fos.game.engine.entities.Entity;
import com.fos.game.engine.entities.EntityContainer;
import com.fos.game.engine.files.assets.GameAssetManager;
import com.fos.game.engine.files.serialization.JsonConverter;
import com.fos.game.engine.renderer.system.Renderer;
import com.fos.game.scripts.test.OrangeSquareScript;
import com.fos.game.scripts.test.SimpleCameraScript;

import java.util.HashMap;
import java.util.Map;

public class SaveEntityScene2 extends GameScreen {

    private final GameAssetManager assetManager;

    // assets
    private SpriteSheet testSpriteSheet;
    private EntityContainer container;

    // rendering
    private Renderer renderer;

    // entities
    private Entity camera;
    private Entity orangeSquare;

    // serialization
    private JsonConverter jsonConverter;

    public enum EntityLayers {
        LAYER_1,
        LAYER_2,
    }

    public SaveEntityScene2(final GameContext context) {
        super(context);
        this.jsonConverter = context.jsonConverter;
        this.assetManager = context.assetManager;
        this.renderer = new Renderer(false);
    }

    @Override
    public void show() {
        setupAssets();
        setupScene();
    }

    private void setupAssets() {
        testSpriteSheet = assetManager.get("atlases/test/testSpriteSheet.atlas", SpriteSheet.class);
        Array<TextureAtlas.AtlasRegion> regionArray = testSpriteSheet.findRegions("testArrowOrange");
        System.out.println("width: " + regionArray.get(0).getRegionWidth());
        System.out.println("filepath:  " + testSpriteSheet.filepath);
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

        testSerialization1();
        testSerialization2();

        container.addEntity(camera);
        container.addEntity(orangeSquare);
    }

    private void testSerialization1() {
        ComponentSoundEffects component = context.factoryAudio.create("audio/sample.wav", "audio/sample2.wav");
        System.out.println("component: " + component);
        String json = jsonConverter.gson.toJson(component);
        System.out.println("json: " + json);
        ComponentSoundEffects deserialized = context.factoryAudio.createFromJson(json);
        deserialized.get(0).sound.play();
    }

    private void testSerialization2() {
        AnimationData data1 = new AnimationData(testSpriteSheet, "testArrowOrange", 1, Animation.PlayMode.LOOP);
        AnimationData data2 = new AnimationData(testSpriteSheet, "testArrowGreen", 2, Animation.PlayMode.LOOP_PINGPONG);

        ComponentAnimations2D component = context.factoryAnimation.create(data1, data2);
        System.out.println();
        System.out.println("component: " + component);
        String json = jsonConverter.gson.toJson(component);
        System.out.println("json: " + json);
        //ComponentAnimations2D deserialized = context.factoryAnimation.create(json);
        //System.out.println("deserialized: " + deserialized.elapsedTime);
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
        assetNameClassMap.put("atlases/test/testSpriteSheet.atlas", SpriteSheet.class);
        assetNameClassMap.put("audio/sample.wav", SoundEffect.class);
        assetNameClassMap.put("audio/sample2.wav", SoundEffect.class);
        return assetNameClassMap;
    }
}
