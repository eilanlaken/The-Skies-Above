package com.fos.game.engine.core.context;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;

public abstract class Scene {

    protected final ApplicationContext context;

    public Scene(ApplicationContext context) {
        this.context = context;
    }

    /** Called when this screen becomes the current screen for a {@link Game}. */
    public abstract void show();

    /** Called every frame. This is the "game loop" method. */
    public abstract void update(float delta);

    /** @see ApplicationListener#resize(int, int) */
    public abstract void resize (int width, int height);

    /** @see ApplicationListener#pause() */
    public abstract void pause();

    /** @see ApplicationListener#resume() */
    public abstract void resume();

    /** Called when this screen is no longer the current screen for a {@link Game}. */
    public abstract void hide();

    /** Called when this screen should release all resources. */
    public abstract void dispose();

}
