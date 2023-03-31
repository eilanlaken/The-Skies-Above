package com.fos.game.engine.ecs.systems.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.logic.ComponentLogic;
import com.fos.game.engine.ecs.components.logic.Logic;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.systems.base.EntitiesProcessor;

public class LogicUpdater implements EntitiesProcessor {

    @Override
    public void process(final Array<Entity> entities) {
        final float delta = Gdx.graphics.getDeltaTime();
        for (Entity entity : entities) {
            ComponentLogic componentLogic = (ComponentLogic) entity.components[ComponentType.LOGIC.ordinal()];
            if (!componentLogic.active) continue;
            for (Logic logic : componentLogic.logic) {
                logic.update(delta);
            }
        }
    }

    public void addEntities(final Array<Entity> toAdd) {
        for (Entity entity : toAdd) {
            ComponentLogic componentLogic = entity.getLogic();
            if (componentLogic == null) continue;
            for (Logic logic : componentLogic.logic) logic.start();
        }
    }

    @Override
    public boolean shouldProcess(Entity entity) {
        return (entity.componentsBitMask & LogicUpdaterUtils.SCRIPTED_ENTITY_BIT_MASK) > 0;
    }
}
