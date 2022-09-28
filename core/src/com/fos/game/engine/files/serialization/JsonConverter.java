package com.fos.game.engine.files.serialization;

import com.badlogic.gdx.graphics.Camera;
import com.fos.game.engine.components.lights.ComponentLight;
import com.fos.game.engine.components.lights.PointLight;
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
        .registerTypeAdapter(ComponentLight.class, new ComponentLightTypeAdapter());

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

    private class ComponentLightTypeAdapter implements JsonSerializer<ComponentLight>, JsonDeserializer<ComponentLight> {

        @Override
        public JsonElement serialize(ComponentLight src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject result = new JsonObject();
            System.out.println("here");
            result.add("type", new JsonPrimitive(src.getClass().getCanonicalName()));
            result.add("properties", context.serialize(src, src.getClass()));
            return result;
        }

        @Override
        public ComponentLight deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            //System.out.println("json object: " + jsonObject);
            String type = jsonObject.get("type").getAsString();
            JsonElement element = jsonObject.get("properties");
            try {
                return context.deserialize(element, Class.forName(type));
            } catch (ClassNotFoundException e) {
                throw new JsonParseException("Unknown element type: " + type, e);
            }
        }

    }

    private class PointLightTypeAdapter implements JsonSerializer<PointLight>, JsonDeserializer<PointLight> {

        @Override
        public JsonElement serialize(PointLight src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject result = new JsonObject();
            result.add("type", new JsonPrimitive(src.getClass().getCanonicalName()));
            result.add("properties", context.serialize(src, src.getClass()));
            return result;
        }

        @Override
        public PointLight deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            //System.out.println("json object: " + jsonObject);
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
