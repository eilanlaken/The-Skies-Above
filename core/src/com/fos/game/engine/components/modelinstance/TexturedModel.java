package com.fos.game.engine.components.modelinstance;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Model;

public class TexturedModel {

    public final Model model;
    public final TextureAtlas[] spriteSheets;

    public TexturedModel(final Model model, final TextureAtlas... spriteSheets) {
        this.model = model;
        this.spriteSheets = spriteSheets;
    }

    public TexturedModel(final Model model) {
        this.model = model;
        this.spriteSheets = null;
    }

}
