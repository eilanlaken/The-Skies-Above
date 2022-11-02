package com.fos.game.engine.core.context;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.fos.game.engine.core.files.assets.GameAssetManager;
import com.fos.game.engine.core.files.serialization.JsonConverter;
import com.fos.game.engine.ecs.components.animations2d.FactoryFrameAnimations2D;
import com.fos.game.engine.ecs.components.audio.FactoryAudio;
import com.fos.game.engine.ecs.components.camera.FactoryCamera;
import com.fos.game.engine.ecs.components.lights2d.FactoryLight2D;
import com.fos.game.engine.ecs.components.lights3d.FactoryLight3D;
import com.fos.game.engine.ecs.components.physics2d.FactoryRigidBody2D;
import com.fos.game.engine.ecs.components.physics3d.FactoryRigidBody3D;
import com.fos.game.engine.ecs.components.logic.FactoryLogic;
import com.fos.game.engine.ecs.components.shapes.FactoryShape2D;
import com.fos.game.engine.ecs.components.signals.FactorySignalBox;
import com.fos.game.engine.ecs.components.transform.FactoryTransform;
import com.fos.game.scenes.LoadingScene;

public class ApplicationContext implements ApplicationListener {

    public Scene scene;

    // service providers
    public GameAssetManager assetManager;
    public JsonConverter jsonConverter;
    // Component Factory(s)
    public FactoryFrameAnimations2D factoryFrameAnimations2D;
    public FactoryAudio factoryAudio;
    public FactoryCamera factoryCamera;
    public FactoryLight2D factoryLight2D;
    public FactoryLight3D factoryLight3D;
    public FactoryRigidBody2D factoryRigidBody2D;
    public FactoryRigidBody3D factoryRigidBody3D;
    public FactoryLogic factoryLogic;
    public FactoryShape2D factoryShape2D;
    public FactorySignalBox factorySignalBox;
    public FactoryTransform factoryTransform;

    @Override
    public void create() {
        this.assetManager = new GameAssetManager();
        this.jsonConverter = new JsonConverter();
        this.factoryFrameAnimations2D = new FactoryFrameAnimations2D(this.assetManager, this.jsonConverter);
        this.factoryAudio = new FactoryAudio(this.assetManager, this.jsonConverter);
        this.factoryCamera = new FactoryCamera(this.assetManager, this.jsonConverter);
        this.factoryLight2D = new FactoryLight2D(this.assetManager, this.jsonConverter);
        this.factoryLight3D = new FactoryLight3D(this.assetManager, this.jsonConverter);
        this.factoryRigidBody2D = new FactoryRigidBody2D(this.assetManager, this.jsonConverter);
        this.factoryRigidBody3D = new FactoryRigidBody3D(this.assetManager, this.jsonConverter);
        this.factoryLogic = new FactoryLogic(this.assetManager, this.jsonConverter);
        this.factoryShape2D = new FactoryShape2D(this.assetManager, this.jsonConverter);
        this.factorySignalBox = new FactorySignalBox(this.assetManager, this.jsonConverter);
        this.factoryTransform = new FactoryTransform(this.assetManager, this.jsonConverter);
        playScene(new LoadingScene(this));
    }

    @Override
    public void resize(int width, int height) {
        if (scene != null) scene.resize(width, height);
    }

    @Override
    public void render() {
        if (scene != null) scene.update(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void pause() {
        if (scene != null) scene.pause();
    }

    @Override
    public void resume() {
        if (scene != null) scene.resume();
    }

    @Override
    public void dispose() {
        if (scene != null) scene.dispose();
    }

    public void playScene(Scene scene) {
        if (this.scene != null) this.scene.hide();
        this.scene = scene;
        if (this.scene != null) {
            this.scene.show();
            this.scene.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }
}
