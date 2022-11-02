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
            ComponentLogic scripts = (ComponentLogic) entity.components[ComponentType.SCRIPTS.ordinal()];
            if (!scripts.active) continue;
            for (Logic logic : scripts) {
                logic.update(delta);
            }
        }
    }

    public void startScripts(final Entity entity) {
        ComponentLogic scripts = (ComponentLogic) entity.components[ComponentType.SCRIPTS.ordinal()];
        if (scripts != null) {
            for (Logic logic : scripts) logic.start();
        }
    }

    @Override
    public boolean shouldProcess(Entity entity) {
        return (entity.componentsBitMask & LogicUpdaterUtils.SCRIPTED_ENTITY_BIT_MASK) > 0;
    }
}