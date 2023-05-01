package com.fos.game.engine.ecs.components.z_animations3d;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.fos.game.engine.utils.GameStringUtils;

import java.util.HashMap;

public class PoseFactory {

    protected static WeightedPose parsePose(final String poseJson) {
        JsonReader jsonReader = new JsonReader();
        JsonValue jsonValue = jsonReader.parse(poseJson);
        JsonValue.JsonIterator it = jsonValue.iterator();

        HashMap<String, WeightedBoneTransform> boneTransformMap = new HashMap<>();
        while(it.hasNext()) {
            JsonValue currentValue = it.next();
            String boneName = currentValue.name;
            Vector3 translation = GameStringUtils.vector3FromString(currentValue.get("translation").asString());
            Quaternion rotation = GameStringUtils.quaternionFromString(currentValue.get("rotation").asString());
            float weight = Float.parseFloat(currentValue.get("weight").asString());
            boneTransformMap.put(boneName, new WeightedBoneTransform(weight, new BoneTransform(translation, rotation)));
        }

        return new WeightedPose(boneTransformMap);
    }

}
