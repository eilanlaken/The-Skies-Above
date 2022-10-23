package com.fos.game.engine.ecs.systems.renderer;

// TODO: implement.

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.systems.base.EntitiesProcessor;

/***
 * The @Renderer is where the scene is composed from all the cameras_old back buffers.
 * The 2d rendering and 3d rendering tasks are delegated to the Renderer2D and Renderer3D,
 * respectively. Then the final scene is composed.
 */
public class Renderer implements EntitiesProcessor, Disposable {

    @Override
    public void dispose() {

    }

    @Override
    public void process(Array<Entity> entities) {

    }

    @Override
    public boolean shouldProcess(Entity entity) {
        return false;
    }
}
