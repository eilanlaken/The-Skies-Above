package com.fos.game.engine.components.lights;

// TODO: move to factory based creation pattern
public class FactoryLights {

    public static ComponentLight createPointLight() {
        return new PointLight();
    }

}
