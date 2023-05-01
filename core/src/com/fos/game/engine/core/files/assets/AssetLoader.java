package com.fos.game.engine.core.files.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.fos.game.engine.core.graphics.g2d.SpriteSheet;

import java.nio.FloatBuffer;

public class AssetLoader extends AssetManager {

    public AssetLoader() {
        super();
        this.setLoader(SpriteSheet.class, new SpriteSheetLoader(getFileHandleResolver()));
    }

    @Override
    public boolean update() {
        boolean done = super.update();
        if (done) {
            try {
                setAnisotropicFilteringForAllTextures();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        return done;
    }

    private void setAnisotropicFilteringForAllTextures() {
        boolean anisotropicSupported = Gdx.graphics.supportsExtension("GL_EXT_texture_filter_anisotropic");
        if (!anisotropicSupported) return;
        FloatBuffer buffer = BufferUtils.newFloatBuffer(64);
        if (Gdx.gl30 != null)
            Gdx.gl30.glGetFloatv(GL20.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT, buffer);
        else if (Gdx.gl20 != null)
            Gdx.gl20.glGetFloatv(GL20.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT, buffer);
        else if (Gdx.gl != null)
            Gdx.gl.glGetFloatv(GL20.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT, buffer);
        else
            throw new GdxRuntimeException("GL11 not available"); // should never reach this
        float maxAnisotropy = buffer.get(0);

        Array<SpriteSheet> spriteSheets = new Array<>();
        getAll(SpriteSheet.class, spriteSheets);
        for (SpriteSheet spriteSheet : spriteSheets) {
            for (Texture texture : spriteSheet.getTextures()) {
                texture.bind();
                Gdx.gl30.glTexParameterf(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MAX_ANISOTROPY_EXT, Math.min(16, maxAnisotropy));
                texture.bind(0);
            }
        }
    }

}
