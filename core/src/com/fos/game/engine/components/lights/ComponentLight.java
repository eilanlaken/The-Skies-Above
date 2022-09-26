package com.fos.game.engine.components.lights;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.fos.game.engine.components.base.Component;
import com.fos.game.engine.components.base.ComponentType;

public abstract class ComponentLight implements Component {

    public Vector3 localPosition = new Vector3();
    public Vector3 worldPosition = new Vector3();
    public Color color = new Color(1,1,1,1);
    public float intensity = 1;

    @Override
    public final ComponentType getComponentType() {
        return ComponentType.LIGHT;
    }
}
