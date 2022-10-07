package com.fos.game.scripts.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.camera.ComponentCamera;
import com.fos.game.engine.ecs.components.scripts.Script;
import com.fos.game.engine.ecs.entities.Entity;

public class SimpleCameraScript extends Script {

    private OrthographicCamera lens;

    public SimpleCameraScript(final Entity entity) {
        super(entity);
    }

    @Override
    public void start() {
        ComponentCamera camera = (ComponentCamera) entity.components[ComponentType.CAMERA.ordinal()];
        lens = (OrthographicCamera) camera.lens;
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) lens.position.x -= 1f;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) lens.position.x += 1f;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) lens.position.y += 1f;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) lens.position.y -= 1f;
        if (Gdx.input.isKeyPressed(Input.Keys.PLUS)) lens.zoom += 0.1f;
        if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) lens.zoom -= 0.1f;
    }

}
