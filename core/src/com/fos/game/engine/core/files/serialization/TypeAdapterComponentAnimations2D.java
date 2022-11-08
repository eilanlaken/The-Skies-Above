package com.fos.game.engine.core.files.serialization;

import com.fos.game.engine.ecs.components.animations2d.Animations2DData_old;
import com.fos.game.engine.ecs.components.animations2d.ComponentAnimations2D;
import com.google.gson.*;

import java.lang.reflect.Type;

public class TypeAdapterComponentAnimations2D implements JsonSerializer<ComponentAnimations2D>, JsonDeserializer<ComponentAnimations2D> {

    protected TypeAdapterComponentAnimations2D() {

    }

    @Override
    public JsonElement serialize(ComponentAnimations2D src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("animationsData", context.serialize(src.data, Animations2DData_old[].class));
        result.add("currentAnimation", new JsonPrimitive(src.currentPlayingAnimation.getKeyFrames()[0].name));
        result.add("elapsedTime", new JsonPrimitive(src.elapsedTime));
        return result;
    }

    @Override
    public ComponentAnimations2D deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement animationsData = jsonObject.get("animationsData");
        JsonElement elapsedTime = jsonObject.get("elapsedTime");
        JsonElement currentAnimation = jsonObject.get("currentAnimation");
        System.out.println("animationsData: " + animationsData);
        System.out.println("elapsedTime: " + elapsedTime);
        System.out.println("currentAnimation: " + currentAnimation);
        Animations2DData_old[] data = context.deserialize(animationsData, Animations2DData_old[].class);
        try {

            return context.deserialize(animationsData, Animations2DData_old.class/*Class.forName(type)*/);
        } catch (Exception e) {
            throw new JsonParseException("Unknown element type: " + animationsData, e);
        }
    }

}
