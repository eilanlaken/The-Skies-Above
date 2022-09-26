package com.fos.game.engine.utils;

import com.badlogic.gdx.utils.Array;

public class GameLogger {

    private static Array<Object> objectsLogOnce = new Array<>();

    public static void logOnce(final String tag, final Object object) {
        if (objectsLogOnce.contains(object, true)) return;
        else {
            System.out.println(tag + ":");
            System.out.println(object);
            objectsLogOnce.add(object);
        }
    }

    public static void printArray(final Object[] objects) {
        System.out.println("[");
        for (int i = 0; i < objects.length; i++) {
            if (i == objects.length - 1) System.out.print(objects[i]);
            else System.out.print(objects[i] + ", ");
        }
        System.out.print("]");
    }

}
