package com.fos.game.engine.renderer.materials.base;

import com.badlogic.gdx.graphics.Texture;

public class BasicMaterialMap {

    public final Texture texture;
    public final float roughness;
    public final float metallic;
    public final float ambient;

    public BasicMaterialMap(final Texture texture, final float roughness, final float metallic, final float ambient) {
        this.texture = texture;
        this.roughness = roughness;
        this.metallic = metallic;
        this.ambient = ambient;
    }

}
