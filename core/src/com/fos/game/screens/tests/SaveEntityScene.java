package com.fos.game.screens.tests;

import com.badlogic.gdx.audio.Sound;
import com.fos.game.engine.context.GameContext;
import com.fos.game.engine.context.Scene_old;
import com.fos.game.engine.core.files.serialization.JsonConverter;
import com.fos.game.engine.core.graphics.g2d.SpriteSheet;
import com.fos.game.engine.ecs.components.animations2d.Animations2DData_old;
import com.fos.game.engine.ecs.components.animations2d.ComponentFrameAnimations2D;
import com.fos.game.engine.ecs.components.audio.ComponentSoundEffects;
import com.fos.game.engine.ecs.components.camera_old.FactoryCamera;
import com.fos.game.engine.ecs.components.scripts.ComponentScripts;
import com.fos.game.engine.ecs.components.transform2d_old.FactoryTransform2D;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.systems.base.EntityContainer;
import com.fos.game.scripts.test.OrangeSquareScript;
import com.fos.game.scripts.test.SimpleCameraScript;

import java.util.HashMap;
import java.util.Map;

public class SaveEntityScene extends Scene_old {

    // assets
    private EntityContainer container;

    // entities
    private Entity camera;
    private Entity orangeSquare;

    // serialization
    // testing
    ComponentSoundEffects componentSoundEffects;
    ComponentFrameAnimations2D componentFrameAnimations2D;
    JsonConverter jsonConverter;

    public enum EntityLayers {
        LAYER_1,
        LAYER_2,
    }

    public SaveEntityScene(final GameContext context) {
        super(context);
        this.jsonConverter = context.jsonConverter;
    }

    @Override
    public void start() {
        container = new EntityContainer();

        camera = new Entity();
        camera.attachComponents(
                FactoryCamera.create2DCamera(EntityLayers.LAYER_1, EntityLayers.LAYER_2),
                new ComponentScripts(new SimpleCameraScript(camera))
        );

        orangeSquare = new Entity();
        orangeSquare.attachComponents(
                FactoryTransform2D.create(0,0),
                context.factoryFrameAnimations2D.create("atlases/test/testSpriteSheet.atlas", "testArrowOrange"),
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
        Animations2DData_old data1 = new Animations2DData_old(null, null, 3);
        Animations2DData_old data2 = new Animations2DData_old(null, null, 3);

        componentFrameAnimations2D = context.factoryFrameAnimations2D.create(data1, data2);
        System.out.println();
        System.out.println("component: " + componentFrameAnimations2D);
        String json = jsonConverter.gson.toJson(componentFrameAnimations2D);
        System.out.println("json: " + json);
        //ComponentAnimations2D deserialized = context.factoryAnimation.create(json);
        //System.out.println("deserialized: " + deserialized.elapsedTime);
    }

    @Override
    public void update(float deltaTime) {
        final float delta = Math.min(1f / 30f, deltaTime);
        container.update();

        //componentSoundEffects.lastPlayingSoundEffect.sound.
    }

    @Override
    public void hide() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {

    }

    public static Map<String, Class> getRequiredAssetsNameTypeMap() {
        HashMap<String, Class> assetNameClassMap = new HashMap<>();
        assetNameClassMap.put("atlases/test/testSpriteSheet.atlas", SpriteSheet.class);
        assetNameClassMap.put("audio/sample.wav", Sound.class);
        assetNameClassMap.put("audio/beep.wav", Sound.class);
        return assetNameClassMap;
    }

}
