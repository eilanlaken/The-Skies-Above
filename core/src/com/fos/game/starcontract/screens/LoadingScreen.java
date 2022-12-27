package com.fos.game.starcontract.screens;

import com.fos.game.engine.core.context.ApplicationContext;
import com.fos.game.engine.core.files.assets.GameAssetManager;
import com.fos.game.scenes.tests.TestSceneUI;
import com.fos.game.scenes.tests.TestSceneUI3;
import com.fos.game.starcontract.context.GameContext;

import java.util.Map;

public class LoadingScreen extends GameScreen {

    private final GameAssetManager assetManager;
    private boolean doneLoading = false;

    public LoadingScreen(final GameContext context) {
        super(context);
        this.assetManager = context.assetManager;
    }

    @Override
    public void show() {
        queueAssets();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void frame(float delta) {
        while (!doneLoading) {
            if (assetManager.update()) {
                doneLoading = true;
                context.setScreen(new MainMenuScreen(context));
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private void queueAssets() {
        Map<String, Class> assetsNameTypeMap = TestSceneUI.getRequiredAssetsNameTypeMap();
        for (Map.Entry<String, Class> entry : assetsNameTypeMap.entrySet()) {
            this.assetManager.load(entry.getKey(), entry.getValue());
        }
    }
}
