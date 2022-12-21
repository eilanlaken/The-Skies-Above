// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.base;

enum InternalToken
{
    WAIT("WAIT", TokenCategory.WAIT), 
    SPEED("SPEED", TokenCategory.SPEED), 
    SLOWER("SLOWER", TokenCategory.SPEED), 
    SLOW("SLOW", TokenCategory.SPEED), 
    NORMAL("NORMAL", TokenCategory.SPEED), 
    FAST("FAST", TokenCategory.SPEED), 
    FASTER("FASTER", TokenCategory.SPEED), 
    NATURAL("NATURAL", TokenCategory.SPEED), 
    COLOR("COLOR", TokenCategory.COLOR), 
    STYLE("STYLE", TokenCategory.COLOR), 
    SIZE("SIZE", TokenCategory.COLOR), 
    FONT("FONT", TokenCategory.COLOR), 
    CLEARCOLOR("CLEARCOLOR", TokenCategory.COLOR), 
    CLEARSIZE("CLEARSIZE", TokenCategory.COLOR), 
    CLEARFONT("CLEARFONT", TokenCategory.COLOR), 
    ENDCOLOR("ENDCOLOR", TokenCategory.COLOR), 
    VAR("VAR", TokenCategory.VARIABLE), 
    EVENT("EVENT", TokenCategory.EVENT), 
    RESET("RESET", TokenCategory.RESET), 
    SKIP("SKIP", TokenCategory.SKIP);
    
    final String name;
    final TokenCategory category;
    
    private InternalToken(final String name, final TokenCategory category) {
        this.name = name;
        this.category = category;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    static InternalToken fromName(final String name) {
        if (name != null) {
            for (final InternalToken token : values()) {
                if (name.equalsIgnoreCase(token.name)) {
                    return token;
                }
            }
        }
        return null;
    }
    
    private static /* synthetic */ InternalToken[] $values() {
        return new InternalToken[] { InternalToken.WAIT, InternalToken.SPEED, InternalToken.SLOWER, InternalToken.SLOW, InternalToken.NORMAL, InternalToken.FAST, InternalToken.FASTER, InternalToken.NATURAL, InternalToken.COLOR, InternalToken.STYLE, InternalToken.SIZE, InternalToken.FONT, InternalToken.CLEARCOLOR, InternalToken.CLEARSIZE, InternalToken.CLEARFONT, InternalToken.ENDCOLOR, InternalToken.VAR, InternalToken.EVENT, InternalToken.RESET, InternalToken.SKIP };
    }
}
