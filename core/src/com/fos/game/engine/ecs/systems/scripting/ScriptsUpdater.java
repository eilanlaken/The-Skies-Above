package com.fos.game.engine.ecs.systems.scripting;

import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.systems.base.EntitiesProcessor;

public class ScriptsUpdater implements EntitiesProcessor {

    @Override
    public void process(Array<Entity> entities) {

    }

    @Override
    public boolean shouldProcess(Entity entity) {
        return (entity.componentsBitMask & ScriptsUpdaterUtils.SCRIPTED_ENTITY_BIT_MASK) > 0;
    }
}
