package com.fos.game.engine.ecs.systems.physics3d;

import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.entities.EntityContainer;

public class Physics3DUtils {

    private static final short PHYSICS_3D_BIT_MASK = ComponentType.PHYSICS_BODY_3D.bitMask;

    protected static void fillPhysics3DEntitiesArray(final EntityContainer container, final Array<Entity> result) {
        result.clear();
        for (final Entity entity : container.entities) {
            if ((entity.componentsBitMask & PHYSICS_3D_BIT_MASK) > 0) {
                result.add(entity);
            }
        }
    }

}
