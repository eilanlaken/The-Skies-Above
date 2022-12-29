package com.fos.game.engine.core.graphics.shaders.base;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;
import java.util.Map;

public class Shader extends ShaderProgram {

    // change back to private and protected.
    public HashMap<String, Integer> uniformLocations = new HashMap<>();
    public HashMap<String, Object> currentUniforms = new HashMap<>();

    public Shader(final String vertex, final String fragment) {
        super(vertex, fragment);
        if (!isCompiled()) throw new IllegalArgumentException("Error compiling shader: " + getLog());
        String[] uniforms = getUniforms();
        // cache uniform locations
        for (final String uniform : uniforms) {
            System.out.println("name: " + uniform);
            int location = fetchUniformLocation(uniform, false);
            uniformLocations.put(uniform, location);
        }
    }

    public final void updateUniform(final String name, Object value) {
        currentUniforms.put(name, value);
    }

    public final void bindUniformsToGPU() {
        for (Map.Entry<String, Object> entry : currentUniforms.entrySet()) {
            final String entryName = entry.getKey();
            final Object entryValue = entry.getValue();
            Integer location = uniformLocations.get(entryName);
            if (location == null) throw new IllegalArgumentException("Error: shader " + this.getClass().getSimpleName() + " " +
                    "does not contain a uniform variable named " + entryName + ". If you have defined " + entryName + " in the shader but haven't used it, " +
                    "the glsl compiler would not register it.");
            if (entryValue instanceof Integer) setUniformi(location, (Integer) entryValue);
            if (entryValue instanceof Float) setUniformf(location, (Float) entryValue);
            if (entryValue instanceof Vector2) setUniformf(location, (Vector2) entryValue);
            if (entryValue instanceof Vector3) setUniformf(location, (Vector3) entryValue);
            if (entryValue instanceof Color) setUniformf(location, (Color) entryValue);
            if (entryValue instanceof Matrix3) setUniformMatrix(location, (Matrix3) entryValue);
            if (entryValue instanceof Matrix4) setUniformMatrix(location, (Matrix4) entryValue);
        }
    }

}
