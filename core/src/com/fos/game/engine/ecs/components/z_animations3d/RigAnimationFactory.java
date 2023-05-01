package com.fos.game.engine.ecs.components.z_animations3d;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.model.NodeAnimation;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

public class RigAnimationFactory {

    protected static HashMap<String, RigAnimation> create(final Model model) {
        final HashMap<String, RigAnimation> namedAnimations = new HashMap<>();
        for (Animation gdxAnimation : model.animations) {
            final String name = gdxAnimation.id;
            final RigAnimation rigAnimation = null;//getRigAnimationFromGdxAnimation(gdxAnimation, namedTPoseBonesData);
            namedAnimations.put(name, new RigAnimation(null));
        }
        return namedAnimations;
    }

    private static RigAnimation createRigAnimationFromGdxAnimation(final Animation gdxAnimation) {
        Array<RigAnimation.RigKeyFrame> keyFrames = new Array<>();
        float[] keyTimes = getKeyTimes(gdxAnimation);
        return new RigAnimation(null);
    }

    private static float[] getKeyTimes(final Animation gdxAnimation) {
        final Array<NodeAnimation> nodeAnimations = gdxAnimation.nodeAnimations;
        return null;
    }

    private static Vector3 getTranslation(final NodeAnimation nodeAnimation, final float keyTime) {
        final boolean noTranslation = nodeAnimation.translation == null || nodeAnimation.translation.size == 0;
        if (noTranslation) return new Vector3(0,0,0);
        for (int i = 0; i < nodeAnimation.translation.size; i++) {
            if (keyTime == nodeAnimation.translation.get(i).keytime) return nodeAnimation.translation.get(i).value;
        }
        return new Vector3(0,0,0);
    }

    private static Quaternion getRotation(final NodeAnimation nodeAnimation, final float keyTime) {
        final boolean noRotation = nodeAnimation.rotation == null || nodeAnimation.rotation.size == 0;
        if (noRotation) return new Quaternion();
        for (int i = 0; i < nodeAnimation.rotation.size; i++) {
            if (keyTime == nodeAnimation.rotation.get(i).keytime) return nodeAnimation.rotation.get(i).value;
        }
        return new Quaternion();
    }


    private static Vector3 getScaling(final NodeAnimation nodeAnimation, final float keyTime) {
        final boolean noScaling = nodeAnimation.scaling == null || nodeAnimation.scaling.size == 0;
        if (noScaling) return new Vector3(1,1,1);
        for (int i = 0; i < nodeAnimation.scaling.size; i++) {
            if (keyTime == nodeAnimation.scaling.get(i).keytime) return nodeAnimation.scaling.get(i).value;
        }
        return new Vector3(1,1,1);
    }


    private static WeightedPose extractWeightedPose(final Animation source, final float keyTime, final Map<String, BoneTPoseData> namedTPoseBonesData) {
        Array<NodeAnimation> nodeAnimations = source.nodeAnimations;
        HashMap<String, WeightedBoneTransform> namedWeightTransformsMap = new HashMap<>();
        for (NodeAnimation nodeAnimation : nodeAnimations) {
            final String name = nodeAnimation.node.id;
            final Vector3 translation = getTranslation(nodeAnimation, keyTime);
            final Quaternion rotation = getRotation(nodeAnimation, keyTime);
            final Vector3 scaling = getScaling(nodeAnimation, keyTime);
            final float weight = 1; // TODO: just a placeholder value
            namedWeightTransformsMap.put(name, new WeightedBoneTransform(weight, new BoneTransform(translation, rotation, scaling)));
        }
        for (Map.Entry<String, BoneTPoseData> entry : namedTPoseBonesData.entrySet()) {
            final String boneName = entry.getKey();
            if (namedWeightTransformsMap.get(boneName) == null) {
                BoneTransform counterpart = new BoneTransform(new Vector3(), new Quaternion(), new Vector3(1,1,1));
                namedWeightTransformsMap.put(boneName, new WeightedBoneTransform(1, counterpart));
            }
        }
        return new WeightedPose(namedWeightTransformsMap);
    }

//    private static float[] getKeyTimes(final Animation gdxAnimation) {
//        Array<NodeAnimation> nodeAnimations = gdxAnimation.nodeAnimations;
//        final boolean hasScaling = nodeAnimations.get(0).scaling != null && nodeAnimations.get(0).scaling.size > 0;
//        final boolean hasRotations = nodeAnimations.get(0).rotation != null && nodeAnimations.get(0).rotation.size > 0;
//        final boolean hasTranslation = nodeAnimations.get(0).translation != null && nodeAnimations.get(0).translation.size > 0;
//        final int size = GameMathUtils.max(
//                nodeAnimations.get(0).rotation != null ? nodeAnimations.get(0).rotation.size : 0,
//                nodeAnimations.get(0).translation != null ? nodeAnimations.get(0).translation.size : 0,
//                nodeAnimations.get(0).scaling != null ? nodeAnimations.get(0).scaling.size : 0);
//        float[] keyTimes = new float[size];
//        for (int i = 0; i < size; i++) {
//            float keyTime = 0;
//            if (hasScaling) keyTime = nodeAnimations.get(0).scaling.get(i).keytime;
//            if (hasRotations) keyTime = nodeAnimations.get(0).rotation.get(i).keytime;
//            if (hasTranslation) keyTime = nodeAnimations.get(0).translation.get(i).keytime;
//            keyTimes[i] = keyTime;
//        }
//        return keyTimes;
//    }

}
