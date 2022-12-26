package com.fos.game.starcontract.context;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.fos.game.engine.core.files.assets.GameAssetManager;
import com.fos.game.starcontract.screens.LoadingScreen;

public class GameContext extends Game {

    public GameAssetManager assetManager;

    @Override
    public void create() {
        this.assetManager = new GameAssetManager();
        setScreen(new LoadingScreen(this));
    }
}
