package com.fos.game.engine.ecs.components.audio;

public class SoundEffectData {

    public final String filepath;
    public final String name;
    public float pitch;
    public float volume;
    public float pan;
    public int priority;
    public boolean loop;

    public SoundEffectData(final String filepath, final String name, float pitch, float volume, float pan, int priority, boolean loop) {
        this.filepath = filepath;
        this.name = name;
        this.pitch = pitch;
        this.volume = volume;
        this.pan = pan;
        this.priority = priority;
        this.loop = loop;
    }

}
