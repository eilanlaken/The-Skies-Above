package com.fos.game.engine.ecs.entities;

import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.systems.base.EntitiesProcessor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EntityContainerUtils {

    protected static void prepareForProcessing(final Array<Entity> allEntities, HashMap<EntitiesProcessor, Array<Entity>> systemEntitiesMapResult) {
        Set<Map.Entry<EntitiesProcessor, Array<Entity>>> entrySet = systemEntitiesMapResult.entrySet();
        for (final Map.Entry<EntitiesProcessor, Array<Entity>> entry : entrySet) {
            entry.getValue().clear();
        }
        for (final Entity entity : allEntities) {
            for (final Map.Entry<EntitiesProcessor, Array<Entity>> entry : entrySet) {
                final EntitiesProcessor entitiesProcessor = entry.getKey();
                final Array<Entity> entities = entry.getValue();
                if (entitiesProcessor.shouldProcess(entity)) entities.add(entity);
            }
        }
    }

}
