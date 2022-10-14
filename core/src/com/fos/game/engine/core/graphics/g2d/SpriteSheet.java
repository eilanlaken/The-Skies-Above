package com.fos.game.engine.core.graphics.g2d;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

public class SpriteSheet extends TextureAtlas {

    private final HashMap<String, Array<AtlasRegion>> namedRegions = new HashMap<>();

    public SpriteSheet(final TextureAtlasData data) {
        super(data);
        for (TextureAtlasData.Region region : data.getRegions()) {
            String name = region.name;
            if (namedRegions.get(name) != null) continue;
            Array<AtlasRegion> regions = super.findRegions(name);
            namedRegions.put(name, regions);
        }
    }

    @Override
    public Array<AtlasRegion> findRegions(final String name) {
        return namedRegions.get(name);
    }


}
