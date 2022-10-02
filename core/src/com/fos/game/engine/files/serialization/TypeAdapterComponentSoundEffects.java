package com.fos.game.engine.files.serialization;

import com.fos.game.engine.components.audio.ComponentSoundEffects;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

class TypeAdapterComponentSoundEffects implements JsonSerializer<ComponentSoundEffects> {

    @Override
    public JsonElement serialize(ComponentSoundEffects src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        String[] paths = new String[src.size];
        for (int i = 0; i < paths.length; i++) {
            paths[i] = src.get(i).filepath;
        }
        result.add("paths", context.serialize(paths, paths.getClass()));
        return result;
    }

}
