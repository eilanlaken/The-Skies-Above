package com.fos.game.engine.context;

import com.badlogic.gdx.Screen;
import com.fos.game.engine.ecs.entities.EntityManager;

public abstract class Scene extends EntityManager implements Screen {

    protected final GameContext context;

    public Scene(final GameContext context) {
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

    protected void update(float delta) {
        super.update();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

}
