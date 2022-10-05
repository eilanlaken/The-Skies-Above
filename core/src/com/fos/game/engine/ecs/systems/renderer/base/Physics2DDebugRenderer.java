package com.fos.game.engine.ecs.systems.renderer.base;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class Physics2DDebugRenderer extends Box2DDebugRenderer {

    public Physics2DDebugRenderer() {
        super(true, true, true, true, true, true);
    }

    public void begin() {
        super.renderer.begin(ShapeRenderer.ShapeType.Line);
    }

    public void setProjectionMatrix(final Matrix4 matrix) {
        renderer.setProjectionMatrix(matrix);
    }

    @Override
    public void renderBody(final Body body) {
        super.renderBody(body);
    }

    public void end() {
        super.renderer.end();
    }

}
