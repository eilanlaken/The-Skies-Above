package com.fos.game.engine.components.lights;

import com.badlogic.gdx.utils.Array;

public class LightingEnvironment extends com.badlogic.gdx.graphics.g3d.Environment {

    public static final int MAX_POINT_LIGHTS = 10;
    public static final int MAX_SPOT_LIGHTS = 2;
    public static final int MAX_DIRECTIONAL_LIGHTS = 1;

    public Array<PointLight> pointLights = new Array<>(MAX_POINT_LIGHTS);
    public Array<DirectionalLight> directionalLights = new Array<>(MAX_DIRECTIONAL_LIGHTS);
    public Array<SpotLight> spotLights = new Array<>(MAX_SPOT_LIGHTS);

    public void addLight(final Object light) {
        if (light instanceof PointLight) {
            if (pointLights.size >= MAX_POINT_LIGHTS) return;
            else pointLights.add((PointLight) light);
        }
        if (light instanceof DirectionalLight) {
            if (directionalLights.size >= MAX_DIRECTIONAL_LIGHTS) return;
            directionalLights.add((DirectionalLight) light);
        }
        if (light instanceof SpotLight) {
            if (spotLights.size >= MAX_SPOT_LIGHTS) return;
            else spotLights.add((SpotLight) light);
        }
    }

    public void removeLight(final Object light) {
        if (light instanceof PointLight) {
            pointLights.removeValue((PointLight) light, true);
            return;
        }
        if (light instanceof DirectionalLight) {
            directionalLights.removeValue((DirectionalLight) light, true);
            return;
        }
        if (light instanceof SpotLight) {
            spotLights.removeValue((SpotLight) light, true);
            return;
        }
    }

    public void clearLights() {
        pointLights.clear();
        directionalLights.clear();
        spotLights.clear();
    }
}
