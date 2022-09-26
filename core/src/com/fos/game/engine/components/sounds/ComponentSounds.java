package com.fos.game.engine.components.sounds;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.components.base.Component;
import com.fos.game.engine.components.base.ComponentType;

public class ComponentSounds extends Array<Sound> implements Component {

    public ComponentSounds(final Sound ...sounds) {
        super();
        addAll(sounds);
    }

    @Override
    public final ComponentType getComponentType() {
        return ComponentType.SOUNDS;
    }
}
