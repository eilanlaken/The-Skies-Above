// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.base;

public enum TokenCategory
{
    WAIT, 
    SPEED, 
    COLOR, 
    VARIABLE, 
    EVENT, 
    RESET, 
    SKIP, 
    EFFECT_START, 
    EFFECT_END;
    
    private static /* synthetic */ TokenCategory[] $values() {
        return new TokenCategory[] { TokenCategory.WAIT, TokenCategory.SPEED, TokenCategory.COLOR, TokenCategory.VARIABLE, TokenCategory.EVENT, TokenCategory.RESET, TokenCategory.SKIP, TokenCategory.EFFECT_START, TokenCategory.EFFECT_END };
    }
}
