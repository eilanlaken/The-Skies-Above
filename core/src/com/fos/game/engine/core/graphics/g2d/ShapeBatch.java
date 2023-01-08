package com.fos.game.engine.core.graphics.g2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class ShapeBatch extends ShapeDrawer {

    public ShapeBatch(final Batch batch) {
        super(batch, createWhitePixelTextureRegion());
    }

    @Override
    public void update() {
        super.update();
        setColor(Color.WHITE);
    }

    private static final TextureRegion createWhitePixelTextureRegion() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.drawPixel(0, 0);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        TextureRegion region = new TextureRegion(texture, 0, 0, 1, 1);
        return region;
    }
}
