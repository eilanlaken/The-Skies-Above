package com.fos.game.engine.components.modelinstance;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Model;

@Deprecated
public class TexturedModel_old {

    public final Model model;
    public final TextureAtlas[] spriteSheets;

    public TexturedModel_old(final Model model, final TextureAtlas... spriteSheets) {
        this.model = model;
        this.spriteSheets = spriteSheets;
    }

    public TexturedModel_old(final Model model) {
        this.model = model;
        this.spriteSheets = null;
    }

}
