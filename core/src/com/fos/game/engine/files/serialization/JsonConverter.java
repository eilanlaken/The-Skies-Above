package com.fos.game.engine.files.serialization;

import com.badlogic.gdx.graphics.Camera;
import com.fos.game.engine.components.audio.ComponentSoundEffects;
import com.google.gson.*;

import java.lang.reflect.Type;

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
        }).registerTypeAdapter(Camera.class, new CameraTypeAdapter())
        .registerTypeAdapter(ComponentSoundEffects.class, new ComponentSoundEffectsTypeAdapter());

        this.gson = gsonBuilder.create();
    }

    private class CameraTypeAdapter implements JsonSerializer<Camera>, JsonDeserializer<Camera> {
        @Override
        public JsonElement serialize(Camera src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject result = new JsonObject();
            result.add("type", new JsonPrimitive(src.getClass().getCanonicalName()));
            result.add("properties", context.serialize(src, src.getClass()));
            return result;
        }

        @Override
        public Camera deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String type = jsonObject.get("type").getAsString();
            JsonElement element = jsonObject.get("properties");
            try {
                return context.deserialize(element, Class.forName(type));
            } catch (ClassNotFoundException e) {
                throw new JsonParseException("Unknown element type: " + type, e);
            }
        }
    }

    private class ComponentSoundEffectsTypeAdapter implements JsonSerializer<ComponentSoundEffects>, JsonDeserializer<ComponentSoundEffects> {
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

        @Override
        public ComponentSoundEffects deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String type = jsonObject.get("type").getAsString();
            JsonElement element = jsonObject.get("properties");
            try {
                return context.deserialize(element, Class.forName(type));
            } catch (ClassNotFoundException e) {
                throw new JsonParseException("Unknown element type: " + type, e);
            }
        }
    }

}
