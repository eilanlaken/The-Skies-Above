package com.fos.game.engine.utils;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public final class GameMathUtils {

    public static final int[] POWERS_OF_TWO = {1,2,4,8,16,32,64,128,256,512,1024,2048,4096};

    public static Matrix4 getMatrix(final Vector3 translation, Quaternion rotation) {
        Matrix4 mtx = new Matrix4().idt();
        mtx.translate(translation);
        mtx.rotate(rotation);
        return mtx;
    }

    public static int max(int ...numbers) {
        int max = numbers[0];
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] > max) max = numbers[i];
        }
        return max;
    }

    public static float max(float ...numbers) {
        float max = numbers[0];
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] > max) max = numbers[i];
        }
        return max;
    }

}
