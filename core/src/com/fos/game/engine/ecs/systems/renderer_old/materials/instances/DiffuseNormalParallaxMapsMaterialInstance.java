package com.fos.game.engine.ecs.systems.renderer_old.materials.instances;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.fos.game.engine.ecs.systems.renderer_old.materials.base.*;
import com.fos.game.engine.ecs.systems.renderer_old.shaders.base.ShadingMethod;

public class DiffuseNormalParallaxMapsMaterialInstance extends Material implements RenderedDiffuseMap, RenderedNormalMap, RenderedParallaxMap {

    public final float shineDamper;
    public final float reflectivity;
    public final float ambient;

    public final int glTextureObjectHandle;
    public final TextureAtlas spriteSheet;
    public final TextureAtlas.AtlasRegion diffuseRegion;
    public final TextureAtlas.AtlasRegion normalMapRegion;
    public final TextureAtlas.AtlasRegion parallaxMapRegion;

    public final int xDiffuse;
    public final int yDiffuse;
    public final int xNormalMap;
    public final int yNormalMap;
    public final int xParallaxMap;
    public final int yParallaxMap;
    public final int width;
    public final int height;
    public final float atlasWidthInv;
    public final float atlasHeightInv;

    public DiffuseNormalParallaxMapsMaterialInstance(final TextureAtlas spriteSheet,
                                             float shineDamper, float reflectivity, float ambient,
                                             final String diffuseRegionName,
                                             final String normalRegionName,
                                             final String parallaxRegionName) {
        this.glTextureObjectHandle = spriteSheet.getTextures().first().getTextureObjectHandle();
        this.shineDamper = shineDamper;
        this.reflectivity = reflectivity;
        this.ambient = ambient;
        this.spriteSheet = spriteSheet;
        // for example: diffuse: metalCrissCross, normal map: metalCrissCrossNM
        this.diffuseRegion = spriteSheet.findRegion(diffuseRegionName);
        this.normalMapRegion = spriteSheet.findRegion(normalRegionName);
        this.parallaxMapRegion = spriteSheet.findRegion(parallaxRegionName);
        // uv-remapping
        this.xDiffuse = diffuseRegion.getRegionX();
        this.yDiffuse = diffuseRegion.getRegionY();
        this.xNormalMap = normalMapRegion.getRegionX();
        this.yNormalMap = normalMapRegion.getRegionY();
        this.xParallaxMap = parallaxMapRegion.getRegionX();
        this.yParallaxMap = parallaxMapRegion.getRegionY();
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
        return xNormalMap;
    }

    @Override
    public int getNormalY(float elapsedTime) {
        return yNormalMap;
    }

    @Override
    public int getParallaxX(float elapsedTime) {
        return xParallaxMap;
    }

    @Override
    public int getParallaxY(float elapsedTime) {
        return yParallaxMap;
    }

    @Override
    public int getGLTextureHandle() {
        return glTextureObjectHandle;
    }

    @Override
    public ShadingMethod getShadingMethod() {
        return ShadingMethod.DiffuseNormalParallaxMaps;
    }

}
