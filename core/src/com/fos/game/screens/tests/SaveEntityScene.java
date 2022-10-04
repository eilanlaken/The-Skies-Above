package com.fos.game.screens.tests;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.components.animations2d.AnimationData;
import com.fos.game.engine.ecs.components.animations2d.ComponentAnimations2D;
import com.fos.game.engine.ecs.components.animations2d.SpriteSheet;
import com.fos.game.engine.ecs.components.audio.ComponentSoundEffects;
import com.fos.game.engine.ecs.components.audio.SoundEffect;
import com.fos.game.engine.ecs.components.camera.FactoryCamera;
import com.fos.game.engine.ecs.components.scripts.ComponentScripts;
import com.fos.game.engine.ecs.components.transform.FactoryTransform2D;
import com.fos.game.engine.context.GameContext;
import com.fos.game.engine.context.GameScreen;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.entities.EntityContainer;
import com.fos.game.engine.files.assets.GameAssetManager;
import com.fos.game.engine.files.serialization.JsonConverter;
import com.fos.game.engine.ecs.systems.renderer.base.Renderer;
import com.fos.game.scripts.test.OrangeSquareScript;
import com.fos.game.scripts.test.SimpleCameraScript;

import java.util.HashMap;
import java.util.Map;

public class SaveEntityScene extends GameScreen {

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

    public SaveEntityScene(final GameContext context) {
        super(context);
        this.jsonConverter = context.jsonConverter;
        this.assetManager = context.assetManager;
        this.renderer = new Renderer();
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
    }

    private void setupScene() {
        container = new EntityContainer();

        camera = new Entity();
        camera.attachComponents(
                FactoryCamera.create2DCamera(EntityLayers.LAYER_1, EntityLayers.LAYER_2),
                new ComponentScripts(new SimpleCameraScript(camera))
        );

        orangeSquare = new Entity();
        orangeSquare.attachComponents(
                FactoryTransform2D.create(0,0),
                context.factoryAnimation.create("atlases/test/testSpriteSheet.atlas", "testArrowOrange"),
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
        ComponentSoundEffects deserialize = context.factoryAudio.createFromJson(json);
        deserialize.get(0).sound.play();
    }

    private void testSerialization2() {
        AnimationData data1 = new AnimationData("atlases/test/testSpriteSheet.atlas", "testArrowOrange", 1, Animation.PlayMode.LOOP);
        AnimationData data2 = new AnimationData("atlases/test/testSpriteSheet.atlas", "testArrowGreen", 2, Animation.PlayMode.LOOP_PINGPONG);

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

        //renderer.process(container);
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
