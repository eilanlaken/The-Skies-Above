package com.fos.game.engine.components.sounds;

import com.badlogic.gdx.audio.Sound;
import com.fos.game.engine.components.sounds.ComponentSounds;
import com.fos.game.engine.files.GameAssetManager;

public class FactorySound {

    public static ComponentSounds create(final GameAssetManager assetManager, final String name) {
        Sound sound = assetManager.get(name, Sound.class);
        return new ComponentSounds(new Sound[] {sound});
    }

    public static ComponentSounds create(final GameAssetManager assetManager, final String ...names) {
        Sound[] sounds = new Sound[names.length];
        for (int i = 0; i < names.length; i++) {
            sounds[i] = assetManager.get(names[i], Sound.class);
        }
        return new ComponentSounds(sounds);
    }

}
