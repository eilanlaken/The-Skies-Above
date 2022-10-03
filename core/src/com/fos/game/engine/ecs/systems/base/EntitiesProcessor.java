package com.fos.game.engine.ecs.systems.base;

import com.fos.game.engine.ecs.entities.EntityContainer;

public interface EntitiesProcessor {

    void process(final EntityContainer container);

}
