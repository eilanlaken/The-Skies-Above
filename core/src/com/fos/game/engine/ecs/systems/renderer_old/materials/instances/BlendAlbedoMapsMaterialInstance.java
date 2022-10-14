package com.fos.game.engine.ecs.systems.renderer_old.materials.instances;

import com.badlogic.gdx.graphics.Texture;
import com.fos.game.engine.ecs.systems.renderer_old.materials.base.BasicMaterialMap;
import com.fos.game.engine.ecs.systems.renderer_old.materials.base.Material;
import com.fos.game.engine.ecs.systems.renderer_old.shaders.base.ShadingMethod;

public class BlendAlbedoMapsMaterialInstance extends Material {

    public final Texture blendTexture;
    public final BasicMaterialMap[] basicMaterialMaps; // supports up to 4 blended material maps.

    public BlendAlbedoMapsMaterialInstance(final Texture blendTexture, final BasicMaterialMap... basicMaterialMaps) {
        this.blendTexture = blendTexture;
        this.basicMaterialMaps = basicMaterialMaps;
    }

    @Override
    public ShadingMethod getShadingMethod() {
        return ShadingMethod.PBRBlendAlbedoMaps;
    }
}
