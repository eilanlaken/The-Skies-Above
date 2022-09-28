package com.fos.game.engine.renderer.materials.instances;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.fos.game.engine.renderer.materials.base.*;
import com.fos.game.engine.renderer.shaders.base.ShadingMethod;

public class DiffuseNormalBloomMapsMaterialInstance extends Material implements RenderedDiffuseMap, RenderedNormalMap, RenderedBloomMap {

    public final float shineDamper;
    public final float reflectivity;
    public final float ambient;

    public final int glTextureObjectHandle;
    public final TextureAtlas spriteSheet;
    public final TextureAtlas.AtlasRegion diffuseRegion;
    public final TextureAtlas.AtlasRegion normalMapRegion;
    public final TextureAtlas.AtlasRegion bloomMapRegion;

    public final int xDiffuse;
    public final int yDiffuse;
    public final int xNormal;
    public final int yNormal;
    public final int xBloom;
    public final int yBloom;
    public final int width;
    public final int height;
    public final float atlasWidthInv;
    public final float atlasHeightInv;

    public DiffuseNormalBloomMapsMaterialInstance(final TextureAtlas spriteSheet,
                                             float shineDamper, float reflectivity, float ambient,
                                             final String diffuseRegionName,
                                             final String normalRegionName,
                                             final String bloomRegionName) {
        this.glTextureObjectHandle = spriteSheet.getTextures().first().getTextureObjectHandle();
        this.shineDamper = shineDamper;
        this.reflectivity = reflectivity;
        this.ambient = ambient;
        this.spriteSheet = spriteSheet;
        // for example: diffuse: metalCrissCross, normal map: metalCrissCrossNM
        this.diffuseRegion = spriteSheet.findRegion(diffuseRegionName);
        this.normalMapRegion = spriteSheet.findRegion(normalRegionName);
        this.bloomMapRegion = spriteSheet.findRegion(bloomRegionName);
        // uv-remapping
        this.xDiffuse = diffuseRegion.getRegionX();
        this.yDiffuse = diffuseRegion.getRegionY();
        this.xNormal = normalMapRegion.getRegionX();
        this.yNormal = normalMapRegion.getRegionY();
        this.xBloom = bloomMapRegion.getRegionX();
        this.yBloom = bloomMapRegion.getRegionY();
        this.width = diffuseRegion.getRegionWidth();
        this.height = diffuseRegion.getRegionHeight();
        this.atlasWidthInv = 1f / spriteSheet.getTextures().first().getWidth();
        this.atlasHeightInv = 1f / spriteSheet.getTextures().first().getHeight();
    }

    @Override
    public int getDiffuseX(float elapsedTime) {
        return xDiffuse;
    }

    @Override
    public int getDiffuseY(float elapsedTime) {
        return yDiffuse;
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
        return xNormal;
    }

    @Override
    public int getNormalY(float elapsedTime) {
        return yNormal;
    }

    @Override
    public int getBloomX(float elapsedTime) {
        return xBloom;
    }

    @Override
    public int getBloomY(float elapsedTime) {
        return yBloom;
    }

    @Override
    public int getGLTextureHandle() {
        return glTextureObjectHandle;
    }

    @Override
    public ShadingMethod getShadingMethod() {
        return ShadingMethod.DiffuseNormalBloomMaps;
    }

}
