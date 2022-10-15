package com.fos.game.screens.loading;

import com.fos.game.engine.context.GameContext;
import com.fos.game.engine.context.Scene;
import com.fos.game.engine.core.files.assets.GameAssetManager;
import com.fos.game.screens.tests.BigSystemsTestScene2;

import java.util.Map;

public class LoadingScreen extends Scene {

    private final GameAssetManager assetManager;
    private boolean doneLoading = false;

    public LoadingScreen(final GameContext context) {
        super(context);
        this.assetManager = context.assetManager;
    }

    @Override
    protected void start() {
        queueAssets();
    }

    @Override
    protected void update(float delta) {
        while (!doneLoading) {
            if (assetManager.update()) {
                doneLoading = true;
                context.setScreen(new BigSystemsTestScene2(context));
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
        Map<String, Class> assetsNameTypeMap = BigSystemsTestScene2.getRequiredAssetsNameTypeMap();
        for (Map.Entry<String, Class> entry : assetsNameTypeMap.entrySet()) {
            this.assetManager.load(entry.getKey(), entry.getValue());
        }
    }
}
