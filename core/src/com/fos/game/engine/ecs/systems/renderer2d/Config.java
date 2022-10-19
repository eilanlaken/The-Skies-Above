package com.fos.game.engine.ecs.systems.renderer2d;

import com.fos.game.engine.ecs.systems.base.SystemConfig;

public final class Config extends SystemConfig {

    public static final Config DEFAULT = new Config(true, 1f / 2.2f);
    public final boolean debugMode;
    public final float gamma;

    public Config(final boolean debugMode, final float gamma) {
        this.debugMode = debugMode;
        this.gamma = gamma;
    }

}
