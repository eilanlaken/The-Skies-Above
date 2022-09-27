package com.fos.game.engine.files.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.JsonReader;
import com.fos.game.engine.renderer.materials.base.FOSMaterial;
import com.fos.game.engine.components.modelinstance.UtilsModel;

import java.nio.FloatBuffer;
import java.util.HashMap;

public class GameAssetManager extends AssetManager {

    // catalogs
    public final HashMap<String, TextureAtlas> namedSpriteSheets = new HashMap<>();
    public final HashMap<String, Array<TextureAtlas.AtlasRegion>> namedAtlasRegionArrays = new HashMap<>(); // <- TODO: use this.
    public final HashMap<String, FOSMaterial> namedMaterials = new HashMap<>();

    // buffers
    public static final Array<TextureAtlas> TEXTURE_ATLASES_ARRAY = new Array<>();

    public GameAssetManager() {
        super();
        this.setLoader(Model.class, ".inst", new G3dModelLoader(new JsonReader(), super.getFileHandleResolver()));
    }

    @Override
    public void clear() {
        super.clear();
        this.namedSpriteSheets.clear();
        namedAtlasRegionArrays.clear();
        namedMaterials.clear();
    }

    @Override
    public void load(String name, Class clazz) {
        super.load(name, clazz);
    }

    @Override
    public void unload(String name) {
        Class clazz = getAssetType(name);
        if (clazz == TextureAtlas.class) {
            this.namedSpriteSheets.remove(name);
        }
        super.unload(name);
    }

    @Override
    public boolean update() {
        boolean done = super.update();
        if (done) {
            try {
                wrap();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        return done;
    }

    private void wrap() throws ModelDataMismatchException {
        wrapTextures();
        wrapMeshes();
    }

    private void wrapTextures() {
        Array<TextureAtlas> allTextureAtlases = new Array<>();
        getAll(TextureAtlas.class, allTextureAtlases);
        for (TextureAtlas textureAtlas : allTextureAtlases) {
            String id = getAssetFileName(textureAtlas);
            namedSpriteSheets.put(id, textureAtlas);
        }
        setAnisotropicFilteringForAllTextures();
        createAnimationsCatalog();
    }

    private void wrapMeshes() throws ModelDataMismatchException {
        Array<Model> allModels = new Array<>();
        getAll(Model.class, allModels);
        for (Model model : allModels) {
            String id = getAssetFileName(model);
            if (!isModelInstanced(id)) continue;
            Array<Node> nodes = model.nodes;
            Array<MeshPart> meshParts = model.meshParts;
            if (meshParts.size != 1) throw new ModelDataMismatchException();
            final int INSTANCE_COUNT = nodes.size;
            Mesh mesh = meshParts.get(0).mesh;
            UtilsModel.prepareForStaticBundleInstancing(mesh, INSTANCE_COUNT, nodes);
        }
    }

    private void createAnimationsCatalog() {
        TEXTURE_ATLASES_ARRAY.clear();
        getAll(TextureAtlas.class, TEXTURE_ATLASES_ARRAY);
        for (TextureAtlas textureAtlas : TEXTURE_ATLASES_ARRAY) {
            Array<TextureAtlas.AtlasRegion> regions = textureAtlas.getRegions();
            for (TextureAtlas.AtlasRegion region : regions) {
                Object frames = namedAtlasRegionArrays.get(region.name);
                if (frames == null) {
                    final Array<TextureAtlas.AtlasRegion> regionsForName = new Array<>();
                    regionsForName.add(region);
                    namedAtlasRegionArrays.put(region.name, regionsForName);
                } else {
                    final Array<TextureAtlas.AtlasRegion> regionsForName = (Array<TextureAtlas.AtlasRegion>) frames;
                    regionsForName.add(region);
                }
            }
        }
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

        for (String textureAtlasName : namedSpriteSheets.keySet()) {
            final TextureAtlas atlas = namedSpriteSheets.get(textureAtlasName);
            setAnisotropicFiltering(atlas, maxAnisotropy);
        }
    }

    private void setAnisotropicFiltering(final TextureAtlas atlas, float maxAnisotropy) {
        for (Texture texture : atlas.getTextures()) {
            texture.bind();
            Gdx.gl30.glTexParameterf(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MAX_ANISOTROPY_EXT, Math.min(16, maxAnisotropy));
            texture.bind(0);
        }
    }

    // helper methods
    private static boolean isModelInstanced(final String id) {
        return id.endsWith(".inst");
    }

    public static class ModelDataMismatchException extends Exception {
        @Override
        public String getMessage() {
            return super.getMessage() + " Instanced Models should contain a single mesh part.";
        }
    }

}
