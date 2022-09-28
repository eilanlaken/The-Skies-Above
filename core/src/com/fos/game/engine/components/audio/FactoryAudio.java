package com.fos.game.engine.components.audio;

import com.badlogic.gdx.audio.Sound;
import com.fos.game.engine.components.base.Factory;
import com.fos.game.engine.files.assets.GameAssetManager;

public class FactoryAudio extends Factory {

    public FactoryAudio(final GameAssetManager assetManager) {
        super(assetManager);
    }

    public ComponentSoundEffects create(final String name) {
        return new ComponentSoundEffects(new SoundEffect[] {new SoundEffect(this.assetManager, name)});
    }

    public ComponentSoundEffects create(final String ...names) {
        SoundEffect[] soundEffects = new SoundEffect[names.length];
        for (int i = 0; i < names.length; i++) {
            soundEffects[i] = new SoundEffect(this.assetManager, names[i]);
        }
        return new ComponentSoundEffects(soundEffects);
    }

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

}
