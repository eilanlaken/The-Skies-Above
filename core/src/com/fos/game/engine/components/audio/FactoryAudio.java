package com.fos.game.engine.components.audio;

import com.fos.game.engine.components.base.Factory;
import com.fos.game.engine.files.assets.GameAssetManager;
import com.fos.game.engine.files.serialization.JsonConverter;

import java.util.ArrayList;
import java.util.HashMap;

public class FactoryAudio extends Factory {

    public FactoryAudio(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    public ComponentSoundEffects create(final SoundEffect ...soundEffects) {
        return new ComponentSoundEffects(soundEffects);
    }

    public ComponentSoundEffects createFromJson(final String json) {
        HashMap<String, ArrayList> pathsMap = jsonConverter.gson.fromJson(json, HashMap.class);
        ArrayList<String> paths = pathsMap.get("paths");
        SoundEffect[] soundEffects = new SoundEffect[paths.size()];
        for (int i = 0; i < soundEffects.length; i++) {
            String path = paths.get(i);
            soundEffects[i] = this.assetManager.get(path, SoundEffect.class);
        }
        return new ComponentSoundEffects(soundEffects);
    }

    public ComponentSoundEffects create(final String ...names) {
        SoundEffect[] soundEffects = new SoundEffect[names.length];
        for (int i = 0; i < names.length; i++) {
            soundEffects[i] = this.assetManager.get(names[i], SoundEffect.class);
        }
        return new ComponentSoundEffects(soundEffects);
    }

}
