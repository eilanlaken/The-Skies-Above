package com.fos.game.scenes;

import com.fos.game.engine.core.context.ApplicationContext;
import com.fos.game.engine.core.context.Scene;
import com.kotcrab.vis.ui.VisUI;

public class TestSceneUI3 extends Scene {

    public TestSceneUI3(final ApplicationContext context) {
        super(context);
    }

    @Override
    public void show() {
        VisUI.load();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        VisUI.dispose();
    }
}
