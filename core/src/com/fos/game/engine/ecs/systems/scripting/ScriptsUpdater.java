package com.fos.game.engine.ecs.systems.scripting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.scripts.ComponentScripts;
import com.fos.game.engine.ecs.components.scripts.Script;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.systems.base.EntitiesProcessor;

public class ScriptsUpdater implements EntitiesProcessor {

    @Override
    public void process(final Array<Entity> entities) {
        final float delta = Gdx.graphics.getDeltaTime();
        for (Entity entity : entities) {
            ComponentScripts scripts = (ComponentScripts) entity.components[ComponentType.SCRIPTS.ordinal()];
            if (!scripts.active) continue;
            for (Script script : scripts) {
                script.update(delta);
            }
        }
    }

    public void startScripts(final Entity entity) {
        ComponentScripts scripts = (ComponentScripts) entity.components[ComponentType.SCRIPTS.ordinal()];
        if (scripts != null) {
            for (Script script : scripts) script.start();
        }
    }

    @Override
    public boolean shouldProcess(Entity entity) {
        return (entity.componentsBitMask & ScriptsUpdaterUtils.SCRIPTED_ENTITY_BIT_MASK) > 0;
    }
}
