package com.fos.game.engine.utils;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public final class GameStringUtils {

    public static Vector3 vector3FromString(final String floatArrayString) {
        StringBuilder builder = new StringBuilder(floatArrayString.substring(1, floatArrayString.length()-1));
        String[] split = builder.toString().split(",");
        float x = Float.parseFloat(split[0]);
        float y = Float.parseFloat(split[1]);
        float z = Float.parseFloat(split[2]);
        Vector3 vector3 = new Vector3(x,y,z);
        return vector3;
    }

    public static Quaternion quaternionFromString(final String floatArrayString) {
        StringBuilder builder = new StringBuilder(floatArrayString.substring(1, floatArrayString.length()-1));
        String[] split = builder.toString().split(",");
        float w = Float.parseFloat(split[0]);
        float x = Float.parseFloat(split[1]);
        float y = Float.parseFloat(split[2]);
        float z = Float.parseFloat(split[3]);
        Quaternion quaternion = new Quaternion(x,y,z,w);
        return quaternion;
    }

}
