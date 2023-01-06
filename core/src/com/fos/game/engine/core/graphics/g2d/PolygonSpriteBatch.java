package com.fos.game.engine.core.graphics.g2d;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;

public class PolygonSpriteBatch extends com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch {

    public void draw(final TextureAtlas.AtlasRegion region, final float x, final float y, final float angleRad, final float scaleX, final float scaleY, final float size, final int pixelsPerUnit) {
        final float rotation = angleRad * MathUtils.radiansToDegrees;
        final float regionOriginalWidthHalf = region.originalWidth * 0.5f;
        final float regionOriginalHeightHalf = region.originalHeight * 0.5f;
        final float regionWidthHalf = region.getRegionWidth() * 0.5f;
        final float regionHeightHalf = region.getRegionHeight() * 0.5f;
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
