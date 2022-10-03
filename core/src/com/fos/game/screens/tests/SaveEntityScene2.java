package com.fos.game.screens.tests;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.fos.game.engine.context.GameContext;
import com.fos.game.engine.context.GameScreen;
import com.fos.game.engine.ecs.components.animations2d.AnimationData;
import com.fos.game.engine.ecs.components.animations2d.ComponentAnimations2D;
import com.fos.game.engine.ecs.components.animations2d.SpriteSheet;
import com.fos.game.engine.ecs.components.audio.ComponentSoundEffects;
import com.fos.game.engine.ecs.components.camera.FactoryCamera;
import com.fos.game.engine.ecs.components.scripts.ComponentScripts;
import com.fos.game.engine.ecs.components.transform.FactoryTransform2D;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.entities.EntityContainer;
import com.fos.game.engine.ecs.systems.renderer.base.Renderer;
import com.fos.game.engine.files.assets.GameAssetManager;
import com.fos.game.engine.files.serialization.JsonConverter;
import com.fos.game.scripts.test.OrangeSquareScript;
import com.fos.game.scripts.test.SimpleCameraScript;

import java.util.HashMap;
import java.util.Map;

public class SaveEntityScene2 extends GameScreen {

    private final GameAssetManager assetManager;

    // assets
    private EntityContainer container;

    // rendering
    private Renderer renderer;

    // entities
    private Entity camera;
    private Entity orangeSquare;

    // serialization
    // testing
    ComponentSoundEffects componentSoundEffects;
    ComponentAnimations2D componentAnimations2D;
    JsonConverter jsonConverter;

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
        container = new EntityContainer(false, true);

        camera = new Entity();
        camera.attachComponents(
                FactoryCamera.create2DCamera(EntityLayers.LAYER_1, EntityLayers.LAYER_2),
                new ComponentScripts(new SimpleCameraScript(camera))
        );

        orangeSquare = new Entity();
        orangeSquare.attachComponents(
                FactoryTransform2D.create(0,0),
                context.factoryAnimation.create("atlases/test/testSpriteSheet.atlas", "testArrowOrange"),
                context.factoryAudio.create("audio/sample.wav", "audio/beep.wav"),
                new ComponentScripts(new OrangeSquareScript(orangeSquare))
        );

        testSerialization1();
        testSerialization2();

        container.addEntity(camera);
        container.addEntity(orangeSquare);
    }

    private void testSerialization1() {
        componentSoundEffects = context.factoryAudio.create("audio/sample.wav", "audio/beep.wav");
        componentSoundEffects.play("audio/beep.wav");
        System.out.println("component: " + componentSoundEffects);
        String json = jsonConverter.gson.toJson(componentSoundEffects);
        System.out.println("json: " + json);
        //ComponentSoundEffects deserialize = context.factoryAudio.createFromJson(json);
        //deserialize.get(0).sound.play();
    }

    private void testSerialization2() {
        AnimationData data1 = new AnimationData("atlases/test/testSpriteSheet.atlas", "testArrowOrange", 1, Animation.PlayMode.LOOP);
        AnimationData data2 = new AnimationData("atlases/test/testSpriteSheet.atlas", "testArrowGreen", 2, Animation.PlayMode.LOOP_PINGPONG);

        componentAnimations2D = context.factoryAnimation.create(data1, data2);
        System.out.println();
        System.out.println("component: " + componentAnimations2D);
        String json = jsonConverter.gson.toJson(componentAnimations2D);
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

        //componentSoundEffects.lastPlayingSoundEffect.sound.
    }

    public static Map<String, Class> getRequiredAssetsNameTypeMap() {
        HashMap<String, Class> assetNameClassMap = new HashMap<>();
        assetNameClassMap.put("atlases/test/testSpriteSheet.atlas", SpriteSheet.class);
        assetNameClassMap.put("audio/sample.wav", Sound.class);
        assetNameClassMap.put("audio/beep.wav", Sound.class);
        return assetNameClassMap;
    }
}
