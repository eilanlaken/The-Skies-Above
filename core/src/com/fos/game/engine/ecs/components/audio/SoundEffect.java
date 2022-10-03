package com.fos.game.engine.ecs.components.audio;

import com.badlogic.gdx.audio.Sound;

public class SoundEffect {

    public Sound sound;
    public float pitch;
    public float volume;
    public float pan;
    public int priority;
    public boolean loop;

    public SoundEffect(final Sound sound, float pitch, float volume, float pan, int priority, boolean loop) {
        this.sound = sound;
        this.pitch = pitch;
        this.volume = volume;
        this.pan = pan;
        this.priority = priority;
        this.loop = loop;
    }

}
