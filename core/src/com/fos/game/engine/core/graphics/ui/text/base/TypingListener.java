// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.base;

public interface TypingListener
{
    void event(final String p0);
    
    void end();
    
    String replaceVariable(final String p0);
    
    void onChar(final long p0);
}
