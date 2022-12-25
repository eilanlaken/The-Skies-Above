package com.fos.game.scenes.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.fos.game.engine.core.context.ApplicationContext;
import com.fos.game.engine.core.context.Scene;
import com.fos.game.engine.core.graphics.ui.text.base.TypingLabel;
import com.fos.game.ui.GameWindow;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.layout.GridGroup;
import com.kotcrab.vis.ui.widget.*;

public class TestSceneUI3 extends Scene {

    Stage stage = new Stage();
    VisWindow window;
    GridGroup group;
    VisImage image;

    VisTable visTable;
    Tooltip tooltip;
    GameWindow inventory;

    public TestSceneUI3(final ApplicationContext context) {
        super(context);
    }

    @Override
    public void show() {
        VisUI.load();
        Gdx.input.setInputProcessor(stage);

        Texture texture = new Texture(Gdx.files.internal("ui/eye.jpg"));
        image = new VisImage(texture);
        VisImage image2 = new VisImage(texture);
        VisImage image3 = new VisImage(texture);
        VisImage image4 = new VisImage(texture);

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

        String text = "[GREEN]Hello,{WAIT} world!"
                + "[ORANGE]{SLOWER} Did you know orange is my favorite color?";
        TypingLabel label = new TypingLabel(text, VisUI.getSkin());

        //VisList<String> list = new VisList<>();
        List<String> list = new List<>(VisUI.getSkin());
        list.setItems("hi", "bye", "mye","asd","sfdf", "hhh");
        list.pack();
        list.setX(600);
        list.setY(600);

        window = new VisWindow("window title");
        window.setX(300);
        window.setY(300);
        window.setWidth(300);
        window.setHeight(300);
        window.setResizeBorder(22);
        window.addCloseButton();
        window.debugAll();
        VisWindow.FADE_TIME = 0.01f;
        window.setResizable(true);
        window.add(textArea);
        window.row();
        window.add(button);
        window.row();
        window.add(label);
        stage.addActor(window);

        Stack stack = new Stack();
        stack.setSize(64,64);
        stack.add(image);
        VisLabel lbl = new VisLabel("eye jfdaf lkjea p fdapofida f");
        lbl.setColor(Color.BLUE);
        stack.add(lbl);
        stack.setPosition(600,600);
        //stage.addActor(stack);

        tooltip = new Tooltip();
        tooltip.setFadeTime(0.05f);
        tooltip.setAppearDelayTime(0.05f);
        tooltip.setTarget(stack);
        tooltip.setText("This is a window tool tip");
        tooltip.pack();

        inventory = new GameWindow("inventory", 400,500, 600, 600, false);
        inventory.align(Align.topLeft);
        VisTextButton button2 = new VisTextButton("click");
        button2.setFocusBorderEnabled(false);
        button2.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("hiii");
            }
        } );
        VisTextButton button3 = new VisTextButton("click");
        button3.setFocusBorderEnabled(false);
        button3.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("hiii");
            }
        } );
        inventory.add(image).center().pad(3);
        inventory.add(button2).center();
        inventory.add(button3).center();

        inventory.debugAll();
        stage.addActor(inventory);
    }

    @Override
    public void update(float delta) {
        handleInput();
        ScreenUtils.clear(0f, 0f, 0f, 1);
        // make window un draggable
        // window.setX(300);
        // window.setY(300);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        tooltip.setX(Gdx.input.getX());
        tooltip.setY(Gdx.graphics.getHeight() - Gdx.input.getY());
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            group.removeActor(image);
        }
    }

    @Override
    public void resize(int width, int height) {
        //stage.getViewport().update(1920, 1080, true); // <- preserves aspect ration of the images.
        stage.getViewport().update(width, height, true);
        System.out.println("width: " + inventory.getWidth());
        System.out.println("height: " + inventory.getHeight());

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
