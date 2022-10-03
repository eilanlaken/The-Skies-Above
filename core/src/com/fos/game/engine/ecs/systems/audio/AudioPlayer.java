package com.fos.game.engine.ecs.systems.audio;

import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.components.audio.ComponentMusic;
import com.fos.game.engine.ecs.components.audio.ComponentSoundEffects;
import com.fos.game.engine.ecs.entities.EntityContainer;

public class AudioPlayer {

    private final Array<ComponentSoundEffects> soundEffects = new Array<>();
    private final Array<ComponentMusic> musicTracks = new Array<>();

    public AudioPlayer() {

    }

    public void playSounds(final EntityContainer container) {

    }

}
