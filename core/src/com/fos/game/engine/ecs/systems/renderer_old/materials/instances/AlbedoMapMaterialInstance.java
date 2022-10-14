package com.fos.game.engine.ecs.systems.renderer_old.materials.instances;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.fos.game.engine.ecs.systems.renderer_old.materials.base.Material;
import com.fos.game.engine.ecs.systems.renderer_old.materials.base.RenderedAlbedoMap;
import com.fos.game.engine.ecs.systems.renderer_old.shaders.base.ShadingMethod;

public class AlbedoMapMaterialInstance extends Material implements RenderedAlbedoMap {

    public final float roughness;
    public final float metallic;
    public final float ambient;

    public final int glTextureObjectHandle;
    public final TextureAtlas spriteSheet;
    public final TextureAtlas.AtlasRegion albedoMapRegion;

    public final int xAlbedo;
    public final int yAlbedo;
    public final int width;
    public final int height;
    public final float atlasWidthInv;
    public final float atlasHeightInv;


    public AlbedoMapMaterialInstance(final TextureAtlas spriteSheet,
                                     float roughness, float metallic, float ambient,
                                     final String albedoRegionName) {
        this.glTextureObjectHandle = spriteSheet.getTextures().first().getTextureObjectHandle();
        this.roughness = roughness;
        this.metallic = metallic;
        this.ambient = ambient;
        this.spriteSheet = spriteSheet;
        this.albedoMapRegion = spriteSheet.findRegion(albedoRegionName);
        this.xAlbedo = albedoMapRegion.getRegionX();
        this.yAlbedo = albedoMapRegion.getRegionY();
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
    public int getGLTextureHandle() {
        return glTextureObjectHandle;
    }

    @Override
    public ShadingMethod getShadingMethod() {
        return ShadingMethod.PBRAlbedoMap;
    }
}
