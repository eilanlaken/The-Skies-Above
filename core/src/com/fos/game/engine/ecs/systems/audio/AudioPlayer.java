package com.fos.game.engine.ecs.systems.audio;

import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.components.audio.ComponentMusic;
import com.fos.game.engine.ecs.components.audio.ComponentSoundEffects;
import com.fos.game.engine.ecs.entities.Entity;
import com.fos.game.engine.ecs.entities.EntityContainer;
import com.fos.game.engine.ecs.systems.base.EntitiesProcessor;

@Deprecated
public class AudioPlayer implements EntitiesProcessor {

    private final Array<ComponentSoundEffects> soundEffects = new Array<>();
    private final Array<ComponentMusic> musicTracks = new Array<>();

    public AudioPlayer() {

    }

    public void playSounds(final EntityContainer container) {

    }

    @Override
    public void process(final Array<Entity> entities) {

    }

    @Override
    public boolean shouldProcess(Entity entity) {
        return (entity.componentsBitMask & AudioUtils.AUDIO_BIT_MASK) > 0;
    }
}
