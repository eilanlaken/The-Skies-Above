package com.fos.game.engine.ecs.components.audio;

import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

public class ComponentSoundEffects extends Array<SoundEffect> implements Component {

    public ComponentSoundEffects(final SoundEffect ...soundEffects) {
        super();
        addAll(soundEffects);
    }

    @Override
    public final ComponentType getComponentType() {
        return ComponentType.AUDIO;
    }
}
