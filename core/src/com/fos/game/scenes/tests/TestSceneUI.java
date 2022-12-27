package com.fos.game.scenes.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.fos.game.engine.core.context.ApplicationContext;
import com.fos.game.engine.core.context.Scene;
import com.fos.game.engine.core.graphics.g2d.CustomSpriteBatch;

import java.util.HashMap;
import java.util.Map;

public class TestSceneUI extends Scene {

    CustomSpriteBatch batch;
    Stage stage;

    private Skin skin;
    TextButton button;
    TextField textField;
    Slider slider;
    Label label;
    private OrthographicCamera camera;

    public TestSceneUI(final ApplicationContext context) {
        super(context);
    }

    @Override
    public void show() {
        batch = new CustomSpriteBatch();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        skin = context.assetManager.get("ui/uiskin.json", Skin.class);
        stage = new Stage();

        button = new TextButton("Click me", skin, "default");
        button.setWidth(96);
        button.setHeight(30);
        button.setPosition(0, 0.5f * Gdx.graphics.getHeight());
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                button.setText("You clicked the button");
            }
        });

        textField = new TextField("txt", skin);

        label = new Label("label com.fos.game.engine.core.graphics.ui.text", skin);
        label.setWidth(5);


        slider = new Slider(0,100,1,true, skin);
        slider.setWidth(40);
        slider.setHeight(40);
        slider.setPosition(0.5f * Gdx.graphics.getWidth(), 0.75f * Gdx.graphics.getHeight());
        slider.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
            }
        });

        stage.addActor(textField);
        stage.addActor(button);
        stage.addActor(slider);
        stage.addActor(label);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void update(float delta) {

        Gdx.gl.glClearColor(1,1,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();

        // render scene
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        batch.end();

        // render ui on top
        stage.draw();

        System.out.println(0.5f * Gdx.graphics.getHeight());
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(1920, 1080, false);
        button.setPosition(0, 0.5f * Gdx.graphics.getHeight());
        slider.setPosition(0.5f * Gdx.graphics.getWidth(), 0.75f * Gdx.graphics.getHeight());
        label.setPosition(0.5f * Gdx.graphics.getWidth(), 0.5f * Gdx.graphics.getHeight());
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
        batch.dispose();
    }

    public static Map<String, Class> getRequiredAssetsNameTypeMap() {
        HashMap<String, Class> assetNameClassMap = new HashMap<>();
        assetNameClassMap.put("ui/uiskin.json", Skin.class);
        return assetNameClassMap;
    }
}
