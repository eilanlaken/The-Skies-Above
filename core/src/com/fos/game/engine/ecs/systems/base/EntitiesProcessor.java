package com.fos.game.engine.ecs.systems.base;

import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.entities.Entity;

public interface EntitiesProcessor {

    void process(final Array<Entity> entities);
    boolean shouldProcess(final Entity entity);

}
