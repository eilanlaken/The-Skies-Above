package com.fos.game.engine.core.files.serialization;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.fos.game.engine.ecs.components.animations2d.Animations2DData_old;
import com.google.gson.*;

import java.lang.reflect.Type;

public class TypeAdapterAnimationData implements JsonSerializer<Animations2DData_old> {

    @Override
    public JsonElement serialize(Animations2DData_old src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("filepath", new JsonPrimitive(src.filepath));
        result.add("animationName", new JsonPrimitive(src.animationName));
        result.add("frameDuration", new JsonPrimitive(src.frameDuration));
        result.add(Animation.PlayMode.class.getSimpleName(), new JsonPrimitive(src.playMode.name()));
        return result;
    }
}
