package com.fos.game.engine.ecs.systems.renderer_old.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.fos.game.engine.ecs.components.transform2d_old.ComponentTransform2D;

public class SpriteBatch extends com.badlogic.gdx.graphics.g2d.SpriteBatch {

    public void draw2(final TextureAtlas.AtlasRegion region, final ComponentTransform2D transform2D, final float pixelsPerMeterX, final float pixelsPerMeterY) {
        final float screenWidth = Gdx.graphics.getWidth();
        final float screenHeight = Gdx.graphics.getHeight();
        //final float stretchXFactor = viewportWidth / screenWidth;
        //final float stretchYFactor = viewportHeight / screenHeight;
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

    public void draw1(final TextureAtlas.AtlasRegion region, final ComponentTransform2D transform2D, final float pixelsPerMeter) {
        final float screenWidth = Gdx.graphics.getWidth();
        final float screenHeight = Gdx.graphics.getHeight();
        //final float stretchXFactor = viewportWidth / screenWidth;
        //final float stretchYFactor = viewportHeight / screenHeight;
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
    public void draw(final TextureAtlas.AtlasRegion region, final ComponentTransform2D transform2D, final float viewportWidth, final float viewportHeight) {
        final float screenWidth = Gdx.graphics.getWidth();
        final float screenHeight = Gdx.graphics.getHeight();
        final float stretchXFactor = viewportWidth / screenWidth;
        final float stretchYFactor = viewportHeight / screenHeight;
        final float x = transform2D.getPosition().x;
        final float y = transform2D.getPosition().y;
        final float rotation = transform2D.getRotation() * MathUtils.radiansToDegrees;
        final float regionOriginalWidthHalf = region.originalWidth / 2f;
        final float regionOriginalHeightHalf = region.originalHeight / 2f;
        final float regionWidthHalf = region.getRegionWidth() / 2f;
        final float regionHeightHalf = region.getRegionHeight() / 2f;

        System.out.println("stretchXFactor: " + stretchXFactor);
        System.out.println("stretchYFactor: " + stretchYFactor);


        super.draw(	region,

                x - regionWidthHalf - (regionOriginalWidthHalf - regionWidthHalf) + region.offsetX,
                y - regionHeightHalf - (regionOriginalHeightHalf - regionHeightHalf) + region.offsetY,

                regionWidthHalf + (regionOriginalWidthHalf - regionWidthHalf) - region.offsetX,
                regionHeightHalf + (regionOriginalHeightHalf - regionHeightHalf) - region.offsetY,

                region.getRegionWidth(),
                region.getRegionHeight(),

                transform2D.scaleX * stretchXFactor,
                transform2D.scaleY * stretchYFactor,

                rotation);
    }

    @Deprecated
    public void draw(final TextureAtlas.AtlasRegion region, final Body body, final float viewportWidth, final float viewportHeight) {
        final float screenWidth = Gdx.graphics.getWidth();
        final float screenHeight = Gdx.graphics.getHeight();
        final float stretchXFactor = viewportWidth / screenWidth;
        final float stretchYFactor = viewportHeight / screenHeight;
        final float x = body.getPosition().x;
        final float y = body.getPosition().y;
        final float rotation = body.getAngle() * MathUtils.radiansToDegrees;
        final float regionOriginalWidthHalf = region.originalWidth / 2;
        final float regionOriginalHeightHalf = region.originalHeight / 2;
        final float regionWidthHalf = region.getRegionWidth() / 2;
        final float regionHeightHalf = region.getRegionHeight() / 2;
        super.draw(	region,

                x - regionWidthHalf - (regionOriginalWidthHalf - regionWidthHalf) + region.offsetX,
                y - regionHeightHalf - (regionOriginalHeightHalf - regionHeightHalf) + region.offsetY,

                regionWidthHalf + (regionOriginalWidthHalf - regionWidthHalf) - region.offsetX,
                regionHeightHalf + (regionOriginalHeightHalf - regionHeightHalf) - region.offsetY,

                region.getRegionWidth(),
                region.getRegionHeight(),

                1f * stretchXFactor,
                1f * stretchYFactor,

                rotation);
    }

    public void draw(final TextureAtlas.AtlasRegion region, final Body body) {
        final float x = body.getPosition().x;
        final float y = body.getPosition().y;
        final float rotation = body.getAngle() * MathUtils.radiansToDegrees;
        super.draw(	region,

                x - region.getRegionWidth() / 2 - (region.originalWidth / 2 - region.getRegionWidth() / 2) + region.offsetX,
                y - region.getRegionHeight() / 2 - (region.originalHeight / 2 - region.getRegionHeight() / 2) + region.offsetY,

                region.getRegionWidth() / 2 + (region.originalWidth / 2 - region.getRegionWidth() / 2) - region.offsetX,
                region.getRegionHeight() / 2 + (region.originalHeight / 2 - region.getRegionHeight() / 2) - region.offsetY,

                region.getRegionWidth(),
                region.getRegionHeight(),

                1f,
                1f,

                rotation);
    }

    @Deprecated
    public void render(final TextureAtlas.AtlasRegion atlasRegion, ComponentTransform2D transform2D) {
        final float x = transform2D.getPosition().x;
        final float y = transform2D.getPosition().y;
        final float scaleX = transform2D.scaleX;
        final float scaleY = transform2D.scaleY;
        final float rotation = transform2D.getRotation() * MathUtils.radiansToDegrees;
        super.draw(	atlasRegion,

                x - atlasRegion.getRegionWidth() / 2 - (atlasRegion.originalWidth / 2 - atlasRegion.getRegionWidth() / 2) + atlasRegion.offsetX,
                y - atlasRegion.getRegionHeight() / 2 - (atlasRegion.originalHeight / 2 - atlasRegion.getRegionHeight() / 2) + atlasRegion.offsetY,

                atlasRegion.getRegionWidth() / 2 + (atlasRegion.originalWidth / 2 - atlasRegion.getRegionWidth() / 2) - atlasRegion.offsetX,
                atlasRegion.getRegionHeight() / 2 + (atlasRegion.originalHeight / 2 - atlasRegion.getRegionHeight() / 2) - atlasRegion.offsetY,

                atlasRegion.getRegionWidth(),
                atlasRegion.getRegionHeight(),

                scaleX,
                scaleY,

                rotation);
    }

    @Deprecated
    public void render(final TextureAtlas.AtlasRegion atlasRegion, ComponentTransform2D transform2D, float ppm) {
        final float physicsX = transform2D.getPosition().x;
        final float physicsY = transform2D.getPosition().y;
        final float scaleX = transform2D.scaleX;
        final float scaleY = transform2D.scaleY;
        final float rotation = transform2D.getRotation() * MathUtils.radiansToDegrees;
        super.draw(	atlasRegion,

                physicsX * ppm - atlasRegion.getRegionWidth() / 2 - (atlasRegion.originalWidth / 2 - atlasRegion.getRegionWidth() / 2) + atlasRegion.offsetX,
                physicsY * ppm - atlasRegion.getRegionHeight() / 2 - (atlasRegion.originalHeight / 2 - atlasRegion.getRegionHeight() / 2) + atlasRegion.offsetY,

                atlasRegion.getRegionWidth() / 2 + (atlasRegion.originalWidth / 2 - atlasRegion.getRegionWidth() / 2) - atlasRegion.offsetX,
                atlasRegion.getRegionHeight() / 2 + (atlasRegion.originalHeight / 2 - atlasRegion.getRegionHeight() / 2) - atlasRegion.offsetY,

                atlasRegion.getRegionWidth(),
                atlasRegion.getRegionHeight(),

                scaleX,
                scaleY,

                rotation);
    }

}
