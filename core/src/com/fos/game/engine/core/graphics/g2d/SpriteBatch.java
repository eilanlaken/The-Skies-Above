package com.fos.game.engine.core.graphics.g2d;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.fos.game.engine.core.graphics.shaders.base.DefaultShaderProgram;
import com.fos.game.engine.ecs.components.transform2d_old.ComponentTransform2D;

public class SpriteBatch extends com.badlogic.gdx.graphics.g2d.SpriteBatch {

    private static final Vector3 vector = new Vector3(); // <- for internal calculations
    private static final Quaternion quaternion = new Quaternion(); // <- for internal calculations

    public SpriteBatch() {
        super(1000, new DefaultShaderProgram());
    }

    public void draw(final TextureAtlas.AtlasRegion region, final Matrix4 transform, final float size, final int pixelsPerUnit) {
        final float x = transform.getTranslation(vector).x;
        final float y = transform.getTranslation(vector).y;
        final float rotation = transform.getRotation(quaternion).getAngleAround(0,0,1) * MathUtils.radiansToDegrees;
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

                transform.getScaleX() * scaleFactor,
                transform.getScaleY() * scaleFactor,

                rotation);
    }

    @Deprecated
    public void draw(final TextureAtlas.AtlasRegion region, final ComponentTransform2D transform2D, final float size, final int pixelsPerUnit) {
        final float x = transform2D.getPosition().x;
        final float y = transform2D.getPosition().y;
        final float rotation = transform2D.getRotation() * MathUtils.radiansToDegrees;
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

                transform2D.scaleX * scaleFactor,
                transform2D.scaleY * scaleFactor,

                rotation);
    }

    @Deprecated
    public void draw(final TextureAtlas.AtlasRegion region, final ComponentTransform2D transform2D, final float pixelsPerMeter) {
        final float x = transform2D.getPosition().x;
        final float y = transform2D.getPosition().y;
        final float rotation = transform2D.getRotation() * MathUtils.radiansToDegrees;
        final float regionOriginalWidthHalf = region.originalWidth / 2f;
        final float regionOriginalHeightHalf = region.originalHeight / 2f;
        final float regionWidthHalf = region.getRegionWidth() / 2f;
        final float regionHeightHalf = region.getRegionHeight() / 2f;

        super.draw(	region,

                x - regionWidthHalf - (regionOriginalWidthHalf - regionWidthHalf) + region.offsetX,
                y - regionHeightHalf - (regionOriginalHeightHalf - regionHeightHalf) + region.offsetY,

                regionWidthHalf + (regionOriginalWidthHalf - regionWidthHalf) - region.offsetX,
                regionHeightHalf + (regionOriginalHeightHalf - regionHeightHalf) - region.offsetY,

                region.getRegionWidth(),
                region.getRegionHeight(),

                transform2D.scaleX / pixelsPerMeter,
                transform2D.scaleY / pixelsPerMeter,

                rotation);
    }

    @Deprecated
    public void draw(final TextureAtlas.AtlasRegion region, final ComponentTransform2D transform2D, final float pixelsPerMeterX, final float pixelsPerMeterY) {
        final float x = transform2D.getPosition().x;
        final float y = transform2D.getPosition().y;
        final float rotation = transform2D.getRotation() * MathUtils.radiansToDegrees;
        final float regionOriginalWidthHalf = region.originalWidth / 2f;
        final float regionOriginalHeightHalf = region.originalHeight / 2f;
        final float regionWidthHalf = region.getRegionWidth() / 2f;
        final float regionHeightHalf = region.getRegionHeight() / 2f;

        super.draw(	region,

                x - regionWidthHalf - (regionOriginalWidthHalf - regionWidthHalf) + region.offsetX,
                y - regionHeightHalf - (regionOriginalHeightHalf - regionHeightHalf) + region.offsetY,

                regionWidthHalf + (regionOriginalWidthHalf - regionWidthHalf) - region.offsetX,
                regionHeightHalf + (regionOriginalHeightHalf - regionHeightHalf) - region.offsetY,

                region.getRegionWidth(),
                region.getRegionHeight(),

                transform2D.scaleX / pixelsPerMeterX,
                transform2D.scaleY / pixelsPerMeterY,

                rotation);
    }

}
