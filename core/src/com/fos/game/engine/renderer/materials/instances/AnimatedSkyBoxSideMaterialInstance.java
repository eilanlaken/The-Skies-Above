package com.fos.game.engine.renderer.materials.instances;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.fos.game.engine.renderer.materials.base.Material;
import com.fos.game.engine.renderer.materials.base.RenderedDiffuseMap;
import com.fos.game.engine.renderer.shaders.base.ShadingMethod;

public class AnimatedSkyBoxSideMaterialInstance extends Material implements RenderedDiffuseMap {

    public final int glTextureObjectHandle;
    public final TextureAtlas spriteSheet;
    public final Animation<TextureAtlas.AtlasRegion> diffuseAnimation;

    public final float atlasWidthInv;
    public final float atlasHeightInv;

    public AnimatedSkyBoxSideMaterialInstance(final TextureAtlas spriteSheet, final String animationName, final float frameDuration) {
        this.glTextureObjectHandle = spriteSheet.getTextures().first().getTextureObjectHandle();
        this.spriteSheet = spriteSheet;
        this.diffuseAnimation = new Animation<TextureAtlas.AtlasRegion>(frameDuration, spriteSheet.findRegions(animationName));
        this.diffuseAnimation.setPlayMode(Animation.PlayMode.LOOP_RANDOM);
        this.atlasWidthInv = 1f / spriteSheet.getTextures().first().getWidth();
        this.atlasHeightInv = 1f / spriteSheet.getTextures().first().getHeight();
    }

    @Override
    public int getDiffuseX(final float elapsedTime) {
        return diffuseAnimation.getKeyFrame(elapsedTime).getRegionX();
    }

    @Override
    public int getDiffuseY(final float elapsedTime) {
        return diffuseAnimation.getKeyFrame(elapsedTime).getRegionY();
    }

    @Override
    public int getWidth(final float elapsedTime) {
        return diffuseAnimation.getKeyFrame(elapsedTime).getRegionWidth();
    }

    @Override
    public int getHeight(final float elapsedTime) {
        return diffuseAnimation.getKeyFrame(elapsedTime).getRegionHeight();
    }

    @Override
    public int getGLTextureHandle() {
        return glTextureObjectHandle;
    }

    @Override
    public ShadingMethod getShadingMethod() {
        return ShadingMethod.PlainSkyBox;
    }
}
