package com.fos.game.engine.components.audio;

import com.badlogic.gdx.audio.Sound;
import com.fos.game.engine.components.base.Factory;
import com.fos.game.engine.files.assets.GameAssetManager;
import com.fos.game.engine.files.serialization.JsonConverter;

public class FactoryAudio extends Factory {

    public FactoryAudio(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    public ComponentSoundEffects create(final SoundEffect ...soundEffects) {
        return new ComponentSoundEffects(soundEffects);
    }

    public ComponentSoundEffects create(final String ...names) {
        SoundEffect[] soundEffects = new SoundEffect[names.length];
        for (int i = 0; i < names.length; i++) {
            soundEffects[i] = new SoundEffect(this.assetManager.get(names[i], Sound.class), names[i]);
        }
        return new ComponentSoundEffects(soundEffects);
    }

    /*
    @Deprecated
    public static ComponentSoundEffects create(final GameAssetManager assetManager, final String name) {
        return new ComponentSoundEffects(new SoundEffect[] {new SoundEffect(assetManager, name)});
    }

    @Deprecated
    public static ComponentSoundEffects create(final GameAssetManager assetManager, final String ...names) {
        SoundEffect[] soundEffects = new SoundEffect[names.length];
        for (int i = 0; i < names.length; i++) {
            soundEffects[i] = new SoundEffect(assetManager, names[i]);
        }
        return new ComponentSoundEffects(soundEffects);
    }

     */

}
