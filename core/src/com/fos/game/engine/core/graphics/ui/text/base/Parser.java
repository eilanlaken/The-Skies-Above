// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.base;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.Colors;
import com.fos.game.engine.core.graphics.ui.text.utils.Palette;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.math.MathUtils;
import regexodus.Matcher;
import regexodus.Replacer;
import regexodus.Pattern;

class Parser
{
    private static final Pattern PATTERN_MARKUP_STRIP;
    private static final Replacer MARKUP_TO_TAG;
    private static final Pattern PATTERN_COLOR_HEX_NO_HASH;
    private static final String[] BOOLEAN_TRUE;
    private static final int INDEX_TOKEN = 1;
    private static final int INDEX_PARAM = 2;
    private static Pattern PATTERN_TOKEN_STRIP;
    private static String RESET_REPLACEMENT;
    
    static String preprocess(final CharSequence text) {
        return Parser.MARKUP_TO_TAG.replace(text).replace("[]", "{RESET}");
    }
    
    static void parseTokens(final TypingLabel label) {
        if (Parser.PATTERN_TOKEN_STRIP == null || TypingConfig.dirtyEffectMaps) {
            Parser.PATTERN_TOKEN_STRIP = compileTokenPattern();
        }
        if (Parser.RESET_REPLACEMENT == null || TypingConfig.dirtyEffectMaps) {
            Parser.RESET_REPLACEMENT = getResetReplacement();
        }
        label.tokenEntries.clear();
        parseReplacements(label);
        parseRegularTokens(label);
        label.setText(label.getIntermediateText().toString(), false, false);
        label.tokenEntries.sort();
    }
    
    private static void parseReplacements(final TypingLabel label) {
        CharSequence text = label.workingLayout.appendIntoDirect(new StringBuilder());
        final StringBuilder sb = new StringBuilder(text.length());
        final Matcher m = Parser.PATTERN_TOKEN_STRIP.matcher(text);
        int matcherIndexOffset = 0;
        while (true) {
            sb.setLength(0);
            m.setTarget(text);
            m.setPosition(matcherIndexOffset);
            if (!m.find()) {
                break;
            }
            final InternalToken internalToken = InternalToken.fromName(m.group(1));
            final String param = m.group(2);
            if (internalToken == null) {
                ++matcherIndexOffset;
            }
            else {
                String replacement = null;
                switch (internalToken) {
                    case COLOR: {
                        replacement = stringToColorMarkup(param);
                        break;
                    }
                    case STYLE:
                    case SIZE: {
                        replacement = stringToStyleMarkup(param);
                        break;
                    }
                    case FONT: {
                        replacement = "[@" + param + ']';
                        break;
                    }
                    case ENDCOLOR:
                    case CLEARCOLOR: {
                        replacement = "[#" + label.getClearColor().toString() + ']';
                        break;
                    }
                    case CLEARSIZE: {
                        replacement = "[%]";
                        break;
                    }
                    case CLEARFONT: {
                        replacement = "[@]";
                        break;
                    }
                    case VAR: {
                        replacement = null;
                        if (label.getTypingListener() != null) {
                            replacement = label.getTypingListener().replaceVariable(param);
                        }
                        if (replacement == null) {
                            replacement = (String)label.getVariables().get(param.toUpperCase());
                        }
                        if (replacement == null) {
                            replacement = (String)TypingConfig.GLOBAL_VARS.get(param.toUpperCase());
                        }
                        if (replacement == null) {
                            replacement = param.toUpperCase();
                            break;
                        }
                        break;
                    }
                    case RESET: {
                        replacement = Parser.RESET_REPLACEMENT + label.getDefaultToken();
                        break;
                    }
                    default: {
                        ++matcherIndexOffset;
                        continue;
                    }
                }
                m.setPosition(m.start());
                text = m.replaceFirst(replacement);
            }
        }
        label.setIntermediateText(text, false, false);
    }
    
    private static void parseRegularTokens(final TypingLabel label) {
        final CharSequence text = Parser.PATTERN_MARKUP_STRIP.matcher((CharSequence)label.getIntermediateText()).replaceAll("");
        CharSequence text2 = label.getIntermediateText();
        final Matcher m = Parser.PATTERN_TOKEN_STRIP.matcher(text);
        final Matcher m2 = Parser.PATTERN_TOKEN_STRIP.matcher(text2);
        int matcherIndexOffset = 0;
        final int m2IndexOffset = 0;
        while (true) {
            m.setTarget(text);
            m2.setTarget(text2);
            m2.setPosition(m2IndexOffset);
            m.setPosition(matcherIndexOffset);
            if (!m.find()) {
                break;
            }
            m2.find();
            final String tokenName = m.group(1).toUpperCase();
            TokenCategory tokenCategory = null;
            final InternalToken tmpToken = InternalToken.fromName(tokenName);
            if (tmpToken == null) {
                if (TypingConfig.EFFECT_START_TOKENS.containsKey(tokenName)) {
                    tokenCategory = TokenCategory.EFFECT_START;
                }
                else if (TypingConfig.EFFECT_END_TOKENS.containsKey(tokenName)) {
                    tokenCategory = TokenCategory.EFFECT_END;
                }
            }
            else {
                tokenCategory = tmpToken.category;
            }
            final int groupCount = m.groupCount();
            final String paramsString = (groupCount == 2) ? m.group(2) : null;
            final String[] params = (paramsString == null) ? new String[0] : paramsString.split(";");
            final String firstParam = (params.length > 0) ? params[0] : null;
            final int index = m.start(0);
            final int indexOffset = 0;
            if (tokenCategory == null) {
                ++matcherIndexOffset;
            }
            else {
                float floatValue = 0.0f;
                String stringValue = null;
                Effect effect = null;
                switch (tokenCategory) {
                    case WAIT: {
                        floatValue = stringToFloat(firstParam, TypingConfig.DEFAULT_WAIT_VALUE);
                        break;
                    }
                    case EVENT: {
                        stringValue = paramsString;
                        break;
                    }
                    case SPEED: {
                        final String s = tokenName;
                        switch (s) {
                            case "SPEED": {
                                final float minModifier = TypingConfig.MIN_SPEED_MODIFIER;
                                final float maxModifier = TypingConfig.MAX_SPEED_MODIFIER;
                                final float modifier = MathUtils.clamp(stringToFloat(firstParam, 1.0f), minModifier, maxModifier);
                                floatValue = TypingConfig.DEFAULT_SPEED_PER_CHAR / modifier;
                                break;
                            }
                            case "SLOWER": {
                                floatValue = TypingConfig.DEFAULT_SPEED_PER_CHAR / 0.5f;
                                break;
                            }
                            case "SLOW": {
                                floatValue = TypingConfig.DEFAULT_SPEED_PER_CHAR / 0.667f;
                                break;
                            }
                            case "NORMAL": {
                                floatValue = TypingConfig.DEFAULT_SPEED_PER_CHAR;
                                break;
                            }
                            case "FAST": {
                                floatValue = TypingConfig.DEFAULT_SPEED_PER_CHAR / 2.0f;
                                break;
                            }
                            case "FASTER": {
                                floatValue = TypingConfig.DEFAULT_SPEED_PER_CHAR / 4.0f;
                                break;
                            }
                            case "NATURAL": {
                                final float minModifier = TypingConfig.MIN_SPEED_MODIFIER;
                                final float maxModifier = TypingConfig.MAX_SPEED_MODIFIER;
                                final float modifier = MathUtils.clamp(stringToFloat(firstParam, 1.0f), minModifier, maxModifier);
                                floatValue = -TypingConfig.DEFAULT_SPEED_PER_CHAR / modifier;
                                break;
                            }
                        }
                        break;
                    }
                    case EFFECT_START: {
                        final Class<? extends Effect> clazz = (Class<? extends Effect>)TypingConfig.EFFECT_START_TOKENS.get(tokenName.toUpperCase());
                        try {
                            if (clazz != null) {
                                final Constructor constructor = ClassReflection.getConstructors((Class)clazz)[0];
                                final int constructorParamCount = constructor.getParameterTypes().length;
                                if (constructorParamCount >= 2) {
                                    effect = (Effect)constructor.newInstance(new Object[] { label, params });
                                }
                                else {
                                    effect = (Effect)constructor.newInstance(new Object[] { label });
                                }
                            }
                        }
                        catch (ReflectionException e) {
                            final String message = "Failed to initialize " + tokenName + " effect token. Make sure the associated class (" + clazz + ") has only one constructor with TypingLabel as first parameter and optionally String[] as second.";
                            throw new IllegalStateException(message, (Throwable)e);
                        }
                        break;
                    }
                }
                final TokenEntry entry = new TokenEntry(tokenName, tokenCategory, index + indexOffset, m.end(0), floatValue, stringValue);
                entry.effect = effect;
                label.tokenEntries.add(entry);
                matcherIndexOffset = m.end();
                m2.setPosition(0);
                text2 = m2.replaceFirst("");
            }
        }
        label.setIntermediateText(text2, false, false);
    }
    
    private static void parseColorMarkups(final TypingLabel label) {
        final CharSequence text = label.getOriginalText();
        final Matcher m = Parser.PATTERN_MARKUP_STRIP.matcher(text);
        while (m.find()) {
            final String tag = m.group(0);
            final int index = m.start(0);
            label.tokenEntries.add(new TokenEntry("SKIP", TokenCategory.SKIP, index, m.end(0), 0.0f, tag));
        }
    }
    
    static float stringToFloat(final String str, final float defaultValue) {
        if (str != null) {
            try {
                return Float.parseFloat(str.replaceAll("[^\\d.\\-+]", ""));
            }
            catch (Exception ex) {}
        }
        return defaultValue;
    }
    
    static boolean stringToBoolean(final String str) {
        if (str != null) {
            for (final String booleanTrue : Parser.BOOLEAN_TRUE) {
                if (booleanTrue.equalsIgnoreCase(str)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    static int stringToColor(final TypingLabel label, String str) {
        if (str != null) {
            final int namedColor = label.getFont().getColorLookup().getRgba(str);
            if (namedColor != 256) {
                return namedColor;
            }
            if (str.length() >= 6) {
                try {
                    if (str.startsWith("#")) {
                        str = str.substring(1);
                    }
                    if (str.length() >= 8) {
                        return Font.intFromHex(str, 0, 8);
                    }
                    if (str.length() >= 6) {
                        return Font.intFromHex(str, 0, 6) << 8 | 0xFF;
                    }
                }
                catch (NumberFormatException ex) {}
            }
        }
        return 256;
    }
    
    private static String stringToColorMarkup(final String str) {
        if (str != null && str.length() >= 6 && !Palette.NAMED.containsKey(str) && Parser.PATTERN_COLOR_HEX_NO_HASH.matches(str)) {
            return "[#" + str + "]";
        }
        return "[" + str + "]";
    }
    
    private static String stringToStyleMarkup(final String str) {
        if (str != null) {
            if (str.equals("*") || str.equalsIgnoreCase("B") || str.equalsIgnoreCase("BOLD") || str.equalsIgnoreCase("STRONG")) {
                return "[*]";
            }
            if (str.equals("/") || str.equalsIgnoreCase("I") || str.equalsIgnoreCase("OBLIQUE") || str.equalsIgnoreCase("ITALIC")) {
                return "[/]";
            }
            if (str.equals("_") || str.equalsIgnoreCase("U") || str.equalsIgnoreCase("UNDER") || str.equalsIgnoreCase("UNDERLINE")) {
                return "[_]";
            }
            if (str.equals("~") || str.equalsIgnoreCase("STRIKE") || str.equalsIgnoreCase("STRIKETHROUGH")) {
                return "[~]";
            }
            if (str.equals(".") || str.equalsIgnoreCase("SUB") || str.equalsIgnoreCase("SUBSCRIPT")) {
                return "[.]";
            }
            if (str.equals("=") || str.equalsIgnoreCase("MID") || str.equalsIgnoreCase("MIDSCRIPT")) {
                return "[=]";
            }
            if (str.equals("^") || str.equalsIgnoreCase("SUPER") || str.equalsIgnoreCase("SUPERSCRIPT")) {
                return "[^]";
            }
            if (str.equals("!") || str.equalsIgnoreCase("UP") || str.equalsIgnoreCase("UPPER")) {
                return "[!]";
            }
            if (str.equals(",") || str.equalsIgnoreCase("LOW") || str.equalsIgnoreCase("LOWER")) {
                return "[,]";
            }
            if (str.equals(";") || str.equalsIgnoreCase("EACH") || str.equalsIgnoreCase("TITLE")) {
                return "[;]";
            }
            if (str.startsWith("@")) {
                return "[@" + str.substring(1) + "]";
            }
            if (str.endsWith("%")) {
                return "[%" + str.substring(0, str.length() - 1) + "]";
            }
            if (str.startsWith("%")) {
                return "[%" + str.substring(1) + "]";
            }
            if (str.length() >= 6 && !Colors.getColors().containsKey(str) && Parser.PATTERN_COLOR_HEX_NO_HASH.matches(str)) {
                return "[#" + str + "]";
            }
        }
        return "[" + str + "]";
    }
    
    private static Pattern compileTokenPattern() {
        final StringBuilder sb = new StringBuilder();
        sb.append("\\{(");
        final Array<String> tokens = (Array<String>)new Array();
        TypingConfig.EFFECT_START_TOKENS.keys().toArray((Array)tokens);
        TypingConfig.EFFECT_END_TOKENS.keys().toArray((Array)tokens);
        for (final InternalToken token : InternalToken.values()) {
            tokens.add(token.name);
        }
        for (int i = 0; i < tokens.size; ++i) {
            sb.append((String)tokens.get(i));
            if (i + 1 < tokens.size) {
                sb.append('|');
            }
        }
        sb.append(")(?:\\=([^\\{\\}]+))?\\}");
        return Pattern.compile(sb.toString(), 1);
    }
    
    private static String getResetReplacement() {
        final Array<String> tokens = (Array<String>)new Array();
        TypingConfig.EFFECT_END_TOKENS.keys().toArray((Array)tokens);
        tokens.add("NORMAL");
        final StringBuilder sb = new StringBuilder("[]");
        for (final String token : tokens) {
            sb.append('{').append(token).append('}');
        }
        TypingConfig.dirtyEffectMaps = false;
        return sb.toString();
    }
    
    static {
        PATTERN_MARKUP_STRIP = Pattern.compile("((?<!\\[)\\[[^\\[\\]]*(\\]|$))");
        MARKUP_TO_TAG = new Replacer(Pattern.compile("(?<!\\[)\\[([^\\[\\]\\+][^\\[\\]]*)(\\]|$)"), "{STYLE=$1}");
        PATTERN_COLOR_HEX_NO_HASH = Pattern.compile("[A-Fa-f0-9]{6,8}");
        BOOLEAN_TRUE = new String[] { "true", "yes", "t", "y", "on", "1" };
    }
}
