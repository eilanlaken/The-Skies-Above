package com.fos.game.scenes.tests.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.fos.game.engine.core.context.ApplicationContext;
import com.fos.game.engine.core.context.Scene;

import java.util.HashMap;
import java.util.Map;

public class Test3D_2 extends Scene {

    private PerspectiveCamera cam;
    public CameraInputController camController;

    public Environment environment;
    public Model boxModel, shipModel;
    public ModelInstance boxInstance, shipInstance;

    public ModelBatch modelBatch;

    public Test3D_2(final ApplicationContext context) {
        super(context);
        modelBatch = new ModelBatch();
    }

    @Override
    public void show() {
        cam = new PerspectiveCamera(85, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0, 0f, 5f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);

        ModelBuilder modelBuilder = new ModelBuilder();
        boxModel = modelBuilder.createBox(1f, 1f, 1f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        shipModel = context.assetManager.get("models/airplanes/airplane_1.g3dj", Model.class);

        boxInstance = new ModelInstance(boxModel);
        boxInstance.transform.translate(-1,0.5f,0);
        shipInstance = new ModelInstance(shipModel);
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.5f, 0.1f, 0.1f, -1f, -0.8f, -0.2f));
    }

    @Override
    public void update(float delta) {
        camController.update();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) boxInstance.transform.translate(-0.1f,0,0);

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(cam);
        modelBatch.render(boxInstance, environment);
        modelBatch.render(shipInstance, environment);
        modelBatch.end();
    }

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
        boxModel.dispose();
    }

    public static Map<String, Class> getRequiredAssetsNameTypeMap() {
        HashMap<String, Class> assetNameClassMap = new HashMap<>();
        assetNameClassMap.put("models/airplanes/airplane_1.g3dj", Model.class);
        return assetNameClassMap;
    }
}
