package com.fos.game.engine.files.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.components.animation.SpriteSheet;

public class SpriteSheetLoader extends SynchronousAssetLoader<SpriteSheet, SpriteSheetLoader.SpriteSheetParameter> {

    private TextureAtlas.TextureAtlasData data;

    public SpriteSheetLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public SpriteSheet load(AssetManager assetManager, String fileName, FileHandle file, SpriteSheetLoader.SpriteSheetParameter parameter) {
        for (TextureAtlas.TextureAtlasData.Page page : data.getPages()) {
            Texture texture = assetManager.get(page.textureFile.path().replaceAll("\\\\", "/"), Texture.class);
            page.texture = texture;
        }

        SpriteSheet spriteSheet = new SpriteSheet(data, fileName);
        data = null;
        return spriteSheet;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle atlasFile, SpriteSheetLoader.SpriteSheetParameter parameter) {
        FileHandle imgDir = atlasFile.parent();

        if (parameter != null)
            data = new TextureAtlas.TextureAtlasData(atlasFile, imgDir, parameter.flip);
        else {
            data = new TextureAtlas.TextureAtlasData(atlasFile, imgDir, false);
        }

        Array<AssetDescriptor> dependencies = new Array();
        for (TextureAtlas.TextureAtlasData.Page page : data.getPages()) {
            TextureLoader.TextureParameter params = new TextureLoader.TextureParameter();
            params.format = page.format;
            params.genMipMaps = page.useMipMaps;
            params.minFilter = page.minFilter;
            params.magFilter = page.magFilter;
            dependencies.add(new AssetDescriptor(page.textureFile, Texture.class, params));
        }
        return dependencies;
    }

    static public class SpriteSheetParameter extends AssetLoaderParameters<SpriteSheet> {
        /** whether to flip the texture atlas vertically **/
        public boolean flip = false;

        public SpriteSheetParameter() {
        }

        public SpriteSheetParameter(boolean flip) {
            this.flip = flip;
        }
    }

}
