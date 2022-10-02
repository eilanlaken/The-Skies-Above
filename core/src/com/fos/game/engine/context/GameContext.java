package com.fos.game.engine.context;

import com.badlogic.gdx.Game;
import com.fos.game.engine.components.animations.FactoryAnimation;
import com.fos.game.engine.components.audio.FactoryAudio;
import com.fos.game.engine.components.camera.FactoryCamera;
import com.fos.game.engine.components.lights.FactoryLight;
import com.fos.game.engine.components.modelinstance.FactoryModelInstance;
import com.fos.game.engine.components.rigidbody.FactoryRigidBody2D;
import com.fos.game.engine.components.rigidbody.FactoryRigidBody3D;
import com.fos.game.engine.components.scripts.FactoryScripts;
import com.fos.game.engine.components.shapes.FactoryShape2D;
import com.fos.game.engine.components.signals.FactorySignalEmitter;
import com.fos.game.engine.components.signals.FactorySignalReceiver;
import com.fos.game.engine.components.transform.FactoryTransform2D;
import com.fos.game.engine.components.transform.FactoryTransform3D;
import com.fos.game.engine.files.assets.GameAssetManager;
import com.fos.game.engine.files.serialization.JsonConverter;
import com.fos.game.screens.loading.LoadingScreen;

public class GameContext extends Game {

	// service providers
	public GameAssetManager assetManager;
	public JsonConverter jsonConverter;
	// Component Factory(s)
	public FactoryAnimation factoryAnimation;
	public FactoryAudio factoryAudio;
	public FactoryCamera factoryCamera;
	public FactoryLight factoryLight;
	public FactoryModelInstance factoryModelInstance;
	public FactoryRigidBody2D factoryRigidBody2D;
	public FactoryRigidBody3D factoryRigidBody3D;
	public FactoryScripts factoryScripts;
	public FactoryShape2D factoryShape2D;
	public FactorySignalEmitter factorySignalEmitter;
	public FactorySignalReceiver factorySignalReceiver;
	public FactoryTransform2D factoryTransform2D;
	public FactoryTransform3D factoryTransform3D;

	@Override
	public void create () {
		// create service providers
		this.assetManager = new GameAssetManager();
		this.jsonConverter = new JsonConverter();
		// create factories
		this.factoryAnimation = new FactoryAnimation(this.assetManager, this.jsonConverter);
		this.factoryAudio = new FactoryAudio(this.assetManager, this.jsonConverter);
		this.factoryCamera = new FactoryCamera(this.assetManager, this.jsonConverter);
		this.factoryLight = new FactoryLight(this.assetManager, this.jsonConverter);
		this.factoryModelInstance = new FactoryModelInstance(this.assetManager, this.jsonConverter);
		this.factoryRigidBody2D = new FactoryRigidBody2D(this.assetManager, this.jsonConverter);
		this.factoryRigidBody3D = new FactoryRigidBody3D(this.assetManager, this.jsonConverter);
		this.factoryScripts = new FactoryScripts(this.assetManager, this.jsonConverter);
		this.factoryShape2D = new FactoryShape2D(this.assetManager, this.jsonConverter);
		this.factorySignalEmitter = new FactorySignalEmitter(this.assetManager, this.jsonConverter);
		this.factorySignalReceiver = new FactorySignalReceiver(this.assetManager, this.jsonConverter);
		this.factoryTransform2D = new FactoryTransform2D(this.assetManager, this.jsonConverter);
		this.factoryTransform3D = new FactoryTransform3D(this.assetManager, this.jsonConverter);
		// set the screen to the entry screen
		setScreen(new LoadingScreen(this));
	}

	@Override
	public void dispose () {
		assetManager.dispose();
	}


}
