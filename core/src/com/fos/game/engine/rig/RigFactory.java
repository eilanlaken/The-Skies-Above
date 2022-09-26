package com.fos.game.engine.rig;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.ArrayMap;

import java.util.HashMap;
import java.util.Map;

public class RigFactory {

    private static int bonesCount = 0;

    public static Rig createRig(final Model model, final String riggedModelNodeId, final String rigHierarchyId) {
        Node modelNode = model.getNode(riggedModelNodeId);
        Node rigNode = model.getNode(rigHierarchyId);
        final Map<String, BoneTPoseData> namedTPoseBonesData = getNamedTPoseBonesData(modelNode);
        return assembleRig(model, rigNode, namedTPoseBonesData);
    }

    private static Rig assembleRig(final Model model, final Node rigNode, final Map<String, BoneTPoseData> namedTPoseBonesData) {
        Node firstBoneNode = rigNode.getChild(0);
        bonesCount = 0;
        Bone root = createBoneHierarchy(firstBoneNode, namedTPoseBonesData);
        root.calculateTPoseGlobalTransformInvForBoneTree(new Matrix4().idt());
        final int rigSize = bonesCount + 1;
        Bone[] bones = new Bone[rigSize];
        fillBonesArray(root, bones);
        final HashMap<String, RigAnimation> namedAnimations = RigAnimationFactoryOld.getNamedAnimations(model, namedTPoseBonesData);
        //final HashMap<String, RigAnimation> namedAnimations = RigAnimationFactory.create(model);
        return new Rig(root, bones, namedAnimations);
    }

    private static void fillBonesArray(final Bone root, final Bone[] bones) {
        addBoneToArray(root, bones);
    }

    private static void addBoneToArray(final Bone bone, final Bone[] bones) {
        bones[bone.index] = bone;
        for (Bone childBone : bone.children) {
            addBoneToArray(childBone, bones);
        }
    }

    private static Bone createBoneHierarchy(final Node node, final Map<String, BoneTPoseData> namedTPoseBonesData) {
        final int index = namedTPoseBonesData.get(node.id).id;
        final String name = node.id;
        final Matrix4 boneTPoseTransform = namedTPoseBonesData.get(node.id).localTransform;
        Bone bone = new Bone(index, name, boneTPoseTransform);
        for (Node chileNode : node.getChildren()) {
            bonesCount++;
            Bone childBone = createBoneHierarchy(chileNode, namedTPoseBonesData);
            bone.attachChild(childBone);
        }
        return bone;
    }

//    private static Map<String, BoneTPoseData> getNamedTPoseBonesData(final Node modelNode) {
//        Map<String, BoneTPoseData> namedTPoseBonesData = new HashMap<>();
//        try {
//            NodePart nodePart = modelNode.parts.first();
//            ArrayMap<Node, Matrix4> invBoneBindTransforms = nodePart.invBoneBindTransforms;
//            for (int i = 0; i < invBoneBindTransforms.keys.length; i++) {
//                String name = invBoneBindTransforms.keys[i].id;
//                System.out.println(invBoneBindTransforms.keys[i]);
//                BoneTPoseData boneTPoseData =
//                        new BoneTPoseData(i,
//                                invBoneBindTransforms.keys[i].localTransform,
//                                invBoneBindTransforms.keys[i].globalTransform);
//                namedTPoseBonesData.put(name, boneTPoseData);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return namedTPoseBonesData;
//    }

    private static Map<String, BoneTPoseData> getNamedTPoseBonesData(final Node modelNode) {
        Map<String, BoneTPoseData> namedTPoseBonesData = new HashMap<>();
        NodePart nodePart = modelNode.parts.first();
        ArrayMap<Node, Matrix4> invBoneBindTransforms = nodePart.invBoneBindTransforms;
        for (int i = 0; i < invBoneBindTransforms.keys.length; i++) {
            if (invBoneBindTransforms.keys[i] == null) continue;
            String name = invBoneBindTransforms.keys[i].id;
            BoneTPoseData boneTPoseData =
                    new BoneTPoseData(i,
                            invBoneBindTransforms.keys[i].localTransform,
                            invBoneBindTransforms.keys[i].globalTransform);
            namedTPoseBonesData.put(name, boneTPoseData);
        }
        return namedTPoseBonesData;
    }

}
