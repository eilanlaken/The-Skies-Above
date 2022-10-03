package com.fos.game.engine.ecs.systems.renderer.materials.instances;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.fos.game.engine.ecs.systems.renderer.materials.base.*;
import com.fos.game.engine.ecs.systems.renderer.shaders.base.ShadingMethod;

public class AlbedoNormalMapsMaterialInstance extends Material implements RenderedAlbedoMap, RenderedNormalMap {

    public final float roughness;
    public final float metallic;
    public final float ambient;

    public final int glTextureObjectHandle;
    public final TextureAtlas spriteSheet;
    public final TextureAtlas.AtlasRegion albedoMapRegion;
    public final TextureAtlas.AtlasRegion normalMapRegion;

    public final int xAlbedo;
    public final int yAlbedo;
    public final int xNormalMap;
    public final int yNormalMap;
    public final int width;
    public final int height;
    public final float atlasWidthInv;
    public final float atlasHeightInv;


    public AlbedoNormalMapsMaterialInstance(final TextureAtlas spriteSheet,
                                            float roughness, float metallic, float ambient,
                                            final String albedoRegionName,
                                            final String normalRegionName) {
        this.glTextureObjectHandle = spriteSheet.getTextures().first().getTextureObjectHandle();
        this.roughness = roughness;
        this.metallic = metallic;
        this.ambient = ambient;
        this.spriteSheet = spriteSheet;
        // for example: albedo: metalCrissCross, normal map: metalCrissCrossNM
        this.albedoMapRegion = spriteSheet.findRegion(albedoRegionName);
        this.normalMapRegion = spriteSheet.findRegion(normalRegionName);
        // uv-remapping
        this.xAlbedo = albedoMapRegion.getRegionX();
        this.yAlbedo = albedoMapRegion.getRegionY();
        this.xNormalMap = normalMapRegion.getRegionX();
        this.yNormalMap = normalMapRegion.getRegionY();
        this.width = albedoMapRegion.getRegionWidth();
        this.height = albedoMapRegion.getRegionHeight();
        this.atlasWidthInv = 1f / spriteSheet.getTextures().first().getWidth();
        this.atlasHeightInv = 1f / spriteSheet.getTextures().first().getHeight();
    }

    @Override
    public int getAlbedoX(float elapsedTime) {
        return xAlbedo;
    }

    @Override
    public int getAlbedoY(float elapsedTime) {
        return yAlbedo;
    }

    @Override
    public int getHeight(float elapsedTime) {
        return width;
    }

    @Override
    public int getWidth(float elapsedTime) {
        return height;
    }

    @Override
    public int getNormalX(float elapsedTime) {
        return xNormalMap;
    }

    @Override
    public int getNormalY(float elapsedTime) {
        return yNormalMap;
    }

    @Override
    public int getGLTextureHandle() {
        return glTextureObjectHandle;
    }

    @Override
    public ShadingMethod getShadingMethod() {
        return ShadingMethod.PBRAlbedoNormalMaps;
    }
}
