package com.fos.game.engine.ecs.systems.renderer_old.materials.instances;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.fos.game.engine.ecs.systems.renderer_old.materials.base.Material;
import com.fos.game.engine.ecs.systems.renderer_old.materials.base.RenderedBloomMap;
import com.fos.game.engine.ecs.systems.renderer_old.materials.base.RenderedDiffuseMap;
import com.fos.game.engine.ecs.systems.renderer_old.shaders.base.ShadingMethod;

public class AnimatedDiffuseBloomMapsMaterialInstance extends Material implements RenderedDiffuseMap, RenderedBloomMap {

    public final int glTextureObjectHandle;
    public final TextureAtlas spriteSheet;

    public final Animation<TextureAtlas.AtlasRegion> diffuse;
    public final Animation<TextureAtlas.AtlasRegion> bloom;

    public final float atlasWidthInv;
    public final float atlasHeightInv;
    public final int width;
    public final int height;

    public AnimatedDiffuseBloomMapsMaterialInstance(final TextureAtlas spriteSheet,
                                                    final String diffuseRegionName,
                                                    final String bloomRegionName,
                                                    final float frameDuration,
                                                    final Animation.PlayMode playMode) {
        this.glTextureObjectHandle = spriteSheet.getTextures().first().getTextureObjectHandle();
        this.spriteSheet = spriteSheet;
        // for example: diffuse: metalCrissCross, normal map: metalCrissCrossNM
        this.diffuse = new Animation<TextureAtlas.AtlasRegion>(frameDuration, spriteSheet.findRegions(diffuseRegionName));
        this.diffuse.setPlayMode(playMode);
        this.bloom = new Animation<TextureAtlas.AtlasRegion>(frameDuration, spriteSheet.findRegions(bloomRegionName));
        this.bloom.setPlayMode(playMode);
        // uv-remapping
        this.width = diffuse.getKeyFrame(0).getRegionWidth();
        this.height = diffuse.getKeyFrame(0).getRegionHeight();
        this.atlasWidthInv = 1f / spriteSheet.getTextures().first().getWidth();
        this.atlasHeightInv = 1f / spriteSheet.getTextures().first().getHeight();
    }

    @Override
    public int getBloomX(float elapsedTime) {
        return bloom.getKeyFrame(elapsedTime).getRegionX();
    }

    @Override
    public int getBloomY(float elapsedTime) {
        return bloom.getKeyFrame(elapsedTime).getRegionY();
    }

    @Override
    public int getDiffuseX(float elapsedTime) {
        return diffuse.getKeyFrame(elapsedTime).getRegionX();
    }

    @Override
    public int getDiffuseY(float elapsedTime) {
        return diffuse.getKeyFrame(elapsedTime).getRegionY();
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
        return ShadingMethod.DiffuseBloomMaps;
    }

}
