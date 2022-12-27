package com.fos.game.starcontract.screens;

import com.badlogic.gdx.utils.ScreenUtils;
import com.fos.game.engine.core.context.ApplicationContext;
import com.fos.game.starcontract.context.GameContext;

public class MainMenuScreen extends GameScreen {

    public MainMenuScreen(GameContext context) {
        super(context);
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void frame(float delta) {
        // update

        // draw
        ScreenUtils.clear(1,0,0,1,true);
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

    }
}
