package com.fos.game.engine.ecs.systems.renderer.materials.instances;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.fos.game.engine.ecs.systems.renderer.materials.base.Material;
import com.fos.game.engine.ecs.systems.renderer.materials.base.RenderedBloomMap;
import com.fos.game.engine.ecs.systems.renderer.materials.base.RenderedDiffuseMap;
import com.fos.game.engine.ecs.systems.renderer.shaders.base.ShadingMethod;

public class PlainDiffuseBloomMapsMaterialInstance extends Material implements RenderedDiffuseMap, RenderedBloomMap {

    public final int glTextureObjectHandle;
    public final TextureAtlas spriteSheet;
    public final TextureAtlas.AtlasRegion diffuse;
    public final TextureAtlas.AtlasRegion bloom;

    public final int xDiffuse;
    public final int yDiffuse;
    public final int xBloom;
    public final int yBloom;
    public final int width;
    public final int height;
    public final float atlasWidthInv;
    public final float atlasHeightInv;

    public float intensity;

    public PlainDiffuseBloomMapsMaterialInstance(final TextureAtlas spriteSheet, final String diffuseRegionName, final String bloomRegionName, float intensity) {
        this.glTextureObjectHandle = spriteSheet.getTextures().first().getTextureObjectHandle();
        this.spriteSheet = spriteSheet;
        this.diffuse = spriteSheet.findRegion(diffuseRegionName);
        this.bloom = spriteSheet.findRegion(bloomRegionName);
        this.xDiffuse = this.diffuse.getRegionX();
        this.yDiffuse = this.diffuse.getRegionY();
        this.xBloom = this.bloom.getRegionX();
        this.yBloom = this.bloom.getRegionY();
        this.width = diffuse.getRegionWidth();
        this.height = diffuse.getRegionHeight();
        this.atlasWidthInv = 1f / spriteSheet.getTextures().first().getWidth();
        this.atlasHeightInv = 1f / spriteSheet.getTextures().first().getHeight();
        this.intensity = intensity;
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
    public int getBloomX(float elapsedTime) {
        return xBloom;
    }

    @Override
    public int getBloomY(float elapsedTime) {
        return yBloom;
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
        return ShadingMethod.PlainDiffuseBloomMaps;
    }

}
