package com.fos.game.engine.renderer.materials.instances;

import com.badlogic.gdx.graphics.Texture;
import com.fos.game.engine.renderer.materials.base.BasicMaterialMap;
import com.fos.game.engine.renderer.materials.base.FOSMaterial;
import com.fos.game.engine.renderer.shaders.base.ShadingMethod;

public class BlendAlbedoMapsMaterialInstance extends FOSMaterial {

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
