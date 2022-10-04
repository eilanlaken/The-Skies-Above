package com.fos.game.engine.context;

import com.badlogic.gdx.Screen;

public abstract class GameScreen implements Screen {

    protected final GameContext context;

    public GameScreen(final GameContext context) {
        this.context = context;
    }

    @Override
    public final void show() {
        this.start();
    }

    protected abstract void start();

    @Override
    public final void render(float delta) {
        this.update(delta);
    }

    protected abstract void update(float delta);

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

}
