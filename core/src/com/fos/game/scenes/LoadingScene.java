package com.fos.game.scenes;

import com.fos.game.engine.core.context.ApplicationContext;
import com.fos.game.engine.core.context.Scene;
import com.fos.game.engine.core.files.assets.GameAssetManager;
import com.fos.game.scenes.tests.TestSceneB;
import com.fos.game.scenes.tests.TestSceneUI;
import com.fos.game.scenes.tests.TestSceneUI3;
import com.fos.game.scenes.tests.TestSceneUI4;

import java.util.Map;

public class LoadingScene extends Scene {

    private final GameAssetManager assetManager;
    private boolean doneLoading = false;

    public LoadingScene(final ApplicationContext context) {
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
    public void update(float delta) {
        while (!doneLoading) {
            if (assetManager.update()) {
                doneLoading = true;
                context.playScene(new TestSceneB(context));
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
        Map<String, Class> assetsNameTypeMap = TestSceneB.getRequiredAssetsNameTypeMap();
        for (Map.Entry<String, Class> entry : assetsNameTypeMap.entrySet()) {
            this.assetManager.load(entry.getKey(), entry.getValue());
        }
    }
}
