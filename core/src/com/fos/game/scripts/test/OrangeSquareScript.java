package com.fos.game.scripts.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.scripts.Script;
import com.fos.game.engine.ecs.components.transform.ComponentTransform2D;
import com.fos.game.engine.ecs.entities.Entity;

public class OrangeSquareScript extends Script {

    private transient ComponentTransform2D transform2D;
    private transient Vector2 position = new Vector2();

    public OrangeSquareScript(final Entity entity) {
        super(entity);
    }

    @Override
    public void start() {
        transform2D = (ComponentTransform2D) this.entity.components[ComponentType.TRANSFORM_2D.ordinal()];
    }

    @Override
    public void update(float delta) {
        float angle = transform2D.getRotation();
        float x = transform2D.getPosition().x;
        float y = transform2D.getPosition().y;
        float deltaX = 0;
        float deltaY = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) deltaX -= 0.5f;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) deltaX += 0.5f;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) deltaY += 0.5f;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) deltaY -= 0.5f;
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            transform2D.scaleX += 0.01f;
            transform2D.scaleY += 0.01f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            transform2D.scaleX -= 0.01f;
            transform2D.scaleY -= 0.01f;
        }
        this.position.set(x + deltaX, y + deltaY);
        if (Gdx.input.isKeyPressed(Input.Keys.R)) transform2D.transform.setRotation(angle + 0.1f);
        transform2D.transform.setPosition(this.position);
    }

}
