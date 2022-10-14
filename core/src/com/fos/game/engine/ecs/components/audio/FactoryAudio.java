package com.fos.game.engine.ecs.components.audio;

import com.fos.game.engine.ecs.components.base.Factory;
import com.fos.game.engine.core.files.assets.GameAssetManager;
import com.fos.game.engine.core.files.serialization.JsonConverter;

import java.util.ArrayList;
import java.util.HashMap;

public class FactoryAudio extends Factory {

    public FactoryAudio(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    public ComponentSoundEffects create(final SoundEffectData... soundEffectData) {
        return new ComponentSoundEffects(assetManager, soundEffectData);
    }

    public ComponentSoundEffects create(final String ...filePaths) {
        SoundEffectData[] soundEffectData = new SoundEffectData[filePaths.length];
        for (int i = 0; i < filePaths.length; i++) {
            soundEffectData[i] = new SoundEffectData(filePaths[i], filePaths[i], 1.0f, 1.0f, 1.0f, 0, false);
        }
        return new ComponentSoundEffects(assetManager, soundEffectData);
    }

    @Deprecated
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

}
