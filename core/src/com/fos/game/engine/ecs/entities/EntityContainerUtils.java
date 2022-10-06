package com.fos.game.engine.ecs.entities;

import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.physics2d.ComponentJoint2D;
import com.fos.game.engine.ecs.components.physics2d.ComponentRigidBody2D;
import com.fos.game.engine.ecs.components.scripts.ComponentScripts;
import com.fos.game.engine.ecs.components.scripts.Script;
import com.fos.game.engine.ecs.systems.base.EntitiesProcessor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EntityContainerUtils {

    protected static void removeEntities(final EntityContainer container) {
        for (final Entity entity : container.toRemove) {
            // TODO: if has physics...
            container.entities.removeValue(entity, true);
        }
        container.toRemove.clear();
    }

    protected static void addEntities(final EntityContainer container) {
        for (final Entity entity : container.toAdd) {
            container.entities.add(entity);
            // TODO: if has physics...
            ComponentRigidBody2D rigidBody2D = (ComponentRigidBody2D) entity.components[ComponentType.PHYSICS_2D_BODY.ordinal()];
            ComponentJoint2D joint2D = (ComponentJoint2D) entity.components[ComponentType.PHYSICS_2D_JOINT.ordinal()];
            if (rigidBody2D != null) {
                container.physics2D.addBody(rigidBody2D);
            }
            if (joint2D != null) {
                container.physics2D.addJoint(joint2D);
            }
            // TODO: if has scripts attached
            ComponentScripts scripts = (ComponentScripts) entity.components[ComponentType.SCRIPTS.ordinal()];
            if (scripts != null) {
                for (Script script : scripts) script.start();
            }
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
            final Array<Entity> processorEntities = systemEntitiesMapEntry.getValue();
            entitiesProcessor.process(processorEntities);
        }
    }

}
