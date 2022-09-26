package com.fos.game.engine.renderer.system;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.fos.game.engine.components.transform.ComponentTransform2D;

public class SpriteBatch extends com.badlogic.gdx.graphics.g2d.SpriteBatch {

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
