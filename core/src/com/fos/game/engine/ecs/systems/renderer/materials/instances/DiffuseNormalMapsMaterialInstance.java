package com.fos.game.engine.ecs.systems.renderer.materials.instances;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.fos.game.engine.ecs.systems.renderer.materials.base.Material;
import com.fos.game.engine.ecs.systems.renderer.materials.base.RenderedDiffuseMap;
import com.fos.game.engine.ecs.systems.renderer.materials.base.RenderedNormalMap;
import com.fos.game.engine.ecs.systems.renderer.shaders.base.ShadingMethod;

public class DiffuseNormalMapsMaterialInstance extends Material implements RenderedDiffuseMap, RenderedNormalMap {

    public final float shineDamper;
    public final float reflectivity;
    public final float ambient;

    public final int glTextureObjectHandle;
    public final TextureAtlas spriteSheet;
    public final TextureAtlas.AtlasRegion diffuseRegion;
    public final TextureAtlas.AtlasRegion normalMapRegion;

    public final int xDiffuse;
    public final int yDiffuse;
    public final int xNormalMap;
    public final int yNormalMap;
    public final int width;
    public final int height;
    public final float atlasWidthInv;
    public final float atlasHeightInv;


    public DiffuseNormalMapsMaterialInstance(final TextureAtlas spriteSheet,
                                             float shineDamper, float reflectivity, float ambient,
                                             final String diffuseRegionName,
                                             final String normalRegionName) {
        this.glTextureObjectHandle = spriteSheet.getTextures().first().getTextureObjectHandle();
        this.shineDamper = shineDamper;
        this.reflectivity = reflectivity;
        this.ambient = ambient;
        this.spriteSheet = spriteSheet;
        // for example: diffuse: metalCrissCross, normal map: metalCrissCrossNM
        this.diffuseRegion = spriteSheet.findRegion(diffuseRegionName);
        this.normalMapRegion = spriteSheet.findRegion(normalRegionName);
        // uv-remapping
        this.xDiffuse = diffuseRegion.getRegionX();
        this.yDiffuse = diffuseRegion.getRegionY();
        this.xNormalMap = normalMapRegion.getRegionX();
        this.yNormalMap = normalMapRegion.getRegionY();
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
    public int getGLTextureHandle() {
        return glTextureObjectHandle;
    }

    @Override
    public ShadingMethod getShadingMethod() {
        return ShadingMethod.DiffuseNormalMaps;
    }
}
