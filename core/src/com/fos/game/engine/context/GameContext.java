package com.fos.game.engine.context;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fos.game.engine.files.assets.GameAssetManager;
import com.fos.game.screens.loading.LoadingScreen;

public class GameContext extends Game {

	// service providers
	public GameAssetManager assetManager;
    public SpriteBatch batch;

	@Override
	public void create () {
		initServiceProviders();
		runDevTools();
		setScreen(new LoadingScreen(this));
	}

	private void initServiceProviders() {
		assetManager = new GameAssetManager();
		batch = new SpriteBatch();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		assetManager.dispose();
	}

	private void runDevTools() {
		SystemQueries systemQueries = new SystemQueries();
		systemQueries.getMaxTextureSize();

		//BatchImageResize batchImageResize = new BatchImageResize();
		//batchImageResize.run();

		//HoloCardsGenerator holoCardsGenerator = new HoloCardsGenerator();
		//holoCardsGenerator.run();

		//BatchCopyPasteCardsSource bcpCardsSource = new BatchCopyPasteCardsSource();
		//bcpCardsSource.run();

	}

}
