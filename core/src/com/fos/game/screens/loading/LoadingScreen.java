package com.fos.game.screens.loading;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.fos.game.engine.context.GameContext;
import com.fos.game.engine.context.GameScreen;
import com.fos.game.engine.files.assets.GameAssetManager;
import com.fos.game.screens.tests.SaveEntityScene2;

import java.util.Map;

public class LoadingScreen extends GameScreen {

    private final GameAssetManager assetManager;
    private boolean doneLoading = false;

    public LoadingScreen(final GameContext context) {
        super(context);
        assetManager = context.assetManager;
    }

    @Override
    public void show() {
        queueAssets();
    }

    private void queueAssets() {
        Map<String, Class> assetsNameTypeMap = SaveEntityScene2.getRequiredAssetsNameTypeMap();
        for (Map.Entry<String, Class> entry : assetsNameTypeMap.entrySet()) {
            this.assetManager.load(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);
        Gdx.gl.glClearColor(0,1,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void update(float deltaTime) {
        while (!doneLoading) {
            if (assetManager.update()) {
                doneLoading = true;
                context.setScreen(new SaveEntityScene2(context));
            }
        }
    }

}
