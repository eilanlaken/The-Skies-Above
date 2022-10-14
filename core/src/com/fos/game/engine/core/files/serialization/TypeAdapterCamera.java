package com.fos.game.engine.core.files.serialization;

import com.badlogic.gdx.graphics.Camera;
import com.google.gson.*;

import java.lang.reflect.Type;

class TypeAdapterCamera implements JsonSerializer<Camera>, JsonDeserializer<Camera> {
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
