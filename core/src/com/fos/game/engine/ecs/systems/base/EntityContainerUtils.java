package com.fos.game.engine.ecs.systems.base;

import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.entities.Entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EntityContainerUtils {

    protected static void removeEntities(final EntityContainer container) {
        for (final Entity entity : container.toRemove) {
            container.physics2D.destroyPhysics(entity);
            container.entities.removeValue(entity, true);
        }
        container.toRemove.clear();
    }

    protected static void addEntities(final EntityContainer container) {
        for (final Entity entity : container.toAdd) {
            entity.container = container;
            container.entities.add(entity);
            container.physics2D.addPhysics(entity);
            container.logicUpdater.startScripts(entity);
        }
        container.toAdd.clear();
    }

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

    protected static void process(final HashMap<EntitiesProcessor, Array<Entity>> systemEntitiesMap) {
        for (final Map.Entry<EntitiesProcessor, Array<Entity>> systemEntitiesMapEntry : systemEntitiesMap.entrySet()) {
            final EntitiesProcessor entitiesProcessor = systemEntitiesMapEntry.getKey();
            final Array<Entity> entities = systemEntitiesMapEntry.getValue();
            entitiesProcessor.process(entities);
        }
    }

    protected static void addEntity(EntityContainer container, Entity entity) {
        container.toAdd.add(entity);
        if (entity.children == null) return;
        for (Entity child : entity.children) {
            addEntity(container, child);
        }
    }

    protected static void removeEntity(EntityContainer container, Entity entity) {
        if (entity.parent != null) entity.parent.detachChild(entity);
        addToRemoveArray(container, entity);
    }

    private static void addToRemoveArray(EntityContainer container, Entity entity) {
        container.toRemove.add(entity);
        if (entity.children == null) return;
        for (Entity child : entity.children) {
            addToRemoveArray(container, child);
        }
    }
}
