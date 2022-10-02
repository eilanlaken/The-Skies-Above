package com.fos.game.engine.files.serialization;

import com.badlogic.gdx.graphics.Camera;
import com.fos.game.engine.components.animation.AnimationData;
import com.fos.game.engine.components.animation.ComponentAnimations2D;
import com.fos.game.engine.components.audio.ComponentSoundEffects;
import com.fos.game.engine.files.assets.GameAssetManager;
import com.google.gson.*;

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
        .registerTypeAdapter(AnimationData.class, new TypeAdapterAnimationData())
        .registerTypeAdapter(ComponentAnimations2D.class, new TypeAdapterComponentAnimations2D())
        ;
        this.gson = gsonBuilder.create();
    }

}
