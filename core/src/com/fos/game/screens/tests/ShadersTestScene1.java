package com.fos.game.screens.tests;

import com.fos.game.engine.context.GameContext;
import com.fos.game.engine.context.Scene_old;
import com.fos.game.engine.core.graphics.shaders.base.ExampleShaderProgram;

public class ShadersTestScene1 extends Scene_old {

    ExampleShaderProgram exampleShaderProgram;

    public ShadersTestScene1(final GameContext context) {
        super(context);
        exampleShaderProgram = new ExampleShaderProgram();
        System.out.println(exampleShaderProgram.uniformLocations);
        System.out.println(exampleShaderProgram.currentUniforms);

    }

    @Override
    protected void start() {

    }

    @Override
    protected void update(float delta) {

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
}
