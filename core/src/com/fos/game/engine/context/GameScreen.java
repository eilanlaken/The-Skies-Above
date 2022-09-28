package com.fos.game.engine.context;

import com.badlogic.gdx.Screen;
import com.fos.game.engine.context.GameContext;

public abstract class GameScreen implements Screen {

    protected final GameContext context;

    public GameScreen(final GameContext context) {
        this.context = context;
    }

    public abstract void update(float deltaTime);

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

    }

}
