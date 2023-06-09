package com.fos.game.engine.core.files.serialization;

import com.badlogic.gdx.graphics.Camera;
import com.fos.game.engine.ecs.components.audio.ComponentSoundEffects;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonConverter<T> {

    public final Gson gson;

    public JsonConverter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                if(f.getName().equals("swigCPtr")){
                    return true;
                }
                return false;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        })
        .registerTypeAdapter(Camera.class, new TypeAdapterCamera())
        .registerTypeAdapter(ComponentSoundEffects.class, new TypeAdapterComponentSoundEffects())
        ;
        this.gson = gsonBuilder.create();
    }

}
