package com.fos.game.engine.renderer.shaders.base;

public enum ShadingMethod {

    PlainColor,
    PlainDiffuseMap,
    PlainDiffuseBloomMaps,
    PlainSkyBox,
    UniformColor,
    UniformColorBloom,
    @Deprecated AnimatedDiffuseMap,
    DiffuseMap,
    DiffuseNormalMaps,
    DiffuseNormalBloomMaps,
    UniformColorGlow,
    DiffuseBloomMaps,
    DiffuseNormalParallaxMaps,
    DiffuseNormalSpecularRoughnessMaps,
    DiffuseNormalSpecularRoughnessParallaxMaps,
    VolumetricLightSourceColor,
    AlbedoNormalMetallicRoughnessAOParallaxMaps, // <- see if possible to re-implement for extra detailed models
    PBRAlbedoNormalMetallicRoughnessAOMaps,
    PBRAlbedoNormalMaps,
    PBRAlbedoMap,
    PBRBlendAlbedoMaps,
}
