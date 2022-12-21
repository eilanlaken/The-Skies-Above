package com.fos.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.fos.game.engine.core.context.ApplicationContext;
import com.fos.game.engine.core.context.Scene;
import com.fos.game.engine.core.graphics.ui.text.base.TypingLabel;
import com.fos.game.engine.core.graphics.ui.text.effects.BlinkEffect;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.*;

public class TestSceneUI3 extends Scene {

    Stage stage = new Stage();
    VisWindow window;

    public TestSceneUI3(final ApplicationContext context) {
        super(context);
    }

    @Override
    public void show() {
        VisUI.load();
        Gdx.input.setInputProcessor(stage);
        VisTextArea textArea = new VisTextArea("this is a com.fos.game.engine.core.graphics.ui.text area");
        textArea.setX(10);
        textArea.setY(10);
        textArea.setWidth(200);
        textArea.setHeight(200);

        VisTextButton button = new VisTextButton("click");
        //textButton.setSize(300,300);
        button.setX(600);
        button.setY(600);
        button.setFocusBorderEnabled(false);
        button.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("hiii");
            }
        } );

        //VisList<String> list = new VisList<>();
        List<String> list = new List<>(VisUI.getSkin());
        list.setItems("hi", "bye", "mye");
        list.pack();
        VisScrollPane visScrollPane = new VisScrollPane(list);
        VisTable table = new VisTable();
        list.setX(600);
        list.setY(600);
        //table.add(visScrollPane);
        //table.row();
        //table.add(button);

        BlinkEffect x;

        window = new VisWindow("window title");
        window.setX(300);
        window.setY(300);
        window.setWidth(300);
        window.setHeight(300);
        window.addCloseButton();
        VisWindow.FADE_TIME = 0.01f;
        window.setResizable(true);
        window.add(textArea);
        window.row();
        window.setColor(1,0,0,1);
        //stage.addActor(textArea);
        stage.addActor(window);
        stage.addActor(list);

        String text = "[GREEN]Hello,{WAIT} world!"
                + "[ORANGE]{SLOWER} Did you know orange is my favorite color?";
        TypingLabel label = new TypingLabel(text, VisUI.getSkin());
        label.setX(700);
        label.setY(700);
        stage.addActor(label);
    }

    @Override
    public void update(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 1);

        // make window un draggable
        // window.setX(300);
        // window.setY(300);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(1920, 1080, true);
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
