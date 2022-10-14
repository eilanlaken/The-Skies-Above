package com.fos.game.engine.ecs.systems.audio;

import com.fos.game.engine.ecs.components.base.ComponentType;

@Deprecated
public class AudioUtils {

    protected static final int AUDIO_BIT_MASK = ComponentType.AUDIO.bitMask;

    /*
    private static final Comparator<ComponentSoundEffects> soundEffectsPriorityComparator = new Comparator<Entity>() {
        @Override
        public int compare(Entity e1, Entity e2) {
            final float priority1 = ((ComponentSoundEffects) e1.components[ComponentType.AUDIO.ordinal()]).get(0).;
            final float priority2 = ((ComponentSoundEffects) e2.components[ComponentType.AUDIO.ordinal()]).z;
            return priority1 > priority2 ? 1 : priority1 < priority2 ? -1 : 0;
        }
    };
    
     */

}
