package com.fos.game.engine.ecs.systems.physics3d;

import com.fos.game.engine.ecs.components.base.ComponentType;

public class Physics3DUtils {

    protected static final int PHYSICS_3D_BIT_MASK = ComponentType.PHYSICS_3D_BODY.bitMask;

    /*
    protected static void fillPhysics3DEntitiesArray(final EntityContainer container, final Array<Entity> result) {
        result.clear();
        for (final Entity entity : container.entities) {
            if ((entity.componentsBitMask & PHYSICS_3D_BIT_MASK) > 0) {
                result.add(entity);
            }
        }
    }
     */

}
