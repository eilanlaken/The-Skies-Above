package com.fos.game.engine.ecs.systems.renderer.materials.instances;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.fos.game.engine.ecs.systems.renderer.materials.base.Material;
import com.fos.game.engine.ecs.systems.renderer.materials.base.RenderedDiffuseMap;
import com.fos.game.engine.ecs.systems.renderer.shaders.base.ShadingMethod;

public class AnimatedDiffuseMapMaterialInstance extends Material implements RenderedDiffuseMap {

    public final int glTextureObjectHandle;
    public final TextureAtlas spriteSheet;
    public final Animation<TextureAtlas.AtlasRegion> diffuse;

    public final float shineDamper;
    public final float reflectivity;
    public final float ambient;
    public final int width;
    public final int height;
    public final float atlasWidthInv;
    public final float atlasHeightInv;

    public AnimatedDiffuseMapMaterialInstance(final TextureAtlas spriteSheet, final String regionName,
                                              final float shineDamper, final float reflectivity, final float ambient,
                                              float frameDuration, final Animation.PlayMode playMode) {

        this.glTextureObjectHandle = spriteSheet.getTextures().first().getTextureObjectHandle();
        this.spriteSheet = spriteSheet;
        this.diffuse = new Animation<TextureAtlas.AtlasRegion>(frameDuration, spriteSheet.findRegions(regionName));
        this.diffuse.setPlayMode(Animation.PlayMode.LOOP);
        this.width = diffuse.getKeyFrame(0).getRegionWidth();
        this.height = diffuse.getKeyFrame(0).getRegionHeight();
        this.atlasWidthInv = 1f / spriteSheet.getTextures().first().getWidth();
        this.atlasHeightInv = 1f / spriteSheet.getTextures().first().getHeight();

        this.shineDamper = shineDamper;
        this.reflectivity = reflectivity;
        this.ambient = ambient;
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
