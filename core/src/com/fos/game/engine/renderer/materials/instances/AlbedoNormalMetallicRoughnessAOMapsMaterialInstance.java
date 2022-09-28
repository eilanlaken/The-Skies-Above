package com.fos.game.engine.renderer.materials.instances;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.fos.game.engine.renderer.materials.base.*;
import com.fos.game.engine.renderer.shaders.base.ShadingMethod;

public class AlbedoNormalMetallicRoughnessAOMapsMaterialInstance extends Material implements
        RenderedAlbedoMap, RenderedNormalMap, RenderedPBRMap {

    public final float ambient;

    public final int glTextureObjectHandle;
    public final TextureAtlas spriteSheet;
    public final TextureAtlas.AtlasRegion albedoRegion;
    public final TextureAtlas.AtlasRegion normalMapRegion;
    public final TextureAtlas.AtlasRegion metallicRoughnessAOMapRegion;

    public final int xAlbedo;
    public final int yAlbedo;
    public final int xNormalMap;
    public final int yNormalMap;
    public final int xMetallicRoughnessAOMap;
    public final int yMetallicRoughnessAOMap;
    public final int width;
    public final int height;
    public final float atlasWidthInv;
    public final float atlasHeightInv;

    public AlbedoNormalMetallicRoughnessAOMapsMaterialInstance(final TextureAtlas spriteSheet,
                                                               final float ambient,
                                                               final String albedoRegionName,
                                                               final String normalRegionName,
                                                               final String metallicRoughnessAORegionName) {
        this.glTextureObjectHandle = spriteSheet.getTextures().first().getTextureObjectHandle();
        this.spriteSheet = spriteSheet;

        this.ambient = ambient;
        this.albedoRegion = spriteSheet.findRegion(albedoRegionName);
        this.normalMapRegion = spriteSheet.findRegion(normalRegionName);
        this.metallicRoughnessAOMapRegion = spriteSheet.findRegion(metallicRoughnessAORegionName);
        // uv-remapping
        this.xAlbedo = albedoRegion.getRegionX();
        this.yAlbedo = albedoRegion.getRegionY();
        this.xNormalMap = normalMapRegion.getRegionX();
        this.yNormalMap = normalMapRegion.getRegionY();

        // all contained in this single map
        this.xMetallicRoughnessAOMap = metallicRoughnessAOMapRegion.getRegionX();
        this.yMetallicRoughnessAOMap = metallicRoughnessAOMapRegion.getRegionY();

        this.width = albedoRegion.getRegionWidth();
        this.height = albedoRegion.getRegionHeight();
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
    public int getNormalX(float elapsedTime) {
        return xNormalMap;
    }

    @Override
    public int getNormalY(float elapsedTime) {
        return yNormalMap;
    }

    @Override
    public int getPBRX(float elapsedTime) {
        return xMetallicRoughnessAOMap;
    }

    @Override
    public int getPBRY(float elapsedTime) {
        return yMetallicRoughnessAOMap;
    }

    @Override
    public int getHeight(float elapsedTime) {
        return height;
    }

    @Override
    public int getWidth(float elapsedTime) {
        return width;
    }

    @Override
    public int getGLTextureHandle() {
        return glTextureObjectHandle;
    }

    @Override
    public ShadingMethod getShadingMethod() {
        return ShadingMethod.PBRAlbedoNormalMetallicRoughnessAOMaps;
    }


}
