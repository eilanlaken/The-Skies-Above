package com.fos.game.engine.ecs.components.audio;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.core.files.assets.GameAssetManager;

import java.util.HashMap;

public class ComponentSoundEffects extends HashMap<String, SoundEffect> implements Component {

    public SoundEffectData[] soundEffectsData;
    public final Array<SoundEffect> toPlay = new Array<>();

    public ComponentSoundEffects(final GameAssetManager assetManager, final SoundEffectData ...soundEffectsData) {
        this.soundEffectsData = soundEffectsData;
        for (final SoundEffectData data : soundEffectsData) {
            SoundEffect soundEffect = new SoundEffect(assetManager.get(data.filepath, Sound.class), data.pitch, data.volume, data.pan, data.priority, data.loop);
            put(data.name, soundEffect);
        }
    }

    // TODO: move logic for a dedicated system.
    public void play(final String ...names) {
        toPlay.clear();
        for (final String name : names) {
            final SoundEffect soundEffect = get(name);
            toPlay.add(soundEffect);
        }
    }

    @Override
    public final ComponentType getComponentType() {
        return ComponentType.AUDIO;
    }


    // TODO: delete
    public ComponentSoundEffects(final SoundEffect ...soundEffects) {

    }
}
