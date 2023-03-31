// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.base;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.IntFloatMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.OrderedMap;
import com.fos.game.engine.core.graphics.ui.text.effects.*;

public class TypingConfig
{
    public static float DEFAULT_WAIT_VALUE;
    public static float DEFAULT_SPEED_PER_CHAR;
    public static float MIN_SPEED_MODIFIER;
    public static float MAX_SPEED_MODIFIER;
    public static int CHAR_LIMIT_PER_FRAME;
    public static Color DEFAULT_CLEAR_COLOR;
    public static IntFloatMap INTERVAL_MULTIPLIERS_BY_CHAR;
    public static final ObjectMap<String, String> GLOBAL_VARS;
    static final OrderedMap<String, Class<? extends Effect>> EFFECT_START_TOKENS;
    static final OrderedMap<String, Class<? extends Effect>> EFFECT_END_TOKENS;
    static boolean dirtyEffectMaps;
    
    public static void registerEffect(final String startTokenName, final String endTokenName, final Class<? extends Effect> effectClass) {
        TypingConfig.EFFECT_START_TOKENS.put(startTokenName.toUpperCase(), effectClass);
        TypingConfig.EFFECT_END_TOKENS.put(endTokenName.toUpperCase(), effectClass);
        TypingConfig.dirtyEffectMaps = true;
    }
    
    public static void unregisterEffect(final String startTokenName, final String endTokenName) {
        TypingConfig.EFFECT_START_TOKENS.remove(startTokenName.toUpperCase());
        TypingConfig.EFFECT_END_TOKENS.remove(endTokenName.toUpperCase());
        TypingConfig.dirtyEffectMaps = true;
    }
    
    static {
        TypingConfig.DEFAULT_WAIT_VALUE = 0.25f;
        TypingConfig.DEFAULT_SPEED_PER_CHAR = 0.035f;
        TypingConfig.MIN_SPEED_MODIFIER = 0.001f;
        TypingConfig.MAX_SPEED_MODIFIER = 100.0f;
        TypingConfig.CHAR_LIMIT_PER_FRAME = -1;
        TypingConfig.DEFAULT_CLEAR_COLOR = new Color(Color.WHITE);
        TypingConfig.INTERVAL_MULTIPLIERS_BY_CHAR = new IntFloatMap();
        GLOBAL_VARS = new ObjectMap();
        EFFECT_START_TOKENS = new OrderedMap();
        EFFECT_END_TOKENS = new OrderedMap();
        TypingConfig.dirtyEffectMaps = true;
        TypingConfig.INTERVAL_MULTIPLIERS_BY_CHAR.put(32, 0.0f);
        TypingConfig.INTERVAL_MULTIPLIERS_BY_CHAR.put(58, 1.5f);
        TypingConfig.INTERVAL_MULTIPLIERS_BY_CHAR.put(44, 2.5f);
        TypingConfig.INTERVAL_MULTIPLIERS_BY_CHAR.put(46, 2.5f);
        TypingConfig.INTERVAL_MULTIPLIERS_BY_CHAR.put(33, 5.0f);
        TypingConfig.INTERVAL_MULTIPLIERS_BY_CHAR.put(63, 5.0f);
        TypingConfig.INTERVAL_MULTIPLIERS_BY_CHAR.put(10, 2.5f);
        registerEffect("EASE", "ENDEASE", EaseEffect.class);
        registerEffect("HANG", "ENDHANG", HangEffect.class);
        registerEffect("JUMP", "ENDJUMP", JumpEffect.class);
        registerEffect("SHAKE", "ENDSHAKE", ShakeEffect.class);
        registerEffect("SICK", "ENDSICK", SickEffect.class);
        registerEffect("SLIDE", "ENDSLIDE", SlideEffect.class);
        registerEffect("WAVE", "ENDWAVE", WaveEffect.class);
        registerEffect("WIND", "ENDWIND", WindEffect.class);
        registerEffect("RAINBOW", "ENDRAINBOW", RainbowEffect.class);
        registerEffect("GRADIENT", "ENDGRADIENT", GradientEffect.class);
        registerEffect("FADE", "ENDFADE", FadeEffect.class);
        registerEffect("BLINK", "ENDBLINK", BlinkEffect.class);
        registerEffect("JOLT", "ENDJOLT", JoltEffect.class);
        registerEffect("SPIRAL", "ENDSPIRAL", SpiralEffect.class);
        registerEffect("SPIN", "ENDSPIN", SpinEffect.class);
        registerEffect("CROWD", "ENDCROWD", CrowdEffect.class);
        registerEffect("SHRINK", "ENDSHRINK", ShrinkEffect.class);
        registerEffect("EMERGE", "ENDEMERGE", EmergeEffect.class);
        registerEffect("HEARTBEAT", "ENDHEARTBEAT", HeartbeatEffect.class);
        registerEffect("CAROUSEL", "ENDCAROUSEL", CarouselEffect.class);
        registerEffect("SQUASH", "ENDSQUASH", SquashEffect.class);
        registerEffect("SCALE", "ENDSCALE", ScaleEffect.class);
        registerEffect("ROTATE", "ENDROTATE", RotateEffect.class);
        registerEffect("HIGHLIGHT", "ENDHIGHLIGHT", HighlightEffect.class);
        registerEffect("LINK", "ENDLINK", LinkEffect.class);
        registerEffect("TRIGGER", "ENDTRIGGER", TriggerEffect.class);
        registerEffect("ATTENTION", "ENDATTENTION", AttentionEffect.class);
        registerEffect("STYLIST", "ENDSTYLIST", StylistEffect.class);
    }
}
