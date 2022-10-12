package com.fos.game.engine.context;

import com.badlogic.gdx.Game;
import com.fos.game.engine.ecs.components.animations2d.FactoryAnimation2D;
import com.fos.game.engine.ecs.components.audio.FactoryAudio;
import com.fos.game.engine.ecs.components.camera.FactoryCamera;
import com.fos.game.engine.ecs.components.camera2d.FactoryCamera2D;
import com.fos.game.engine.ecs.components.camera3d.FactoryCamera3D;
import com.fos.game.engine.ecs.components.lights2d.FactoryLight2D;
import com.fos.game.engine.ecs.components.lights3d.FactoryLight3D;
import com.fos.game.engine.ecs.components.modelinstance.FactoryModelInstance;
import com.fos.game.engine.ecs.components.physics2d.FactoryRigidBody2D;
import com.fos.game.engine.ecs.components.physics3d.FactoryRigidBody3D;
import com.fos.game.engine.ecs.components.scripts.FactoryScripts;
import com.fos.game.engine.ecs.components.shapes.FactoryShape2D;
import com.fos.game.engine.ecs.components.signals.FactorySignalBox;
import com.fos.game.engine.ecs.components.transform2d.FactoryTransform2D;
import com.fos.game.engine.ecs.components.transform3d.FactoryTransform3D;
import com.fos.game.engine.files.assets.GameAssetManager;
import com.fos.game.engine.files.serialization.JsonConverter;
import com.fos.game.screens.loading.LoadingScreen;

public class GameContext extends Game {

	// service providers
	public GameAssetManager assetManager;
	public JsonConverter jsonConverter;
	// Component Factory(s)
	public FactoryAnimation2D factoryAnimation2D;
	public FactoryAudio factoryAudio;
	@Deprecated
	public FactoryCamera factoryCamera;
	public FactoryCamera2D factoryCamera2D;
	public FactoryCamera3D factoryCamera3D;
	public FactoryLight2D factoryLight2D;
	public FactoryLight3D factoryLight3D;
	public FactoryModelInstance factoryModelInstance;
	public FactoryRigidBody2D factoryRigidBody2D;
	public FactoryRigidBody3D factoryRigidBody3D;
	public FactoryScripts factoryScripts;
	public FactoryShape2D factoryShape2D;
	public FactorySignalBox factorySignalBox;
	public FactoryTransform2D factoryTransform2D;
	public FactoryTransform3D factoryTransform3D;

	@Override
	public void create () {
		// create service providers
		this.assetManager = new GameAssetManager();
		this.jsonConverter = new JsonConverter();
		// create factories
		this.factoryAnimation2D = new FactoryAnimation2D(this.assetManager, this.jsonConverter);
		this.factoryAudio = new FactoryAudio(this.assetManager, this.jsonConverter);
		this.factoryCamera = new FactoryCamera(this.assetManager, this.jsonConverter);
		this.factoryCamera2D = new FactoryCamera2D(this.assetManager, this.jsonConverter);
		this.factoryCamera3D = new FactoryCamera3D(this.assetManager, this.jsonConverter);
		this.factoryLight2D = new FactoryLight2D(this.assetManager, this.jsonConverter);
		this.factoryLight3D = new FactoryLight3D(this.assetManager, this.jsonConverter);
		this.factoryModelInstance = new FactoryModelInstance(this.assetManager, this.jsonConverter);
		this.factoryRigidBody2D = new FactoryRigidBody2D(this.assetManager, this.jsonConverter);
		this.factoryRigidBody3D = new FactoryRigidBody3D(this.assetManager, this.jsonConverter);
		this.factoryScripts = new FactoryScripts(this.assetManager, this.jsonConverter);
		this.factoryShape2D = new FactoryShape2D(this.assetManager, this.jsonConverter);
		this.factorySignalBox = new FactorySignalBox(this.assetManager, this.jsonConverter);
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
