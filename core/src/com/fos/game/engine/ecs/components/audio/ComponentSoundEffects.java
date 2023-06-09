package com.fos.game.engine.ecs.components.audio;

import com.badlogic.gdx.audio.Sound;
import com.fos.game.engine.core.files.assets.GameAssetManager;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;

import java.util.HashMap;

public class ComponentSoundEffects implements Component {

    public SoundEffectData[] soundEffectsData;
    public HashMap<String, SoundEffect> soundEffects;

    public ComponentSoundEffects(final GameAssetManager assetManager, final SoundEffectData ...soundEffectsData) {
        this.soundEffectsData = soundEffectsData;
        soundEffects = new HashMap<>();
        for (final SoundEffectData data : soundEffectsData) {
            SoundEffect soundEffect = new SoundEffect(assetManager.get(data.filepath, Sound.class), data.pitch, data.volume, data.pan, data.priority, data.loop);
            soundEffects.put(data.name, soundEffect);
        }
    }

    public void play(final String ...names) {
        for (final String name : names) {
            final SoundEffect soundEffect = soundEffects.get(name);
            soundEffect.sound.play();
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
