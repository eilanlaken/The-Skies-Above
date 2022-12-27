package com.fos.game.engine.core.graphics.g2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;

public class CustomSpriteBatch extends com.badlogic.gdx.graphics.g2d.SpriteBatch {

    public CustomSpriteBatch() {
        super(1000);
    }

    public void draw(final TextureAtlas.AtlasRegion region, final float x, final float y, final float angleRad, final float scaleX, final float scaleY, final float size, final int pixelsPerUnit) {
        final float rotation = angleRad * MathUtils.radiansToDegrees;
        final float regionOriginalWidthHalf = region.originalWidth / 2f;
        final float regionOriginalHeightHalf = region.originalHeight / 2f;
        final float regionWidthHalf = region.getRegionWidth() / 2f;
        final float regionHeightHalf = region.getRegionHeight() / 2f;
        final float scaleFactor = size / pixelsPerUnit;
        super.draw(	region,

                x - regionWidthHalf - (regionOriginalWidthHalf - regionWidthHalf) + region.offsetX,
                y - regionHeightHalf - (regionOriginalHeightHalf - regionHeightHalf) + region.offsetY,

                regionWidthHalf + (regionOriginalWidthHalf - regionWidthHalf) - region.offsetX,
                regionHeightHalf + (regionOriginalHeightHalf - regionHeightHalf) - region.offsetY,

                region.getRegionWidth(),
                region.getRegionHeight(),

                scaleX * scaleFactor,
                scaleY * scaleFactor,

                rotation);
    }

}
