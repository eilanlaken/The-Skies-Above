package com.fos.game.engine.components.modelinstance;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.fos.game.engine.components.animations2d.SpriteSheet;

// TODO: implement. Add loaders, consider all necessary file paths, etc.
public class TexturedModel extends Model {

    public final String filepath;
    public final SpriteSheet[] spriteSheets;

    public TexturedModel(final ModelData modelData, final String filepath, final SpriteSheet[] spriteSheets) {
        super(modelData);
        this.filepath = filepath;
        this.spriteSheets = spriteSheets;
    }

}
