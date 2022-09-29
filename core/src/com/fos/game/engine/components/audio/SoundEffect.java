package com.fos.game.engine.components.audio;

import com.badlogic.gdx.audio.Sound;

public class SoundEffect {

    public final String filepath;
    public Sound sound;

    public SoundEffect(final Sound sound, final String filepath) {
        this.sound = sound;
        this.filepath = filepath;
    }

    /*
    @Deprecated
    protected SoundEffect(final GameAssetManager assetManager, final String path) {
        this.filepath = path;
        this.sound = assetManager.get(path, Sound.class);
    }

     */

}
