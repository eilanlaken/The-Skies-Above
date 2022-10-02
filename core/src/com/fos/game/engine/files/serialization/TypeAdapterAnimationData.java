package com.fos.game.engine.files.serialization;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.fos.game.engine.components.animation.AnimationData;
import com.google.gson.*;

import java.lang.reflect.Type;

public class TypeAdapterAnimationData implements JsonSerializer<AnimationData> {

    @Override
    public JsonElement serialize(AnimationData src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("filepath", new JsonPrimitive(src.spriteSheet.filepath));
        result.add("animationName", new JsonPrimitive(src.animationName));
        result.add("frameDuration", new JsonPrimitive(src.frameDuration));
        result.add(Animation.PlayMode.class.getSimpleName(), new JsonPrimitive(src.playMode.name()));
        return result;
    }
}
