package com.fos.game.engine.core.files.serialization;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.fos.game.engine.ecs.components.animations2d.Animation2DData;
import com.google.gson.*;

import java.lang.reflect.Type;

public class TypeAdapterAnimationData implements JsonSerializer<Animation2DData> {

    @Override
    public JsonElement serialize(Animation2DData src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("filepath", new JsonPrimitive(src.filepath));
        result.add("animationName", new JsonPrimitive(src.animationName));
        result.add("frameDuration", new JsonPrimitive(src.frameDuration));
        result.add(Animation.PlayMode.class.getSimpleName(), new JsonPrimitive(src.playMode.name()));
        return result;
    }
}