package com.fos.game.engine.ecs.systems.renderer.materials.base;

import com.badlogic.gdx.graphics.g3d.Material;
import com.fos.game.engine.ecs.systems.renderer.materials.instances.*;

public final class MaterialUtils {

    protected static Class getEncodedMaterialClass(final Material gdxMaterial) {
        final String prefix = gdxMaterial.id.substring(0, 2);
        if (prefix.equals("DM")) return DiffuseMapMaterialInstance.class;
        if (prefix.equals("PC")) return PlainColorMaterialInstance.class;
        if (prefix.equals("PD")) return PlainDiffuseMapMaterialInstance.class;
        if (prefix.equals("DB")) return PlainDiffuseBloomMapsMaterialInstance.class;
        if (prefix.equals("RS")) return DiffuseNormalMapsMaterialInstance.class;
        if (prefix.equals("SD")) return AnimatedDiffuseMapMaterialInstance.class;
        if (prefix.equals("SB")) return AnimatedSkyBoxSideMaterialInstance.class;
        if (prefix.equals("PB")) return DiffuseNormalSpecularRoughnessMapsMaterialInstance.class;
        if (prefix.equals("GC")) return GlowingColorMaterialInstance.class;
        if (prefix.equals("AB")) return AnimatedDiffuseBloomMapsMaterialInstance.class;
        if (prefix.equals("PM")) return DiffuseNormalParallaxMapsMaterialInstance.class;
        if (prefix.equals("UC")) return UniformColorMaterialInstance.class;
        if (prefix.equals("UB")) return UniformColorBloomMaterialInstance.class;
        if (prefix.equals("NB")) return DiffuseNormalBloomMapsMaterialInstance.class;
        if (prefix.equals("VL")) return VolumetricLightSourceMaterialInstance.class;
        if (prefix.equals("ED")) return DiffuseNormalSpecularRoughnessParallaxMapsMaterialInstance.class;
        if (prefix.equals("AA")) return AlbedoNormalMetallicRoughnessAOMapsMaterialInstance.class;
        if (prefix.equals("BB")) return AlbedoNormalMapsMaterialInstance.class;
        if (prefix.equals("CC")) return AlbedoMapMaterialInstance.class;
        if (prefix.equals("MF")) return BlendAlbedoMapsMaterialInstance.class;
        return null;
    }

}
