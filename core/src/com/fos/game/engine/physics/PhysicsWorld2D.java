package com.fos.game.engine.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

public class PhysicsWorld2D implements Disposable {

    public World world;

    public PhysicsWorld2D() {
        this(new Vector2()); // <- no gravity
    }

    public PhysicsWorld2D(final Vector2 gravity) {
        this.world = new World(new Vector2(gravity.x,gravity.y), true);
    }

    public void stepSimulation(float delta) {
        world.step(delta, 6, 2);
    }

    @Override
    public void dispose() {
        world.dispose();
    }
}
