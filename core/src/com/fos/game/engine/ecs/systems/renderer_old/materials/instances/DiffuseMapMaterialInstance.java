package com.fos.game.engine.ecs.systems.renderer_old.materials.instances;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.fos.game.engine.ecs.systems.renderer_old.materials.base.Material;
import com.fos.game.engine.ecs.systems.renderer_old.materials.base.RenderedDiffuseMap;
import com.fos.game.engine.ecs.systems.renderer_old.shaders.base.ShadingMethod;

public class DiffuseMapMaterialInstance extends Material implements RenderedDiffuseMap {

    public final int glTextureObjectHandle;
    public final TextureAtlas spriteSheet;
    public final TextureAtlas.AtlasRegion diffuse;

    public final float shineDamper;
    public final float reflectivity;
    public final float ambient;
    public final int width;
    public final int height;
    public final float atlasWidthInv;
    public final float atlasHeightInv;

    public DiffuseMapMaterialInstance(final TextureAtlas spriteSheet, final String diffuseRegionName,
                                      final float shineDamper, final float reflectivity, final float ambient) {

        this.glTextureObjectHandle = spriteSheet.getTextures().first().getTextureObjectHandle();
        this.spriteSheet = spriteSheet;
        this.diffuse = spriteSheet.findRegion(diffuseRegionName);
        this.width = diffuse.getRegionWidth();
        this.height = diffuse.getRegionHeight();
        this.atlasWidthInv = 1f / spriteSheet.getTextures().first().getWidth();
        this.atlasHeightInv = 1f / spriteSheet.getTextures().first().getHeight();

        this.shineDamper = shineDamper;
        this.reflectivity = reflectivity;
        this.ambient = ambient;
    }

    @Override
    public int getDiffuseX(float elapsedTime) {
        return diffuse.getRegionX();
    }

    @Override
    public int getDiffuseY(float elapsedTime) {
        return diffuse.getRegionY();
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
        return ShadingMethod.DiffuseMap;
    }
}
