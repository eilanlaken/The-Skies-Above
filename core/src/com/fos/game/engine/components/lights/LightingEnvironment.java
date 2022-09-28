package com.fos.game.engine.components.lights;

import com.badlogic.gdx.utils.Array;

public class LightingEnvironment extends com.badlogic.gdx.graphics.g3d.Environment {

    public static final int MAX_POINT_LIGHTS = 10;
    public static final int MAX_SPOT_LIGHTS = 2;
    public static final int MAX_DIRECTIONAL_LIGHTS = 1;

    public Array<ComponentPointLight> pointLights = new Array<>(MAX_POINT_LIGHTS);
    public Array<ComponentDirectionalLight> directionalLights = new Array<>(MAX_DIRECTIONAL_LIGHTS);
    public Array<ComponentSpotLight> spotLights = new Array<>(MAX_SPOT_LIGHTS);

    public void addLight(final Light light) {
        if (light instanceof ComponentPointLight) {
            if (pointLights.size >= MAX_POINT_LIGHTS) return;
            else pointLights.add((ComponentPointLight) light);
        }
        if (light instanceof ComponentDirectionalLight) {
            if (directionalLights.size >= MAX_DIRECTIONAL_LIGHTS) return;
            directionalLights.add((ComponentDirectionalLight) light);
        }
        if (light instanceof ComponentSpotLight) {
            if (spotLights.size >= MAX_SPOT_LIGHTS) return;
            else spotLights.add((ComponentSpotLight) light);
        }
    }

    public void removeLight(final Light light) {
        if (light instanceof ComponentPointLight) {
            pointLights.removeValue((ComponentPointLight) light, true);
            return;
        }
        if (light instanceof ComponentDirectionalLight) {
            directionalLights.removeValue((ComponentDirectionalLight) light, true);
            return;
        }
        if (light instanceof ComponentSpotLight) {
            spotLights.removeValue((ComponentSpotLight) light, true);
            return;
        }
    }

    public void clearLights() {
        pointLights.clear();
        directionalLights.clear();
        spotLights.clear();
    }
}
