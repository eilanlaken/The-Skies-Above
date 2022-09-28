package com.fos.game.engine.components.audio;

import com.badlogic.gdx.audio.Sound;
import com.fos.game.engine.files.assets.GameAssetManager;

public class SoundEffect {

    public final String path;
    public Sound sound;

    protected SoundEffect(final GameAssetManager assetManager, final String path) {
        this.path = path;
        this.sound = assetManager.get(path, Sound.class);
    }

}
