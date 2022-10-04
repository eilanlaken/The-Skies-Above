package com.fos.game.screens.loading;

import com.fos.game.engine.context.GameContext;
import com.fos.game.engine.context.GameScreen2;
import com.fos.game.engine.files.assets.GameAssetManager;
import com.fos.game.screens.tests.SaveEntityScene3;

import java.util.Map;

public class LoadingScreen2 extends GameScreen2 {

    private final GameAssetManager assetManager;
    private boolean doneLoading = false;

    public LoadingScreen2(final GameContext context) {
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
                context.setScreen(new SaveEntityScene3(context));
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {

    }

    private void queueAssets() {
        Map<String, Class> assetsNameTypeMap = SaveEntityScene3.getRequiredAssetsNameTypeMap();
        for (Map.Entry<String, Class> entry : assetsNameTypeMap.entrySet()) {
            this.assetManager.load(entry.getKey(), entry.getValue());
        }
    }
}
