package com.fos.game.engine.ecs.components.audio;

import com.badlogic.gdx.audio.Sound;
import com.fos.game.engine.ecs.components.base.Component;
import com.fos.game.engine.ecs.components.base.ComponentType;
import com.fos.game.engine.files.assets.GameAssetManager;

import java.util.HashMap;

public class ComponentSoundEffects extends HashMap<String, SoundEffect> implements Component {

    public SoundEffectData[] soundEffectsData;
    public SoundEffect playingSoundEffect;
    public long playingSoundId = -1;

    public ComponentSoundEffects(final GameAssetManager assetManager, final SoundEffectData ...soundEffectsData) {
        this.soundEffectsData = soundEffectsData;
        for (final SoundEffectData data : soundEffectsData) {
            SoundEffect soundEffect = new SoundEffect(assetManager.get(data.filepath, Sound.class), data.pitch, data.volume, data.pan, data.priority, data.loop);
            put(data.name, soundEffect);
        }
        this.playingSoundEffect = null;
    }

    public void play(final String name) {
        this.playingSoundEffect = get(name);
        this.playingSoundEffect.sound.play();
    }

    @Override
    public final ComponentType getComponentType() {
        return ComponentType.AUDIO;
    }


    // TODO: delete
    public ComponentSoundEffects(final SoundEffect ...soundEffects) {

    }
}
