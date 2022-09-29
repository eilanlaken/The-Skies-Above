package com.fos.game.engine.components.animation;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class SpriteSheet extends TextureAtlas {

    public final String filepath;

    public SpriteSheet(final TextureAtlasData data, final String filepath) {
        super(data);
        this.filepath = filepath;
    }

}
