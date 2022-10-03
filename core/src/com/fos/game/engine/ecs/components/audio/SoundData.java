package com.fos.game.engine.ecs.components.audio;

public class SoundData {

    public final String filepath;
    public final String name;
    public float pitch;
    public float volume;
    public float pan;
    public boolean loop;

    public SoundData(final String filepath, final String name, float pitch, float volume, float pan, boolean loop) {
        this.filepath = filepath;
        this.name = name;
        this.pitch = pitch;
        this.volume = volume;
        this.pan = pan;
        this.loop = loop;
    }

}
