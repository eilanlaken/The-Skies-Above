package com.fos.game.engine.ecs.components.z_animations3d;

public class UtilsRig {

    // debugging
    public static void printBoneHierarchy(final Rig rig) {
        printBoneHierarchy(rig.root, 0);
    }

    private static void printBoneHierarchy(final Bone bone, int tabs) {
        if (bone == null) return;
        StringBuilder tabsString = new StringBuilder();
        for (int i = 0; i < tabs; i++) {
            tabsString.append('\t');
        }
        System.out.println(tabsString.toString() + bone);
        for (Bone childBone : bone.children) {
            printBoneHierarchy(childBone, tabs + 1);
        }
    }

}
