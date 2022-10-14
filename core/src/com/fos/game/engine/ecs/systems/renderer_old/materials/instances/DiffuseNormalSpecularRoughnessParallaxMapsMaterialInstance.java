package com.fos.game.engine.ecs.systems.renderer_old.materials.instances;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.fos.game.engine.ecs.systems.renderer_old.materials.base.*;
import com.fos.game.engine.ecs.systems.renderer_old.shaders.base.ShadingMethod;

/*
TODO:
ignore this incorrect implementation of PBR.
First implement HDR tone mapping and gamma post processing + bloom;
Only then "copy paste" the shader from here:
https://gist.github.com/galek/53557375251e1a942dfa
 */
public class DiffuseNormalSpecularRoughnessParallaxMapsMaterialInstance extends Material implements RenderedDiffuseMap, RenderedNormalMap, RenderedSpecularMap, RenderedRoughnessMap, RenderedParallaxMap {

    public final float ambient;

    public final int glTextureObjectHandle;
    public final TextureAtlas spriteSheet;
    public final TextureAtlas.AtlasRegion diffuseRegion;
    public final TextureAtlas.AtlasRegion normalMapRegion;
    public final TextureAtlas.AtlasRegion specularMapRegion;
    public final TextureAtlas.AtlasRegion roughnessMapRegion;
    public final TextureAtlas.AtlasRegion parallaxMapRegion;

    public final int xDiffuse;
    public final int yDiffuse;
    public final int xNormalMap;
    public final int yNormalMap;
    public final int xSpecularMap;
    public final int ySpecularMap;
    public final int xRoughnessMap;
    public final int yRoughnessMap;
    public final int xParallaxMap;
    public final int yParallaxMap;
    public final int width;
    public final int height;
    public final float atlasWidthInv;
    public final float atlasHeightInv;

    public DiffuseNormalSpecularRoughnessParallaxMapsMaterialInstance(final TextureAtlas spriteSheet,
                                                                      final float ambient,
                                                                      final String diffuseRegionName,
                                                                      final String normalRegionName,
                                                                      final String specularRegionName,
                                                                      final String roughnessRegionName,
                                                                      final String parallaxRegionName) {
        this.glTextureObjectHandle = spriteSheet.getTextures().first().getTextureObjectHandle();
        this.spriteSheet = spriteSheet;

        this.ambient = ambient;
        this.diffuseRegion = spriteSheet.findRegion(diffuseRegionName);
        this.normalMapRegion = spriteSheet.findRegion(normalRegionName);
        this.specularMapRegion = spriteSheet.findRegion(specularRegionName);
        this.roughnessMapRegion = spriteSheet.findRegion(roughnessRegionName);
        this.parallaxMapRegion = spriteSheet.findRegion(parallaxRegionName);
        // uv-remapping
        this.xDiffuse = diffuseRegion.getRegionX();
        this.yDiffuse = diffuseRegion.getRegionY();
        this.xNormalMap = normalMapRegion.getRegionX();
        this.yNormalMap = normalMapRegion.getRegionY();
        this.xSpecularMap = specularMapRegion.getRegionX();
        this.ySpecularMap = specularMapRegion.getRegionY();
        this.xRoughnessMap = roughnessMapRegion.getRegionX();
        this.yRoughnessMap = roughnessMapRegion.getRegionY();
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
    public int getNormalX(float elapsedTime) {
        return xNormalMap;
    }

    @Override
    public int getNormalY(float elapsedTime) {
        return yNormalMap;
    }

    @Override
    public int getRoughnessX(float elapsedTime) {
        return xRoughnessMap;
    }

    @Override
    public int getRoughnessY(float elapsedTime) {
        return yRoughnessMap;
    }

    @Override
    public int getSpecularX(float elapsedTime) {
        return xSpecularMap;
    }

    @Override
    public int getSpecularY(float elapsedTime) {
        return ySpecularMap;
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
        return ShadingMethod.DiffuseNormalSpecularRoughnessParallaxMaps;
    }


}
