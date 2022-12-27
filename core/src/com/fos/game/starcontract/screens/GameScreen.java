package com.fos.game.starcontract.screens;

import com.badlogic.gdx.Screen;
import com.fos.game.engine.core.context.ApplicationContext;
import com.fos.game.starcontract.context.GameContext;

import java.util.Map;

public abstract class GameScreen implements Screen {

    public final GameContext context;

    public GameScreen(final GameContext context) {
        this.context = context;
    }

    @Override
    public final void render(float delta) {
        this.frame(delta);
    }

    public abstract void frame(float delta);

}
