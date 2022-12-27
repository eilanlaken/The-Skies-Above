package com.fos.game.engine.ecs.components.effects;

import com.fos.game.engine.core.files.assets.GameAssetManager;
import com.fos.game.engine.core.files.serialization.JsonConverter;
import com.fos.game.engine.core.graphics.shaders.postprocessing.PostProcessingEffect;
import com.fos.game.engine.ecs.components.base.Factory;

public class FactoryEffects extends Factory {

    public FactoryEffects(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
    }

    public ComponentFullScreenEffect createFullScreenEffect(final String vertex, final String fragment, int priority) {
        PostProcessingEffect postProcessingEffect = new PostProcessingEffect(vertex, fragment);
        return new ComponentFullScreenEffect(postProcessingEffect, priority);
    }

}
