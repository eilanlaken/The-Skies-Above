package com.fos.game.engine.ecs.systems.renderer.materials.instances;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.fos.game.engine.ecs.systems.renderer.materials.base.Material;
import com.fos.game.engine.ecs.systems.renderer.materials.base.RenderedDiffuseMap;
import com.fos.game.engine.ecs.systems.renderer.shaders.base.ShadingMethod;

public class PlainDiffuseMapMaterialInstance extends Material implements RenderedDiffuseMap {

    public final int glTextureObjectHandle;
    public final TextureAtlas spriteSheet;
    public final TextureAtlas.AtlasRegion diffuse;

    public final int xDiffuse;
    public final int yDiffuse;
    public final int width;
    public final int height;
    public final float atlasWidthInv;
    public final float atlasHeightInv;

    public PlainDiffuseMapMaterialInstance(final TextureAtlas spriteSheet, final String regionName) {
        this.glTextureObjectHandle = spriteSheet.getTextures().first().getTextureObjectHandle();
        this.spriteSheet = spriteSheet;
        this.diffuse = spriteSheet.findRegion(regionName);
        this.xDiffuse = this.diffuse.getRegionX();
        this.yDiffuse = this.diffuse.getRegionY();
        this.width = diffuse.getRegionWidth();
        this.height = diffuse.getRegionHeight();
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
    public int getGLTextureHandle() {
        return glTextureObjectHandle;
    }

    @Override
    public ShadingMethod getShadingMethod() {
        return ShadingMethod.PlainDiffuseMap;
    }

}
