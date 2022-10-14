package com.fos.game.engine.core.files.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.ecs.components.audio.SoundEffect;

public class SoundEffectLoader extends AsynchronousAssetLoader<SoundEffect, SoundEffectLoader.SoundEffectParameter> {

    private SoundEffect soundEffect;

    public SoundEffectLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, SoundEffectLoader.SoundEffectParameter parameter) {
        //soundEffect = new SoundEffect(Gdx.audio.newSound(file));
    }

    @Override
    public SoundEffect loadSync(AssetManager manager, String fileName, FileHandle file, SoundEffectLoader.SoundEffectParameter parameter) {
        SoundEffect soundEffect = this.soundEffect;
        this.soundEffect = null;
        return soundEffect;
    }

    @Override
    public Array<AssetDescriptor> getDependencies (String fileName, FileHandle file, SoundEffectLoader.SoundEffectParameter parameter) {
        return null;
    }

    static public class SoundEffectParameter extends AssetLoaderParameters<SoundEffect> {
    }
}
