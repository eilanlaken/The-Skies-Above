package com.fos.game.engine.ecs.systems.audio;

import com.fos.game.engine.ecs.components.audio.ComponentSoundEffects;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.ecs.components.transform.ComponentTransform2D;
import com.fos.game.engine.ecs.entities.Entity;

import java.util.Comparator;

public class AudioUtils {

    private static final Comparator<ComponentSoundEffects> soundEffectsPriorityComparator = new Comparator<Entity>() {
        @Override
        public int compare(Entity e1, Entity e2) {
            final float priority1 = ((ComponentTransform2D) e1.components[ComponentType.TRANSFORM_2D.ordinal()]).z;
            final float priority2 = ((ComponentTransform2D) e2.components[ComponentType.TRANSFORM_2D.ordinal()]).z;
            return priority1 > priority2 ? 1 : priority1 < priority2 ? -1 : 0;
        }
    };

}
