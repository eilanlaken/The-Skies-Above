package com.fos.game.engine.files.assets;

import com.badlogic.gdx.graphics.g3d.Model;

public class AssetDescriptor {

    public final Model model;
    public final String nodeId;
    public final String rigId;
    public final String textureAtlasName;

    public AssetDescriptor(final Model model, final String nodeId, final String rigId, final String textureAtlasName) {
        this.model = model;
        this.nodeId = nodeId;
        this.rigId = rigId;
        this.textureAtlasName = textureAtlasName;
    }

}
