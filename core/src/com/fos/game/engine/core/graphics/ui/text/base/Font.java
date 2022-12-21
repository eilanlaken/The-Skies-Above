// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.*;
import com.fos.game.engine.core.graphics.ui.text.utils.BlockUtils;
import com.fos.game.engine.core.graphics.ui.text.utils.CaseInsensitiveIntMap;
import regexodus.Category;

import java.util.Arrays;

public class Font implements Disposable
{
    public IntMap<GlyphRegion> mapping;
    public CaseInsensitiveIntMap nameLookup;
    public IntMap<String> namesByCharCode;
    public GlyphRegion defaultValue;
    public Array<TextureRegion> parents;
    public DistanceFieldType distanceField;
    public boolean isMono;
    public IntIntMap kerning;
    public float actualCrispness;
    public float distanceFieldCrispness;
    public float cellWidth;
    public float cellHeight;
    public float originalCellWidth;
    public float originalCellHeight;
    public float scaleX;
    public float scaleY;
    public float descent;
    public char solidBlock;
    public FontFamily family;
    public ColorLookup colorLookup;
    public boolean integerPosition;
    public String name;
    public Texture whiteBlock;
    public static final long BOLD = 1073741824L;
    public static final long OBLIQUE = 536870912L;
    public static final long UNDERLINE = 268435456L;
    public static final long STRIKETHROUGH = 134217728L;
    public static final long SUBSCRIPT = 33554432L;
    public static final long MIDSCRIPT = 67108864L;
    public static final long SUPERSCRIPT = 100663296L;
    private final float[] vertices;
    private final Layout tempLayout;
    private final LongArray glyphBuffer;
    private final CharArray breakChars;
    private final CharArray spaceChars;
    public static final String vertexShader = "attribute vec4 a_position;\nattribute vec4 a_color;\nattribute vec2 a_texCoord0;\nuniform mat4 u_projTrans;\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\n\nvoid main() {\n\tv_color = a_color;\n\tv_color.a = v_color.a * (255.0/254.0);\n\tv_texCoords = a_texCoord0;\n\tgl_Position =  u_projTrans * a_position;\n}\n";
    public static final String msdfFragmentShader = "#ifdef GL_ES\n\tprecision mediump float;\n\tprecision mediump int;\n#endif\n\nuniform sampler2D u_texture;\nuniform float u_smoothing;\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\n\nvoid main() {\n  vec3 sdf = texture2D(u_texture, v_texCoords).rgb;\n  gl_FragColor = vec4(v_color.rgb, clamp((max(min(sdf.r, sdf.g), min(max(sdf.r, sdf.g), sdf.b)) - 0.5) * u_smoothing + 0.5, 0.0, 1.0) * v_color.a);\n}\n";
    public ShaderProgram shader;
    private static final int[] hexCodes;
    
    public static long longFromHex(final CharSequence cs, final int start, final int end) {
        int lim = 16;
        int len;
        if (cs == null || start < 0 || end <= 0 || end - start <= 0 || (len = cs.length()) - start <= 0 || end > len) {
            return 0L;
        }
        char c = cs.charAt(start);
        int h;
        if (c == '-') {
            len = -1;
            h = 0;
            lim = 17;
        }
        else if (c == '+') {
            len = 1;
            h = 0;
            lim = 17;
        }
        else {
            if (c > 'f' || (h = Font.hexCodes[c]) < 0) {
                return 0L;
            }
            len = 1;
        }
        long data = h;
        for (int i = start + 1; i < end && i < start + lim; ++i) {
            if ((c = cs.charAt(i)) > 'f' || (h = Font.hexCodes[c]) < 0) {
                return data * len;
            }
            data <<= 4;
            data |= h;
        }
        return data * len;
    }
    
    public static int intFromHex(final CharSequence cs, final int start, final int end) {
        int lim = 8;
        int len;
        if (cs == null || start < 0 || end <= 0 || end - start <= 0 || (len = cs.length()) - start <= 0 || end > len) {
            return 0;
        }
        char c = cs.charAt(start);
        int h;
        if (c == '-') {
            len = -1;
            h = 0;
            lim = 9;
        }
        else if (c == '+') {
            len = 1;
            h = 0;
            lim = 9;
        }
        else {
            if (c > 'f' || (h = Font.hexCodes[c]) < 0) {
                return 0;
            }
            len = 1;
        }
        int data = h;
        for (int i = start + 1; i < end && i < start + lim; ++i) {
            if ((c = cs.charAt(i)) > 'f' || (h = Font.hexCodes[c]) < 0) {
                return data * len;
            }
            data <<= 4;
            data |= h;
        }
        return data * len;
    }
    
    public static int intFromDec(final CharSequence cs, final int start, final int end) {
        int lim = 10;
        int len;
        if (cs == null || start < 0 || end <= 0 || end - start <= 0 || (len = cs.length()) - start <= 0 || end > len) {
            return 0;
        }
        char c = cs.charAt(start);
        int h;
        if (c == '-') {
            len = -1;
            lim = 11;
            h = 0;
        }
        else if (c == '+') {
            len = 1;
            lim = 11;
            h = 0;
        }
        else {
            if (c > 'f' || (h = Font.hexCodes[c]) < 0 || h > 9) {
                return 0;
            }
            len = 1;
        }
        int data = h;
        for (int i = start + 1; i < end && i < start + lim; ++i) {
            if ((c = cs.charAt(i)) > 'f' || (h = Font.hexCodes[c]) < 0 || h > 9) {
                return data * len;
            }
            data = data * 10 + h;
        }
        return data * len;
    }
    
    private static int indexAfter(final String text, final String search, int from) {
        return ((from = text.indexOf(search, from)) < 0) ? text.length() : (from + search.length());
    }
    
    public static String safeSubstring(final String source, int beginIndex, int endIndex) {
        if (source == null || source.isEmpty()) {
            return "";
        }
        if (beginIndex < 0) {
            beginIndex = 0;
        }
        if (endIndex < 0 || endIndex > source.length()) {
            endIndex = source.length();
        }
        if (beginIndex >= endIndex) {
            return "";
        }
        return source.substring(beginIndex, endIndex);
    }
    
    public static boolean isLowerCase(final char c) {
        return Category.Ll.contains(c);
    }
    
    public static boolean isUpperCase(final char c) {
        return Category.Lu.contains(c);
    }
    
    public ColorLookup getColorLookup() {
        return this.colorLookup;
    }
    
    public Font setColorLookup(final ColorLookup lookup) {
        if (lookup != null) {
            this.colorLookup = lookup;
        }
        return this;
    }
    
    public Font(final String fntName) {
        this(fntName, DistanceFieldType.STANDARD, 0.0f, 0.0f, 0.0f, 0.0f);
    }
    
    public Font(final String fntName, final DistanceFieldType distanceField) {
        this(fntName, distanceField, 0.0f, 0.0f, 0.0f, 0.0f);
    }
    
    public Font(final String fntName, final String textureName) {
        this(fntName, textureName, DistanceFieldType.STANDARD, 0.0f, 0.0f, 0.0f, 0.0f);
    }
    
    public Font(final String fntName, final String textureName, final DistanceFieldType distanceField) {
        this(fntName, textureName, distanceField, 0.0f, 0.0f, 0.0f, 0.0f);
    }
    
    public Font(final Font toCopy) {
        this.actualCrispness = 1.0f;
        this.distanceFieldCrispness = 1.0f;
        this.cellWidth = 1.0f;
        this.cellHeight = 1.0f;
        this.originalCellWidth = 1.0f;
        this.originalCellHeight = 1.0f;
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.descent = 0.0f;
        this.solidBlock = '\u2588';
        this.colorLookup = ColorLookup.DESCRIPTIVE;
        this.integerPosition = false;
        this.name = "Unnamed Font";
        this.whiteBlock = null;
        this.vertices = new float[20];
        this.tempLayout = (Layout)Layout.POOL.obtain();
        this.glyphBuffer = new LongArray(128);
        this.breakChars = CharArray.with(new char[] { '\t', '\r', ' ', '-', '\u00ad', '\u2000', '\u2001', '\u2002', '\u2003', '\u2004', '\u2005', '\u2006', '\u2008', '\u2009', '\u200a', '\u200b', '\u2010', '\u2012', '\u2013', '\u2014', '\u2027' });
        this.spaceChars = CharArray.with(new char[] { '\t', '\r', ' ', '\u2000', '\u2001', '\u2002', '\u2003', '\u2004', '\u2005', '\u2006', '\u2008', '\u2009', '\u200a', '\u200b' });
        this.shader = null;
        this.distanceField = toCopy.distanceField;
        this.isMono = toCopy.isMono;
        this.actualCrispness = toCopy.actualCrispness;
        this.distanceFieldCrispness = toCopy.distanceFieldCrispness;
        this.parents = (Array<TextureRegion>)new Array((Array)toCopy.parents);
        this.cellWidth = toCopy.cellWidth;
        this.cellHeight = toCopy.cellHeight;
        this.scaleX = toCopy.scaleX;
        this.scaleY = toCopy.scaleY;
        this.originalCellWidth = toCopy.originalCellWidth;
        this.originalCellHeight = toCopy.originalCellHeight;
        this.descent = toCopy.descent;
        this.mapping = (IntMap<GlyphRegion>)new IntMap(toCopy.mapping.size);
        for (final IntMap.Entry<GlyphRegion> e : toCopy.mapping) {
            if (e.value == null) {
                continue;
            }
            this.mapping.put(e.key, new GlyphRegion((GlyphRegion)e.value));
        }
        if (toCopy.nameLookup != null) {
            this.nameLookup = new CaseInsensitiveIntMap(toCopy.nameLookup);
        }
        if (toCopy.namesByCharCode != null) {
            this.namesByCharCode = (IntMap<String>)new IntMap((IntMap)toCopy.namesByCharCode);
        }
        this.defaultValue = toCopy.defaultValue;
        this.kerning = ((toCopy.kerning == null) ? null : new IntIntMap(toCopy.kerning));
        this.solidBlock = toCopy.solidBlock;
        this.name = toCopy.name;
        this.integerPosition = toCopy.integerPosition;
        if (toCopy.family != null) {
            this.family = new FontFamily(toCopy.family);
        }
        if (toCopy.shader != null) {
            this.shader = toCopy.shader;
        }
        if (toCopy.colorLookup != null) {
            this.colorLookup = toCopy.colorLookup;
        }
    }
    
    public Font(final String fntName, final float xAdjust, final float yAdjust, final float widthAdjust, final float heightAdjust) {
        this(fntName, DistanceFieldType.STANDARD, xAdjust, yAdjust, widthAdjust, heightAdjust);
    }
    
    public Font(final String fntName, final DistanceFieldType distanceField, final float xAdjust, final float yAdjust, final float widthAdjust, final float heightAdjust) {
        this(fntName, distanceField, xAdjust, yAdjust, widthAdjust, heightAdjust, false);
    }
    
    public Font(final String fntName, final DistanceFieldType distanceField, final float xAdjust, final float yAdjust, final float widthAdjust, final float heightAdjust, final boolean makeGridGlyphs) {
        this.actualCrispness = 1.0f;
        this.distanceFieldCrispness = 1.0f;
        this.cellWidth = 1.0f;
        this.cellHeight = 1.0f;
        this.originalCellWidth = 1.0f;
        this.originalCellHeight = 1.0f;
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.descent = 0.0f;
        this.solidBlock = '\u2588';
        this.colorLookup = ColorLookup.DESCRIPTIVE;
        this.integerPosition = false;
        this.name = "Unnamed Font";
        this.whiteBlock = null;
        this.vertices = new float[20];
        this.tempLayout = (Layout)Layout.POOL.obtain();
        this.glyphBuffer = new LongArray(128);
        this.breakChars = CharArray.with(new char[] { '\t', '\r', ' ', '-', '\u00ad', '\u2000', '\u2001', '\u2002', '\u2003', '\u2004', '\u2005', '\u2006', '\u2008', '\u2009', '\u200a', '\u200b', '\u2010', '\u2012', '\u2013', '\u2014', '\u2027' });
        this.spaceChars = CharArray.with(new char[] { '\t', '\r', ' ', '\u2000', '\u2001', '\u2002', '\u2003', '\u2004', '\u2005', '\u2006', '\u2008', '\u2009', '\u200a', '\u200b' });
        this.shader = null;
        this.distanceField = distanceField;
        if (distanceField == DistanceFieldType.MSDF) {
            this.shader = new ShaderProgram("attribute vec4 a_position;\nattribute vec4 a_color;\nattribute vec2 a_texCoord0;\nuniform mat4 u_projTrans;\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\n\nvoid main() {\n\tv_color = a_color;\n\tv_color.a = v_color.a * (255.0/254.0);\n\tv_texCoords = a_texCoord0;\n\tgl_Position =  u_projTrans * a_position;\n}\n", "#ifdef GL_ES\n\tprecision mediump float;\n\tprecision mediump int;\n#endif\n\nuniform sampler2D u_texture;\nuniform float u_smoothing;\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\n\nvoid main() {\n  vec3 sdf = texture2D(u_texture, v_texCoords).rgb;\n  gl_FragColor = vec4(v_color.rgb, clamp((max(min(sdf.r, sdf.g), min(max(sdf.r, sdf.g), sdf.b)) - 0.5) * u_smoothing + 0.5, 0.0, 1.0) * v_color.a);\n}\n");
            if (!this.shader.isCompiled()) {
                Gdx.app.error("textratypist", "MSDF shader failed to compile: " + this.shader.getLog());
            }
        }
        else if (distanceField == DistanceFieldType.SDF) {
            this.shader = DistanceFieldFont.createDistanceFieldShader();
            if (!this.shader.isCompiled()) {
                Gdx.app.error("textratypist", "SDF shader failed to compile: " + this.shader.getLog());
            }
        }
        this.loadFNT(fntName, xAdjust, yAdjust, widthAdjust, heightAdjust, makeGridGlyphs);
    }
    
    public Font(final String fntName, final String textureName, final float xAdjust, final float yAdjust, final float widthAdjust, final float heightAdjust) {
        this(fntName, textureName, DistanceFieldType.STANDARD, xAdjust, yAdjust, widthAdjust, heightAdjust);
    }
    
    public Font(final String fntName, final String textureName, final DistanceFieldType distanceField, final float xAdjust, final float yAdjust, final float widthAdjust, final float heightAdjust) {
        this(fntName, textureName, distanceField, xAdjust, yAdjust, widthAdjust, heightAdjust, false);
    }
    
    public Font(final String fntName, final String textureName, final DistanceFieldType distanceField, final float xAdjust, final float yAdjust, final float widthAdjust, final float heightAdjust, final boolean makeGridGlyphs) {
        this.actualCrispness = 1.0f;
        this.distanceFieldCrispness = 1.0f;
        this.cellWidth = 1.0f;
        this.cellHeight = 1.0f;
        this.originalCellWidth = 1.0f;
        this.originalCellHeight = 1.0f;
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.descent = 0.0f;
        this.solidBlock = '\u2588';
        this.colorLookup = ColorLookup.DESCRIPTIVE;
        this.integerPosition = false;
        this.name = "Unnamed Font";
        this.whiteBlock = null;
        this.vertices = new float[20];
        this.tempLayout = (Layout)Layout.POOL.obtain();
        this.glyphBuffer = new LongArray(128);
        this.breakChars = CharArray.with(new char[] { '\t', '\r', ' ', '-', '\u00ad', '\u2000', '\u2001', '\u2002', '\u2003', '\u2004', '\u2005', '\u2006', '\u2008', '\u2009', '\u200a', '\u200b', '\u2010', '\u2012', '\u2013', '\u2014', '\u2027' });
        this.spaceChars = CharArray.with(new char[] { '\t', '\r', ' ', '\u2000', '\u2001', '\u2002', '\u2003', '\u2004', '\u2005', '\u2006', '\u2008', '\u2009', '\u200a', '\u200b' });
        this.shader = null;
        this.distanceField = distanceField;
        if (distanceField == DistanceFieldType.MSDF) {
            this.shader = new ShaderProgram("attribute vec4 a_position;\nattribute vec4 a_color;\nattribute vec2 a_texCoord0;\nuniform mat4 u_projTrans;\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\n\nvoid main() {\n\tv_color = a_color;\n\tv_color.a = v_color.a * (255.0/254.0);\n\tv_texCoords = a_texCoord0;\n\tgl_Position =  u_projTrans * a_position;\n}\n", "#ifdef GL_ES\n\tprecision mediump float;\n\tprecision mediump int;\n#endif\n\nuniform sampler2D u_texture;\nuniform float u_smoothing;\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\n\nvoid main() {\n  vec3 sdf = texture2D(u_texture, v_texCoords).rgb;\n  gl_FragColor = vec4(v_color.rgb, clamp((max(min(sdf.r, sdf.g), min(max(sdf.r, sdf.g), sdf.b)) - 0.5) * u_smoothing + 0.5, 0.0, 1.0) * v_color.a);\n}\n");
            if (!this.shader.isCompiled()) {
                Gdx.app.error("textratypist", "MSDF shader failed to compile: " + this.shader.getLog());
            }
        }
        else if (distanceField == DistanceFieldType.SDF) {
            this.shader = DistanceFieldFont.createDistanceFieldShader();
            if (!this.shader.isCompiled()) {
                Gdx.app.error("textratypist", "SDF shader failed to compile: " + this.shader.getLog());
            }
        }
        FileHandle textureHandle;
        if ((textureHandle = Gdx.files.internal(textureName)).exists() || (textureHandle = Gdx.files.local(textureName)).exists()) {
            this.parents = (Array<TextureRegion>)Array.with(new TextureRegion[] { new TextureRegion(new Texture(textureHandle)) });
            if (distanceField == DistanceFieldType.SDF || distanceField == DistanceFieldType.MSDF) {
                ((TextureRegion)this.parents.first()).getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            }
            this.loadFNT(fntName, xAdjust, yAdjust, widthAdjust, heightAdjust, makeGridGlyphs);
            return;
        }
        throw new RuntimeException("Missing texture file: " + textureName);
    }
    
    public Font(final String fntName, final TextureRegion textureRegion, final float xAdjust, final float yAdjust, final float widthAdjust, final float heightAdjust) {
        this(fntName, textureRegion, DistanceFieldType.STANDARD, xAdjust, yAdjust, widthAdjust, heightAdjust);
    }
    
    public Font(final String fntName, final TextureRegion textureRegion, final DistanceFieldType distanceField, final float xAdjust, final float yAdjust, final float widthAdjust, final float heightAdjust) {
        this(fntName, textureRegion, distanceField, xAdjust, yAdjust, widthAdjust, heightAdjust, false);
    }
    
    public Font(final String fntName, final TextureRegion textureRegion, final DistanceFieldType distanceField, final float xAdjust, final float yAdjust, final float widthAdjust, final float heightAdjust, final boolean makeGridGlyphs) {
        this.actualCrispness = 1.0f;
        this.distanceFieldCrispness = 1.0f;
        this.cellWidth = 1.0f;
        this.cellHeight = 1.0f;
        this.originalCellWidth = 1.0f;
        this.originalCellHeight = 1.0f;
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.descent = 0.0f;
        this.solidBlock = '\u2588';
        this.colorLookup = ColorLookup.DESCRIPTIVE;
        this.integerPosition = false;
        this.name = "Unnamed Font";
        this.whiteBlock = null;
        this.vertices = new float[20];
        this.tempLayout = (Layout)Layout.POOL.obtain();
        this.glyphBuffer = new LongArray(128);
        this.breakChars = CharArray.with(new char[] { '\t', '\r', ' ', '-', '\u00ad', '\u2000', '\u2001', '\u2002', '\u2003', '\u2004', '\u2005', '\u2006', '\u2008', '\u2009', '\u200a', '\u200b', '\u2010', '\u2012', '\u2013', '\u2014', '\u2027' });
        this.spaceChars = CharArray.with(new char[] { '\t', '\r', ' ', '\u2000', '\u2001', '\u2002', '\u2003', '\u2004', '\u2005', '\u2006', '\u2008', '\u2009', '\u200a', '\u200b' });
        this.shader = null;
        this.distanceField = distanceField;
        if (distanceField == DistanceFieldType.MSDF) {
            this.shader = new ShaderProgram("attribute vec4 a_position;\nattribute vec4 a_color;\nattribute vec2 a_texCoord0;\nuniform mat4 u_projTrans;\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\n\nvoid main() {\n\tv_color = a_color;\n\tv_color.a = v_color.a * (255.0/254.0);\n\tv_texCoords = a_texCoord0;\n\tgl_Position =  u_projTrans * a_position;\n}\n", "#ifdef GL_ES\n\tprecision mediump float;\n\tprecision mediump int;\n#endif\n\nuniform sampler2D u_texture;\nuniform float u_smoothing;\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\n\nvoid main() {\n  vec3 sdf = texture2D(u_texture, v_texCoords).rgb;\n  gl_FragColor = vec4(v_color.rgb, clamp((max(min(sdf.r, sdf.g), min(max(sdf.r, sdf.g), sdf.b)) - 0.5) * u_smoothing + 0.5, 0.0, 1.0) * v_color.a);\n}\n");
            if (!this.shader.isCompiled()) {
                Gdx.app.error("textratypist", "MSDF shader failed to compile: " + this.shader.getLog());
            }
        }
        else if (distanceField == DistanceFieldType.SDF) {
            this.shader = DistanceFieldFont.createDistanceFieldShader();
            if (!this.shader.isCompiled()) {
                Gdx.app.error("textratypist", "SDF shader failed to compile: " + this.shader.getLog());
            }
        }
        this.parents = (Array<TextureRegion>)Array.with(new TextureRegion[] { textureRegion });
        if (distanceField == DistanceFieldType.SDF || distanceField == DistanceFieldType.MSDF) {
            textureRegion.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        this.loadFNT(fntName, xAdjust, yAdjust, widthAdjust, heightAdjust, makeGridGlyphs);
    }
    
    public Font(final String fntName, final Array<TextureRegion> textureRegions, final float xAdjust, final float yAdjust, final float widthAdjust, final float heightAdjust) {
        this(fntName, textureRegions, DistanceFieldType.STANDARD, xAdjust, yAdjust, widthAdjust, heightAdjust);
    }
    
    public Font(final String fntName, final Array<TextureRegion> textureRegions, final DistanceFieldType distanceField, final float xAdjust, final float yAdjust, final float widthAdjust, final float heightAdjust) {
        this(fntName, textureRegions, distanceField, xAdjust, yAdjust, widthAdjust, heightAdjust, false);
    }
    
    public Font(final String fntName, final Array<TextureRegion> textureRegions, final DistanceFieldType distanceField, final float xAdjust, final float yAdjust, final float widthAdjust, final float heightAdjust, final boolean makeGridGlyphs) {
        this.actualCrispness = 1.0f;
        this.distanceFieldCrispness = 1.0f;
        this.cellWidth = 1.0f;
        this.cellHeight = 1.0f;
        this.originalCellWidth = 1.0f;
        this.originalCellHeight = 1.0f;
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.descent = 0.0f;
        this.solidBlock = '\u2588';
        this.colorLookup = ColorLookup.DESCRIPTIVE;
        this.integerPosition = false;
        this.name = "Unnamed Font";
        this.whiteBlock = null;
        this.vertices = new float[20];
        this.tempLayout = (Layout)Layout.POOL.obtain();
        this.glyphBuffer = new LongArray(128);
        this.breakChars = CharArray.with(new char[] { '\t', '\r', ' ', '-', '\u00ad', '\u2000', '\u2001', '\u2002', '\u2003', '\u2004', '\u2005', '\u2006', '\u2008', '\u2009', '\u200a', '\u200b', '\u2010', '\u2012', '\u2013', '\u2014', '\u2027' });
        this.spaceChars = CharArray.with(new char[] { '\t', '\r', ' ', '\u2000', '\u2001', '\u2002', '\u2003', '\u2004', '\u2005', '\u2006', '\u2008', '\u2009', '\u200a', '\u200b' });
        this.shader = null;
        this.distanceField = distanceField;
        if (distanceField == DistanceFieldType.MSDF) {
            this.shader = new ShaderProgram("attribute vec4 a_position;\nattribute vec4 a_color;\nattribute vec2 a_texCoord0;\nuniform mat4 u_projTrans;\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\n\nvoid main() {\n\tv_color = a_color;\n\tv_color.a = v_color.a * (255.0/254.0);\n\tv_texCoords = a_texCoord0;\n\tgl_Position =  u_projTrans * a_position;\n}\n", "#ifdef GL_ES\n\tprecision mediump float;\n\tprecision mediump int;\n#endif\n\nuniform sampler2D u_texture;\nuniform float u_smoothing;\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\n\nvoid main() {\n  vec3 sdf = texture2D(u_texture, v_texCoords).rgb;\n  gl_FragColor = vec4(v_color.rgb, clamp((max(min(sdf.r, sdf.g), min(max(sdf.r, sdf.g), sdf.b)) - 0.5) * u_smoothing + 0.5, 0.0, 1.0) * v_color.a);\n}\n");
            if (!this.shader.isCompiled()) {
                Gdx.app.error("textratypist", "MSDF shader failed to compile: " + this.shader.getLog());
            }
        }
        else if (distanceField == DistanceFieldType.SDF) {
            this.shader = DistanceFieldFont.createDistanceFieldShader();
            if (!this.shader.isCompiled()) {
                Gdx.app.error("textratypist", "SDF shader failed to compile: " + this.shader.getLog());
            }
        }
        this.parents = textureRegions;
        if ((distanceField == DistanceFieldType.SDF || distanceField == DistanceFieldType.MSDF) && textureRegions != null) {
            for (final TextureRegion parent : textureRegions) {
                parent.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            }
        }
        this.loadFNT(fntName, xAdjust, yAdjust, widthAdjust, heightAdjust, makeGridGlyphs);
    }
    
    public Font(final BitmapFont bmFont) {
        this(bmFont, DistanceFieldType.STANDARD, 0.0f, 0.0f, 0.0f, 0.0f, false);
    }
    
    public Font(final BitmapFont bmFont, final float xAdjust, final float yAdjust, final float widthAdjust, final float heightAdjust) {
        this(bmFont, DistanceFieldType.STANDARD, xAdjust, yAdjust, widthAdjust, heightAdjust, false);
    }
    
    public Font(final BitmapFont bmFont, final DistanceFieldType distanceField, final float xAdjust, final float yAdjust, final float widthAdjust, final float heightAdjust) {
        this(bmFont, distanceField, xAdjust, yAdjust, widthAdjust, heightAdjust, false);
    }
    
    public Font(final BitmapFont bmFont, final DistanceFieldType distanceField, final float xAdjust, float yAdjust, final float widthAdjust, final float heightAdjust, final boolean makeGridGlyphs) {
        this.actualCrispness = 1.0f;
        this.distanceFieldCrispness = 1.0f;
        this.cellWidth = 1.0f;
        this.cellHeight = 1.0f;
        this.originalCellWidth = 1.0f;
        this.originalCellHeight = 1.0f;
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.descent = 0.0f;
        this.solidBlock = '\u2588';
        this.colorLookup = ColorLookup.DESCRIPTIVE;
        this.integerPosition = false;
        this.name = "Unnamed Font";
        this.whiteBlock = null;
        this.vertices = new float[20];
        this.tempLayout = (Layout)Layout.POOL.obtain();
        this.glyphBuffer = new LongArray(128);
        this.breakChars = CharArray.with(new char[] { '\t', '\r', ' ', '-', '\u00ad', '\u2000', '\u2001', '\u2002', '\u2003', '\u2004', '\u2005', '\u2006', '\u2008', '\u2009', '\u200a', '\u200b', '\u2010', '\u2012', '\u2013', '\u2014', '\u2027' });
        this.spaceChars = CharArray.with(new char[] { '\t', '\r', ' ', '\u2000', '\u2001', '\u2002', '\u2003', '\u2004', '\u2005', '\u2006', '\u2008', '\u2009', '\u200a', '\u200b' });
        this.shader = null;
        this.distanceField = distanceField;
        if (distanceField == DistanceFieldType.MSDF) {
            this.shader = new ShaderProgram("attribute vec4 a_position;\nattribute vec4 a_color;\nattribute vec2 a_texCoord0;\nuniform mat4 u_projTrans;\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\n\nvoid main() {\n\tv_color = a_color;\n\tv_color.a = v_color.a * (255.0/254.0);\n\tv_texCoords = a_texCoord0;\n\tgl_Position =  u_projTrans * a_position;\n}\n", "#ifdef GL_ES\n\tprecision mediump float;\n\tprecision mediump int;\n#endif\n\nuniform sampler2D u_texture;\nuniform float u_smoothing;\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\n\nvoid main() {\n  vec3 sdf = texture2D(u_texture, v_texCoords).rgb;\n  gl_FragColor = vec4(v_color.rgb, clamp((max(min(sdf.r, sdf.g), min(max(sdf.r, sdf.g), sdf.b)) - 0.5) * u_smoothing + 0.5, 0.0, 1.0) * v_color.a);\n}\n");
            if (!this.shader.isCompiled()) {
                Gdx.app.error("textratypist", "MSDF shader failed to compile: " + this.shader.getLog());
            }
        }
        else if (distanceField == DistanceFieldType.SDF) {
            this.shader = DistanceFieldFont.createDistanceFieldShader();
            if (!this.shader.isCompiled()) {
                Gdx.app.error("textratypist", "SDF shader failed to compile: " + this.shader.getLog());
            }
        }
        this.parents = (Array<TextureRegion>)bmFont.getRegions();
        if ((distanceField == DistanceFieldType.SDF || distanceField == DistanceFieldType.MSDF) && this.parents != null) {
            for (final TextureRegion parent : this.parents) {
                parent.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            }
        }
        final BitmapFont.BitmapFontData data = bmFont.getData();
        this.mapping = (IntMap<GlyphRegion>)new IntMap(128);
        int minWidth = Integer.MAX_VALUE;
        this.descent = bmFont.getDescent();
        yAdjust += this.descent;
        for (final BitmapFont.Glyph[] page : data.glyphs) {
            if (page != null) {
                for (final BitmapFont.Glyph glyph : page) {
                    if (glyph != null) {
                        final int x = glyph.srcX;
                        final int y = glyph.srcY;
                        final int w = glyph.width;
                        int h = glyph.height;
                        int a = glyph.xadvance;
                        a += (int)widthAdjust;
                        h += (int)heightAdjust;
                        minWidth = Math.min(minWidth, a);
                        this.cellWidth = Math.max((float)a, this.cellWidth);
                        this.cellHeight = Math.max((float)h, this.cellHeight);
                        final GlyphRegion gr = new GlyphRegion(bmFont.getRegion(glyph.page), x, y, w, h);
                        if (glyph.id == 10) {
                            a = 0;
                            gr.offsetX = 0.0f;
                        }
                        else if (makeGridGlyphs && BlockUtils.isBlockGlyph(glyph.id)) {
                            gr.offsetX = Float.NaN;
                        }
                        else {
                            gr.offsetX = glyph.xoffset + xAdjust;
                        }
                        gr.offsetY = -h - glyph.yoffset + yAdjust;
                        gr.xAdvance = (float)a;
                        this.mapping.put(glyph.id & 0xFFFF, gr);
                        if (glyph.kerning != null) {
                            if (this.kerning == null) {
                                this.kerning = new IntIntMap(128);
                            }
                            for (int b = 0; b < glyph.kerning.length; ++b) {
                                final byte[] kern = glyph.kerning[b];
                                if (kern != null) {
                                    for (int i = 0; i < 512; ++i) {
                                        final int k = kern[i];
                                        if (k != 0) {
                                            this.kerning.put(glyph.id << 16 | (b << 9 | i), k);
                                        }
                                        if ((b << 9 | i) == 0x5B) {
                                            this.kerning.put(glyph.id << 16 | 0x2, k);
                                        }
                                    }
                                }
                            }
                        }
                        if ((glyph.id & 0xFFFF) == 0x5B) {
                            this.mapping.put(2, gr);
                            if (glyph.kerning != null) {
                                for (int b = 0; b < glyph.kerning.length; ++b) {
                                    final byte[] kern = glyph.kerning[b];
                                    if (kern != null) {
                                        for (int i = 0; i < 512; ++i) {
                                            final int k = kern[i];
                                            if (k != 0) {
                                                this.kerning.put(0x20000 | (b << 9 | i), k);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (this.mapping.containsKey(10)) {
            final GlyphRegion gr2 = (GlyphRegion)this.mapping.get(10);
            gr2.setRegionWidth(0);
            gr2.setRegionHeight(0);
        }
        if (this.mapping.containsKey(32)) {
            this.mapping.put(13, this.mapping.get(32));
        }
        this.solidBlock = (this.mapping.containsKey(9608) ? '\u2588' : '\uffff');
        if (makeGridGlyphs) {
            GlyphRegion block = (GlyphRegion)this.mapping.get((int)this.solidBlock, null);
            if (block == null) {
                final Pixmap temp = new Pixmap(3, 3, Pixmap.Format.RGBA8888);
                temp.setColor(Color.WHITE);
                temp.fill();
                (this.whiteBlock = new Texture(3, 3, Pixmap.Format.RGBA8888)).draw(temp, 0, 0);
                this.solidBlock = '\u2588';
                this.mapping.put((int)this.solidBlock, block = new GlyphRegion(new TextureRegion(this.whiteBlock, 1, 1, 1, 1)));
                temp.dispose();
            }
            for (int j = 9472; j < 9472 + BlockUtils.BOX_DRAWING.length; ++j) {
                final GlyphRegion gr3 = new GlyphRegion(block);
                gr3.offsetX = Float.NaN;
                gr3.xAdvance = this.cellWidth;
                gr3.offsetY = this.cellHeight;
                this.mapping.put(j, gr3);
            }
        }
        else if (!this.mapping.containsKey((int)this.solidBlock)) {
            final Pixmap temp2 = new Pixmap(3, 3, Pixmap.Format.RGBA8888);
            temp2.setColor(Color.WHITE);
            temp2.fill();
            (this.whiteBlock = new Texture(3, 3, Pixmap.Format.RGBA8888)).draw(temp2, 0, 0);
            this.solidBlock = '\u2588';
            this.mapping.put((int)this.solidBlock, new GlyphRegion(new TextureRegion(this.whiteBlock, 1, 1, 1, 1)));
            temp2.dispose();
        }
        this.defaultValue = (GlyphRegion)this.mapping.get((data.missingGlyph == null) ? 32 : data.missingGlyph.id, this.mapping.get(32, this.mapping.values().next()));
        this.originalCellWidth = this.cellWidth;
        this.originalCellHeight = this.cellHeight;
        this.isMono = (minWidth == this.cellWidth && this.kerning == null);
        this.integerPosition = bmFont.usesIntegerPositions();
        this.scale(bmFont.getScaleX(), bmFont.getScaleY());
    }
    
    public Font(final String prefix, final String fntName, final boolean ignoredSadConsoleFlag) {
        this.actualCrispness = 1.0f;
        this.distanceFieldCrispness = 1.0f;
        this.cellWidth = 1.0f;
        this.cellHeight = 1.0f;
        this.originalCellWidth = 1.0f;
        this.originalCellHeight = 1.0f;
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.descent = 0.0f;
        this.solidBlock = '\u2588';
        this.colorLookup = ColorLookup.DESCRIPTIVE;
        this.integerPosition = false;
        this.name = "Unnamed Font";
        this.whiteBlock = null;
        this.vertices = new float[20];
        this.tempLayout = (Layout)Layout.POOL.obtain();
        this.glyphBuffer = new LongArray(128);
        this.breakChars = CharArray.with(new char[] { '\t', '\r', ' ', '-', '\u00ad', '\u2000', '\u2001', '\u2002', '\u2003', '\u2004', '\u2005', '\u2006', '\u2008', '\u2009', '\u200a', '\u200b', '\u2010', '\u2012', '\u2013', '\u2014', '\u2027' });
        this.spaceChars = CharArray.with(new char[] { '\t', '\r', ' ', '\u2000', '\u2001', '\u2002', '\u2003', '\u2004', '\u2005', '\u2006', '\u2008', '\u2009', '\u200a', '\u200b' });
        this.shader = null;
        this.distanceField = DistanceFieldType.STANDARD;
        this.loadSad((prefix == null) ? "" : prefix, fntName);
    }
    
    protected void loadFNT(final String fntName, final float xAdjust, float yAdjust, final float widthAdjust, final float heightAdjust, final boolean makeGridGlyphs) {
        FileHandle fntHandle;
        if ((fntHandle = Gdx.files.internal(fntName)).exists() || (fntHandle = Gdx.files.local(fntName)).exists()) {
            final String fnt = fntHandle.readString("UTF8");
            int idx = indexAfter(fnt, "lineHeight=", 0);
            final int rawLineHeight = intFromDec(fnt, idx, idx = indexAfter(fnt, "base=", idx));
            final int baseline = intFromDec(fnt, idx, idx = indexAfter(fnt, "pages=", idx));
            this.descent = (float)(baseline - rawLineHeight);
            if (this.distanceField == DistanceFieldType.STANDARD) {
                yAdjust += this.descent;
            }
            final int pages = intFromDec(fnt, idx, idx = indexAfter(fnt, "\npage id=", idx));
            if (this.parents == null || this.parents.size < pages) {
                if (this.parents == null) {
                    this.parents = (Array<TextureRegion>)new Array(true, pages, (Class)TextureRegion.class);
                }
                else {
                    this.parents.clear();
                }
                for (int i = 0; i < pages; ++i) {
                    final String s = fnt;
                    idx = indexAfter(fnt, "file=\"", idx);
                    final String textureName = s.substring(idx, idx = fnt.indexOf(34, idx));
                    FileHandle textureHandle;
                    if (!(textureHandle = Gdx.files.internal(textureName)).exists() && !(textureHandle = Gdx.files.local(textureName)).exists()) {
                        throw new RuntimeException("Missing texture file: " + textureName);
                    }
                    this.parents.add(new TextureRegion(new Texture(textureHandle)));
                    if (this.distanceField == DistanceFieldType.SDF || this.distanceField == DistanceFieldType.MSDF) {
                        ((TextureRegion)this.parents.peek()).getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                    }
                }
            }
            final String cs = fnt;
            idx = indexAfter(fnt, "\nchars count=", idx);
            final int size = intFromDec(cs, idx, idx = indexAfter(fnt, "\nchar id=", idx));
            this.mapping = (IntMap<GlyphRegion>)new IntMap(size);
            int minWidth = Integer.MAX_VALUE;
            for (int j = 0; j < size && idx != fnt.length(); ++j) {
                final int c = intFromDec(fnt, idx, idx = indexAfter(fnt, " x=", idx));
                final int x = intFromDec(fnt, idx, idx = indexAfter(fnt, " y=", idx));
                final int y = intFromDec(fnt, idx, idx = indexAfter(fnt, " width=", idx));
                final int w = intFromDec(fnt, idx, idx = indexAfter(fnt, " height=", idx));
                int h = intFromDec(fnt, idx, idx = indexAfter(fnt, " xoffset=", idx));
                final int xo = intFromDec(fnt, idx, idx = indexAfter(fnt, " yoffset=", idx));
                final int yo = intFromDec(fnt, idx, idx = indexAfter(fnt, " xadvance=", idx));
                int a = intFromDec(fnt, idx, idx = indexAfter(fnt, " page=", idx));
                final int p = intFromDec(fnt, idx, idx = indexAfter(fnt, "\nchar id=", idx));
                a += (int)widthAdjust;
                h += (int)heightAdjust;
                if (c != 9608) {
                    minWidth = Math.min(minWidth, a);
                }
                final GlyphRegion gr = new GlyphRegion((TextureRegion)this.parents.get(p), x, y, w, h);
                if (c == 10) {
                    a = 0;
                    gr.offsetX = 0.0f;
                }
                else if (makeGridGlyphs && BlockUtils.isBlockGlyph(c)) {
                    gr.offsetX = Float.NaN;
                }
                else {
                    gr.offsetX = xo + xAdjust;
                }
                gr.offsetY = yo + yAdjust;
                gr.xAdvance = (float)a;
                this.cellWidth = Math.max((float)a, this.cellWidth);
                this.cellHeight = Math.max((float)h, this.cellHeight);
                this.mapping.put(c, gr);
                if (c == 91) {
                    this.mapping.put(2, gr);
                }
            }
            idx = indexAfter(fnt, "\nkernings count=", 0);
            if (idx < fnt.length()) {
                final int kernings = intFromDec(fnt, idx, idx = indexAfter(fnt, "\nkerning first=", idx));
                this.kerning = new IntIntMap(kernings);
                for (int k = 0; k < kernings; ++k) {
                    final int first = intFromDec(fnt, idx, idx = indexAfter(fnt, " second=", idx));
                    final int second = intFromDec(fnt, idx, idx = indexAfter(fnt, " amount=", idx));
                    final int amount = intFromDec(fnt, idx, idx = indexAfter(fnt, "\nkerning first=", idx));
                    this.kerning.put(first << 16 | second, amount);
                    if (first == 91) {
                        this.kerning.put(0x20000 | second, amount);
                    }
                    if (second == 91) {
                        this.kerning.put(first << 16 | 0x2, amount);
                    }
                }
            }
            if (this.mapping.containsKey(10)) {
                final GlyphRegion gr2 = (GlyphRegion)this.mapping.get(10);
                gr2.setRegionWidth(0);
                gr2.setRegionHeight(0);
                gr2.xAdvance = 0.0f;
            }
            if (this.mapping.containsKey(32)) {
                this.mapping.put(13, this.mapping.get(32));
            }
            this.solidBlock = (this.mapping.containsKey(9608) ? '\u2588' : '\uffff');
            if (makeGridGlyphs) {
                GlyphRegion block = (GlyphRegion)this.mapping.get((int)this.solidBlock, null);
                if (block == null) {
                    final Pixmap temp = new Pixmap(3, 3, Pixmap.Format.RGBA8888);
                    temp.setColor(Color.WHITE);
                    temp.fill();
                    (this.whiteBlock = new Texture(3, 3, Pixmap.Format.RGBA8888)).draw(temp, 0, 0);
                    this.solidBlock = '\u2588';
                    this.mapping.put((int)this.solidBlock, block = new GlyphRegion(new TextureRegion(this.whiteBlock, 1, 1, 1, 1)));
                    temp.dispose();
                }
                for (int k = 9472; k < 9472 + BlockUtils.BOX_DRAWING.length; ++k) {
                    final GlyphRegion gr3 = new GlyphRegion(block);
                    gr3.offsetX = Float.NaN;
                    gr3.xAdvance = this.cellWidth;
                    gr3.offsetY = this.cellHeight;
                    this.mapping.put(k, gr3);
                }
            }
            else if (!this.mapping.containsKey((int)this.solidBlock)) {
                final Pixmap temp2 = new Pixmap(3, 3, Pixmap.Format.RGBA8888);
                temp2.setColor(Color.WHITE);
                temp2.fill();
                (this.whiteBlock = new Texture(3, 3, Pixmap.Format.RGBA8888)).draw(temp2, 0, 0);
                this.solidBlock = '\u2588';
                this.mapping.put((int)this.solidBlock, new GlyphRegion(new TextureRegion(this.whiteBlock, 1, 1, 1, 1)));
                temp2.dispose();
            }
            this.defaultValue = (GlyphRegion)this.mapping.get(32, this.mapping.get(0));
            this.originalCellWidth = this.cellWidth;
            this.originalCellHeight = this.cellHeight;
            this.isMono = (minWidth == this.cellWidth && this.kerning == null);
            return;
        }
        throw new RuntimeException("Missing font file: " + fntName);
    }
    
    protected void loadSad(final String prefix, final String fntName) {
        final JsonReader reader = new JsonReader();
        FileHandle fntHandle;
        if ((fntHandle = Gdx.files.internal(prefix + fntName)).exists() || (fntHandle = Gdx.files.local(prefix + fntName)).exists()) {
            final JsonValue fnt = reader.parse(fntHandle);
            final int pages = 1;
            TextureRegion parent;
            if (this.parents == null || this.parents.size == 0) {
                if (this.parents == null) {
                    this.parents = (Array<TextureRegion>)new Array(true, pages, (Class)TextureRegion.class);
                }
                final String textureName = fnt.getString("FilePath");
                FileHandle textureHandle;
                if (!(textureHandle = Gdx.files.internal(prefix + textureName)).exists() && !(textureHandle = Gdx.files.local(prefix + textureName)).exists()) {
                    throw new RuntimeException("Missing texture file: " + prefix + textureName);
                }
                this.parents.add(parent = new TextureRegion(new Texture(textureHandle)));
            }
            else {
                parent = (TextureRegion)this.parents.first();
            }
            final int columns = fnt.getInt("Columns");
            final int padding = fnt.getInt("GlyphPadding");
            this.cellHeight = (float)fnt.getInt("GlyphHeight");
            this.cellWidth = (float)fnt.getInt("GlyphWidth");
            final int rows = (parent.getRegionHeight() - padding) / ((int)this.cellHeight + padding);
            final int size = rows * columns;
            this.mapping = (IntMap<GlyphRegion>)new IntMap(size + 1);
            int y = 0;
            int c = 0;
            while (y < rows) {
                for (int x = 0; x < columns; ++x, ++c) {
                    final GlyphRegion gr = new GlyphRegion(parent, x * ((int)this.cellWidth + padding) + padding, y * ((int)this.cellHeight + padding) + padding, (int)this.cellWidth, (int)this.cellHeight);
                    gr.offsetX = 0.0f;
                    gr.offsetY = 0.0f;
                    if (c == 10) {
                        gr.xAdvance = 0.0f;
                    }
                    else {
                        gr.xAdvance = this.cellWidth;
                    }
                    this.mapping.put(c, gr);
                    if (c == 91) {
                        if (this.mapping.containsKey(2)) {
                            this.mapping.put(size, this.mapping.get(2));
                        }
                        this.mapping.put(2, gr);
                    }
                }
                ++y;
            }
            this.solidBlock = (char)fnt.getInt("SolidGlyphIndex");
            if (this.mapping.containsKey(10)) {
                final GlyphRegion gr2 = (GlyphRegion)this.mapping.get(10);
                gr2.setRegionWidth(0);
                gr2.setRegionHeight(0);
                gr2.xAdvance = 0.0f;
            }
            if (this.mapping.containsKey(32)) {
                this.mapping.put(13, this.mapping.get(32));
            }
            this.defaultValue = (GlyphRegion)this.mapping.get(32, this.mapping.get(0));
            this.originalCellWidth = this.cellWidth;
            this.originalCellHeight = this.cellHeight;
            this.isMono = true;
            return;
        }
        throw new RuntimeException("Missing font file: " + prefix + fntName);
    }
    
    public int kerningPair(final char first, final char second) {
        return first << 16 | (second & '\uffff');
    }
    
    public Font scale(final float horizontal, final float vertical) {
        this.scaleX *= horizontal;
        this.scaleY *= vertical;
        this.cellWidth *= horizontal;
        this.cellHeight *= vertical;
        return this;
    }
    
    public Font scaleTo(final float width, final float height) {
        this.scaleX = width / this.originalCellWidth;
        this.scaleY = height / this.originalCellHeight;
        this.cellWidth = width;
        this.cellHeight = height;
        return this;
    }
    
    public Font adjustLineHeight(final float multiplier) {
        this.cellHeight *= multiplier;
        this.originalCellHeight *= multiplier;
        return this;
    }
    
    public Font adjustCellWidth(final float multiplier) {
        this.cellWidth *= multiplier;
        this.originalCellWidth *= multiplier;
        return this;
    }
    
    public Font fitCell(final float width, final float height, final boolean center) {
        this.cellWidth = width;
        this.cellHeight = height;
        final float wsx = width / this.scaleX;
        final IntMap.Values<GlyphRegion> vs = (IntMap.Values<GlyphRegion>)this.mapping.values();
        if (center) {
            while (vs.hasNext) {
                final GlyphRegion glyphRegion;
                final GlyphRegion g = glyphRegion = (GlyphRegion)vs.next();
                glyphRegion.offsetX += (wsx - g.xAdvance) * 0.5f;
                g.xAdvance = wsx;
            }
        }
        else {
            while (vs.hasNext) {
                ((GlyphRegion)vs.next()).xAdvance = wsx;
            }
        }
        this.isMono = true;
        this.kerning = null;
        return this;
    }
    
    public Font setTextureFilter() {
        return this.setTextureFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }
    
    public Font setTextureFilter(final Texture.TextureFilter minFilter, final Texture.TextureFilter magFilter) {
        for (final TextureRegion parent : this.parents) {
            parent.getTexture().setFilter(minFilter, magFilter);
        }
        return this;
    }
    
    public Font useIntegerPositions(final boolean integer) {
        this.integerPosition = integer;
        return this;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Font setName(final String name) {
        this.name = name;
        return this;
    }
    
    public float getCrispness() {
        return this.distanceFieldCrispness;
    }
    
    public Font setCrispness(final float crispness) {
        this.distanceFieldCrispness = crispness;
        return this;
    }
    
    public Font multiplyCrispness(final float multiplier) {
        this.distanceFieldCrispness *= multiplier;
        return this;
    }
    
    public Font addImage(final String character, final TextureRegion region, final float offsetX, final float offsetY, final float xAdvance) {
        if (character != null && !character.isEmpty()) {
            this.mapping.put((int)character.charAt(character.length() - 1), new GlyphRegion(region, offsetX, offsetY, xAdvance));
        }
        return this;
    }
    
    public Font addImage(final String character, final TextureRegion region) {
        if (character != null && !character.isEmpty()) {
            this.mapping.put((int)character.charAt(character.length() - 1), new GlyphRegion(region));
        }
        return this;
    }
    
    public Font addAtlas(final TextureAtlas atlas) {
        final Array<TextureAtlas.AtlasRegion> regions = (Array<TextureAtlas.AtlasRegion>)atlas.getRegions();
        if (this.nameLookup == null) {
            this.nameLookup = new CaseInsensitiveIntMap(regions.size, 0.5f);
        }
        else {
            this.nameLookup.ensureCapacity(regions.size);
        }
        if (this.namesByCharCode == null) {
            this.namesByCharCode = (IntMap<String>)new IntMap(regions.size >> 1, 0.5f);
        }
        else {
            this.namesByCharCode.ensureCapacity(regions.size >> 1);
        }
        TextureAtlas.AtlasRegion previous = (TextureAtlas.AtlasRegion)regions.first();
        GlyphRegion gr = new GlyphRegion(previous);
        this.mapping.put(57344, gr);
        this.nameLookup.put(previous.name, 57344);
        this.namesByCharCode.put(57344, previous.name);
        for (int i = 57344, a = 1; i < 63488 && a < regions.size; ++a) {
            final TextureAtlas.AtlasRegion region = (TextureAtlas.AtlasRegion)regions.get(a);
            if (previous.getRegionX() == region.getRegionX() && previous.getRegionY() == region.getRegionY()) {
                this.nameLookup.put(region.name, i);
                final char f = previous.name.charAt(0);
                if (f < '\ud800' || f >= '\ue000') {
                    this.namesByCharCode.put(i, region.name);
                }
            }
            else {
                ++i;
                previous = region;
                gr = new GlyphRegion(region);
                this.mapping.put(i, gr);
                this.nameLookup.put(region.name, i);
                this.namesByCharCode.put(i, region.name);
            }
        }
        return this;
    }
    
    public int atlasLookup(final String name) {
        if (this.nameLookup == null) {
            return -1;
        }
        return this.nameLookup.get(name, -1);
    }
    
    public void enableShader(final Batch batch) {
        if (this.distanceField == DistanceFieldType.MSDF) {
            if (batch.getShader() != this.shader) {
                batch.setShader(this.shader);
                this.shader.setUniformf("u_smoothing", 7.0f * this.actualCrispness * Math.max(this.cellHeight / this.originalCellHeight, this.cellWidth / this.originalCellWidth));
            }
        }
        else if (this.distanceField == DistanceFieldType.SDF) {
            if (batch.getShader() != this.shader) {
                batch.setShader(this.shader);
                final float scale = Math.max(this.cellHeight / this.originalCellHeight, this.cellWidth / this.originalCellWidth) * 0.5f + 0.125f;
                this.shader.setUniformf("u_smoothing", this.actualCrispness / scale);
            }
        }
        else {
            batch.setShader((ShaderProgram)null);
        }
        batch.setPackedColor(Color.WHITE_FLOAT_BITS);
    }
    
    public void drawText(final Batch batch, final CharSequence text, final float x, final float y) {
        this.drawText(batch, text, x, y, -2);
    }
    
    public void drawText(final Batch batch, final CharSequence text, float x, final float y, final int color) {
        batch.setPackedColor(NumberUtils.intToFloatColor(Integer.reverseBytes(color)));
        for (int i = 0, n = text.length(); i < n; ++i) {
            final GlyphRegion current;
            batch.draw((TextureRegion)(current = (GlyphRegion)this.mapping.get((int)text.charAt(i))), x + current.offsetX * this.scaleX, y + current.offsetY * this.scaleY, current.getRegionWidth() * this.scaleX, current.getRegionHeight() * this.scaleY);
            x += current.getRegionWidth() * this.scaleX;
        }
    }
    
    public void drawBlocks(final Batch batch, final int[][] colors, final float x, final float y) {
        this.drawBlocks(batch, this.solidBlock, colors, x, y);
    }
    
    public void drawBlocks(final Batch batch, final char blockChar, final int[][] colors, float x, float y) {
        final TextureRegion block = (TextureRegion)this.mapping.get((int)blockChar);
        if (block == null) {
            return;
        }
        final Texture parent = block.getTexture();
        final float ipw = 1.0f / parent.getWidth();
        final float iph = 1.0f / parent.getHeight();
        final float u = block.getU();
        final float v = block.getV();
        final float u2 = block.getU() + ipw;
        final float v2 = block.getV() + iph;
        x += 0.00390625f;
        y += 0.00390625f;
        this.vertices[0] = x;
        this.vertices[1] = y;
        this.vertices[3] = u;
        this.vertices[4] = v;
        this.vertices[5] = x;
        this.vertices[6] = y + this.cellHeight;
        this.vertices[8] = u;
        this.vertices[9] = v2;
        this.vertices[10] = x + this.cellWidth;
        this.vertices[11] = y + this.cellHeight;
        this.vertices[13] = u2;
        this.vertices[14] = v2;
        this.vertices[15] = x + this.cellWidth;
        this.vertices[16] = y;
        this.vertices[18] = u2;
        this.vertices[19] = v;
        int xi = 0;
        final int xn = colors.length;
        final int yn = colors[0].length;
        while (xi < xn) {
            for (int yi = 0; yi < yn; ++yi) {
                if ((colors[xi][yi] & 0xFE) != 0x0) {
                    final float[] vertices = this.vertices;
                    final int n = 2;
                    final float[] vertices2 = this.vertices;
                    final int n2 = 7;
                    final float[] vertices3 = this.vertices;
                    final int n3 = 12;
                    final float[] vertices4 = this.vertices;
                    final int n4 = 17;
                    final float intBitsToFloat = NumberUtils.intBitsToFloat(Integer.reverseBytes(colors[xi][yi] & 0xFFFFFFFE));
                    vertices3[n3] = (vertices4[n4] = intBitsToFloat);
                    vertices[n] = (vertices2[n2] = intBitsToFloat);
                    batch.draw(parent, this.vertices, 0, 20);
                }
                final float[] vertices5 = this.vertices;
                final int n5 = 1;
                final float[] vertices6 = this.vertices;
                final int n6 = 16;
                vertices5[n5] = (vertices6[n6] += this.cellHeight);
                final float[] vertices7 = this.vertices;
                final int n7 = 6;
                final float[] vertices8 = this.vertices;
                final int n8 = 11;
                vertices7[n7] = (vertices8[n8] += this.cellHeight);
            }
            final float[] vertices9 = this.vertices;
            final int n9 = 0;
            final float[] vertices10 = this.vertices;
            final int n10 = 5;
            vertices9[n9] = (vertices10[n10] += this.cellWidth);
            final float[] vertices11 = this.vertices;
            final int n11 = 10;
            final float[] vertices12 = this.vertices;
            final int n12 = 15;
            vertices11[n11] = (vertices12[n12] += this.cellWidth);
            this.vertices[1] = (this.vertices[16] = y);
            this.vertices[6] = (this.vertices[11] = y + this.cellHeight);
            ++xi;
        }
    }
    
    protected void drawBlockSequence(final Batch batch, final float[] sequence, final TextureRegion block, final float color, final float x, final float y) {
        this.drawBlockSequence(batch, sequence, block, color, x, y, this.cellWidth, this.cellHeight);
    }
    
    protected void drawBlockSequence(final Batch batch, final float[] sequence, final TextureRegion block, final float color, final float x, final float y, final float width, final float height) {
        final Texture parent = block.getTexture();
        final float ipw = 1.0f / parent.getWidth();
        final float iph = 1.0f / parent.getHeight();
        final float u = block.getU();
        final float v = block.getV();
        final float u2 = u + ipw;
        final float v2 = v - iph;
        for (int b = 0; b < sequence.length; b += 4) {
            final float startX = x + sequence[b] * width;
            final float startY = y + sequence[b + 1] * height;
            final float sizeX = sequence[b + 2] * width;
            final float sizeY = sequence[b + 3] * height;
            this.vertices[0] = startX;
            this.vertices[1] = startY;
            this.vertices[2] = color;
            this.vertices[3] = u;
            this.vertices[4] = v;
            this.vertices[5] = startX;
            this.vertices[6] = startY + sizeY;
            this.vertices[7] = color;
            this.vertices[8] = u;
            this.vertices[9] = v2;
            this.vertices[10] = startX + sizeX;
            this.vertices[11] = startY + sizeY;
            this.vertices[12] = color;
            this.vertices[13] = u2;
            this.vertices[14] = v2;
            this.vertices[15] = startX + sizeX;
            this.vertices[16] = startY;
            this.vertices[17] = color;
            this.vertices[18] = u2;
            this.vertices[19] = v;
            batch.draw(parent, this.vertices, 0, 20);
        }
    }
    
    protected void drawBlockSequence(final Batch batch, final float[] sequence, final TextureRegion block, final float color, final float x, final float y, final float width, final float height, final float rotation) {
        final Texture parent = block.getTexture();
        final float ipw = 1.0f / parent.getWidth();
        final float iph = 1.0f / parent.getHeight();
        final float u = block.getU();
        final float v = block.getV();
        final float u2 = u + ipw;
        final float v2 = v - iph;
        final float sn = MathUtils.sinDeg(rotation);
        final float cs = MathUtils.cosDeg(rotation);
        final float xc = 0.0f;
        final float yt = 0.0f;
        for (int b = 0; b < sequence.length; b += 4) {
            final float startX = sequence[b] * width;
            final float startY = sequence[b + 1] * height;
            final float sizeX = sequence[b + 2] * width;
            final float sizeY = sequence[b + 3] * height;
            final float p0x = xc + startX;
            final float p0y = yt + startY + sizeY;
            final float p1x = xc + startX;
            final float p1y = yt + startY;
            final float p2x = xc + startX + sizeX;
            final float p2y = yt + startY;
            final float[] vertices = this.vertices;
            final int n = 15;
            final float[] vertices2 = this.vertices;
            final int n2 = 0;
            final float n3 = x + cs * p0x - sn * p0y;
            vertices2[n2] = n3;
            final float[] vertices3 = this.vertices;
            final int n4 = 5;
            final float n5 = x + cs * p1x - sn * p1y;
            vertices3[n4] = n5;
            vertices[n] = n3 - n5 + (this.vertices[10] = x + cs * p2x - sn * p2y);
            final float[] vertices4 = this.vertices;
            final int n6 = 16;
            final float[] vertices5 = this.vertices;
            final int n7 = 1;
            final float n8 = y + sn * p0x + cs * p0y;
            vertices5[n7] = n8;
            final float[] vertices6 = this.vertices;
            final int n9 = 6;
            final float n10 = y + sn * p1x + cs * p1y;
            vertices6[n9] = n10;
            vertices4[n6] = n8 - n10 + (this.vertices[11] = y + sn * p2x + cs * p2y);
            this.vertices[2] = color;
            this.vertices[3] = u;
            this.vertices[4] = v;
            this.vertices[7] = color;
            this.vertices[8] = u;
            this.vertices[9] = v2;
            this.vertices[12] = color;
            this.vertices[13] = u2;
            this.vertices[14] = v2;
            this.vertices[17] = color;
            this.vertices[18] = u2;
            this.vertices[19] = v;
            batch.draw(parent, this.vertices, 0, 20);
        }
    }
    
    public int drawMarkupText(final Batch batch, final String text, float x, float y) {
        final Layout layout = this.tempLayout;
        layout.clear();
        this.markup(text, this.tempLayout);
        final int lines = layout.lines();
        int drawn = 0;
        for (int ln = 0; ln < lines; ++ln) {
            final Line line = layout.getLine(ln);
            final int n = line.glyphs.size;
            drawn += n;
            if (this.kerning != null) {
                int kern = -1;
                for (int i = 0; i < n; ++i) {
                    final long glyph;
                    kern = (kern << 16 | (int)((glyph = line.glyphs.get(i)) & 0xFFFFL));
                    final int amt = this.kerning.get(kern, 0);
                    x += this.drawGlyph(batch, glyph, x + amt, y) + amt;
                }
            }
            else {
                for (int j = 0; j < n; ++j) {
                    x += this.drawGlyph(batch, line.glyphs.get(j), x, y);
                }
            }
            y -= this.cellHeight;
        }
        return drawn;
    }
    
    public float drawGlyphs(final Batch batch, final Layout glyphs, final float x, final float y) {
        return this.drawGlyphs(batch, glyphs, x, y, 8);
    }
    
    public float drawGlyphs(final Batch batch, final Layout glyphs, final float x, float y, final int align) {
        float drawn = 0.0f;
        for (int lines = glyphs.lines(), ln = 0; ln < lines; ++ln) {
            final Line l = glyphs.getLine(ln);
            y -= l.height;
            drawn += this.drawGlyphs(batch, l, x, y, align);
        }
        return drawn;
    }
    
    public float drawGlyphs(final Batch batch, final Layout glyphs, float x, float y, final int align, final float rotation, final float originX, final float originY) {
        float drawn = 0.0f;
        final float sn = MathUtils.sinDeg(rotation);
        final float cs = MathUtils.cosDeg(rotation);
        final int lines = glyphs.lines();
        x -= sn * 0.5f * this.cellHeight;
        y += cs * 0.5f * this.cellHeight;
        x += cs * 0.5f * this.cellWidth;
        y += sn * 0.5f * this.cellWidth;
        for (int ln = 0; ln < lines; ++ln) {
            final Line l = glyphs.getLine(ln);
            y -= cs * l.height;
            x += sn * l.height;
            drawn += this.drawGlyphs(batch, l, x, y, align, rotation, originX, originY);
        }
        return drawn;
    }
    
    public float drawGlyphs(final Batch batch, final Line glyphs, final float x, final float y) {
        if (glyphs == null) {
            return 0.0f;
        }
        return this.drawGlyphs(batch, glyphs, x, y, 8);
    }
    
    public float drawGlyphs(final Batch batch, final Line glyphs, final float x, final float y, final int align) {
        final float originX = Align.isRight(align) ? glyphs.width : (Align.isCenterHorizontal(align) ? (glyphs.width * 0.5f) : 0.0f);
        final float originY = Align.isTop(align) ? glyphs.height : (Align.isCenterVertical(align) ? (glyphs.height * 0.5f) : 0.0f);
        return this.drawGlyphs(batch, glyphs, x, y, align, 0.0f, originX, originY);
    }
    
    public float drawGlyphs(final Batch batch, final Line glyphs, float x, float y, final int align, final float rotation, final float originX, final float originY) {
        if (glyphs == null || glyphs.glyphs.size == 0) {
            return 0.0f;
        }
        float drawn = 0.0f;
        final float cs = MathUtils.cosDeg(rotation);
        final float sn = MathUtils.sinDeg(rotation);
        final float worldOriginX = x + originX;
        final float worldOriginY = y + originY;
        final float fx = -originX;
        final float fy = -originY;
        x = cs * fx - sn * fy + worldOriginX;
        y = sn * fx + cs * fy + worldOriginY;
        if (Align.isCenterHorizontal(align)) {
            x -= cs * (glyphs.width * 0.5f);
            y -= sn * (glyphs.width * 0.5f);
        }
        else if (Align.isRight(align)) {
            x -= cs * glyphs.width;
            y -= sn * glyphs.width;
        }
        int kern = -1;
        float xChange = 0.0f;
        float yChange = 0.0f;
        boolean curly = false;
        boolean initial = true;
        for (int i = 0, n = glyphs.glyphs.size; i < n; ++i) {
            final long glyph = glyphs.glyphs.get(i);
            final char ch = (char)glyph;
            if (curly) {
                if (ch == '}') {
                    curly = false;
                    continue;
                }
                if (ch != '{') {
                    continue;
                }
                curly = false;
            }
            else if (ch == '{') {
                curly = true;
                continue;
            }
            Font font = null;
            if (this.family != null) {
                font = this.family.connected[(int)(glyph >>> 16 & 0xFL)];
            }
            if (font == null) {
                font = this;
            }
            if (font.kerning != null) {
                kern = (kern << 16 | (int)(glyph & 0xFFFFL));
                final float amt = font.kerning.get(kern, 0) * font.scaleX * (glyph + 4194304L >>> 20 & 0xFL) * 0.25f;
                xChange += cs * amt;
                yChange += sn * amt;
            }
            if (initial) {
                final float ox = ((GlyphRegion)font.mapping.get((int)(glyph & 0xFFFFL), font.defaultValue)).offsetX * font.scaleX * (glyph + 4194304L >>> 20 & 0xFL) * 0.25f;
                if (ox < 0.0f) {
                    xChange -= cs * ox;
                    yChange -= sn * ox;
                }
                initial = false;
            }
            final float single = this.drawGlyph(batch, glyph, x + xChange, y + yChange, rotation);
            xChange += cs * single;
            yChange += sn * single;
            drawn += single;
        }
        return drawn;
    }
    
    public static float xAdvance(final Font font, final float scale, final long glyph) {
        if (glyph >>> 32 == 0L) {
            return 0.0f;
        }
        final GlyphRegion tr = (GlyphRegion)font.mapping.get((int)(char)glyph);
        if (tr == null) {
            return 0.0f;
        }
        float changedW = tr.xAdvance * scale;
        if (!font.isMono && (glyph & 0x6000000L) != 0x0L) {
            changedW *= 0.5f;
        }
        return changedW;
    }
    
    public float xAdvance(final long glyph) {
        if (glyph >>> 32 == 0L) {
            return 0.0f;
        }
        final GlyphRegion tr = (GlyphRegion)this.mapping.get((int)(char)glyph);
        if (tr == null) {
            return 0.0f;
        }
        float scale;
        if ((char)glyph >= '\ue000' && (char)glyph < '\uf800') {
            scale = ((glyph + 3145728L >>> 20 & 0xFL) + 1L) * 0.25f * this.cellHeight / (tr.xAdvance * 1.25f);
        }
        else {
            scale = this.scaleX * ((glyph + 3145728L >>> 20 & 0xFL) + 1L) * 0.25f;
        }
        float changedW = tr.xAdvance * scale;
        if (!this.isMono) {
            changedW += tr.offsetX * scale;
            if ((glyph & 0x6000000L) != 0x0L) {
                changedW *= 0.5f;
            }
        }
        return changedW;
    }
    
    public float measureWidth(final Line line) {
        float drawn = 0.0f;
        final LongArray glyphs = line.glyphs;
        boolean curly = false;
        boolean initial = true;
        int kern = -1;
        for (int i = 0, n = glyphs.size; i < n; ++i) {
            final long glyph = glyphs.get(i);
            final char ch = (char)glyph;
            if (curly) {
                if (ch == '}') {
                    curly = false;
                    continue;
                }
                if (ch != '{') {
                    continue;
                }
                curly = false;
            }
            else if (ch == '{') {
                curly = true;
                continue;
            }
            Font font = null;
            if (this.family != null) {
                font = this.family.connected[(int)(glyph >>> 16 & 0xFL)];
            }
            if (font == null) {
                font = this;
            }
            final GlyphRegion tr = (GlyphRegion)font.mapping.get((int)ch);
            if (tr != null) {
                if (font.kerning != null) {
                    kern = (kern << 16 | ch);
                    final float scale = ((glyph + 3145728L >>> 20 & 0xFL) + 1L) * 0.25f;
                    float scaleX;
                    if ((char)glyph >= '\ue000' && (char)glyph < '\uf800') {
                        scaleX = scale * font.cellHeight / (tr.xAdvance * 1.25f);
                    }
                    else {
                        scaleX = font.scaleX * scale * (1.0f + 0.5f * (-(glyph & 0x6000000L) >> 63));
                    }
                    final float amt = font.kerning.get(kern, 0) * scaleX;
                    float changedW = tr.xAdvance * scaleX;
                    if (initial) {
                        final float ox = ((GlyphRegion)font.mapping.get((int)(glyph & 0xFFFFL), font.defaultValue)).offsetX * scaleX;
                        if (ox < 0.0f) {
                            changedW -= ox;
                        }
                        initial = false;
                    }
                    drawn += changedW + amt;
                }
                else {
                    final float scale = ((glyph + 3145728L >>> 20 & 0xFL) + 1L) * 0.25f;
                    float scaleX;
                    if ((char)glyph >= '\ue000' && (char)glyph < '\uf800') {
                        scaleX = scale * font.cellHeight / (tr.xAdvance * 1.25f);
                    }
                    else {
                        scaleX = font.scaleX * scale * (((glyph & 0x6000000L) != 0x0L && !font.isMono) ? 0.5f : 1.0f);
                    }
                    float changedW = tr.xAdvance * scaleX;
                    if (font.isMono) {
                        changedW += tr.offsetX * scaleX;
                    }
                    else if (initial) {
                        final float ox = ((GlyphRegion)font.mapping.get((int)(glyph & 0xFFFFL), font.defaultValue)).offsetX * scaleX;
                        if (ox < 0.0f) {
                            changedW -= ox;
                        }
                        initial = false;
                    }
                    drawn += changedW;
                }
            }
        }
        return drawn;
    }
    
    public float calculateSize(final Line line) {
        float drawn = 0.0f;
        final LongArray glyphs = line.glyphs;
        boolean curly = false;
        boolean initial = true;
        int kern = -1;
        line.height = 0.0f;
        for (int i = 0, n = glyphs.size; i < n; ++i) {
            final long glyph = glyphs.get(i);
            final char ch = (char)glyph;
            if (curly) {
                if (ch == '}') {
                    curly = false;
                    continue;
                }
                if (ch != '{') {
                    continue;
                }
                curly = false;
            }
            else if (ch == '{') {
                curly = true;
                continue;
            }
            Font font = null;
            if (this.family != null) {
                font = this.family.connected[(int)(glyph >>> 16 & 0xFL)];
            }
            if (font == null) {
                font = this;
            }
            final GlyphRegion tr = (GlyphRegion)font.mapping.get((int)ch);
            if (tr != null) {
                if (font.kerning != null) {
                    kern = (kern << 16 | ch);
                    final float scale = ((glyph + 3145728L >>> 20 & 0xFL) + 1L) * 0.25f;
                    float scaleX;
                    if ((char)glyph >= '\ue000' && (char)glyph < '\uf800') {
                        scaleX = scale * font.cellHeight / (tr.xAdvance * 1.25f);
                    }
                    else {
                        scaleX = font.scaleX * scale * (1.0f + 0.5f * (-(glyph & 0x6000000L) >> 63));
                    }
                    line.height = Math.max(line.height, font.cellHeight * scale);
                    final float amt = font.kerning.get(kern, 0) * scaleX;
                    float changedW = xAdvance(font, scaleX, glyph);
                    if (initial) {
                        final float ox = ((GlyphRegion)font.mapping.get((int)(glyph & 0xFFFFL), font.defaultValue)).offsetX * scaleX;
                        if (ox < 0.0f) {
                            changedW -= ox;
                        }
                        initial = false;
                    }
                    drawn += changedW + amt;
                }
                else {
                    final float scale = ((glyph + 3145728L >>> 20 & 0xFL) + 1L) * 0.25f;
                    line.height = Math.max(line.height, font.cellHeight * scale);
                    float scaleX;
                    if ((char)glyph >= '\ue000' && (char)glyph < '\uf800') {
                        scaleX = scale * font.cellHeight / (tr.xAdvance * 1.25f);
                    }
                    else {
                        scaleX = font.scaleX * scale * (((glyph & 0x6000000L) != 0x0L && !font.isMono) ? 0.5f : 1.0f);
                    }
                    float changedW = xAdvance(font, scaleX, glyph);
                    if (font.isMono) {
                        changedW += tr.offsetX * scaleX;
                    }
                    else if (initial) {
                        final float ox = ((GlyphRegion)font.mapping.get((int)(glyph & 0xFFFFL), font.defaultValue)).offsetX * scaleX;
                        if (ox < 0.0f) {
                            changedW -= ox;
                        }
                        initial = false;
                    }
                    drawn += changedW;
                }
            }
        }
        return line.width = drawn;
    }
    
    public float calculateSize(final Layout layout) {
        float w = 0.0f;
        float currentHeight = 0.0f;
        for (int ln = 0; ln < layout.lines(); ++ln) {
            float drawn = 0.0f;
            final Line line = layout.getLine(ln);
            final LongArray glyphs = line.glyphs;
            boolean curly = false;
            boolean initial = true;
            int kern = -1;
            line.height = currentHeight;
            for (int i = 0, n = glyphs.size; i < n; ++i) {
                final long glyph = glyphs.get(i);
                final char ch = (char)glyph;
                if (curly) {
                    if (ch == '}') {
                        curly = false;
                        continue;
                    }
                    if (ch != '{') {
                        continue;
                    }
                    curly = false;
                }
                else if (ch == '{') {
                    curly = true;
                    continue;
                }
                Font font = null;
                if (this.family != null) {
                    font = this.family.connected[(int)(glyph >>> 16 & 0xFL)];
                }
                if (font == null) {
                    font = this;
                }
                final GlyphRegion tr = (GlyphRegion)font.mapping.get((int)ch);
                if (tr != null) {
                    if (font.kerning != null) {
                        kern = (kern << 16 | ch);
                        final float scale = ((glyph + 3145728L >>> 20 & 0xFL) + 1L) * 0.25f;
                        float scaleX;
                        if ((char)glyph >= '\ue000' && (char)glyph < '\uf800') {
                            scaleX = scale * font.cellHeight / (tr.xAdvance * 1.25f);
                        }
                        else {
                            scaleX = font.scaleX * scale * (1.0f + 0.5f * (-(glyph & 0x6000000L) >> 63));
                        }
                        line.height = Math.max(line.height, currentHeight = font.cellHeight * scale);
                        final float amt = font.kerning.get(kern, 0) * scaleX;
                        float changedW = tr.xAdvance * scaleX;
                        if (initial) {
                            final float ox = ((GlyphRegion)font.mapping.get((int)(glyph & 0xFFFFL), font.defaultValue)).offsetX * scaleX;
                            if (ox < 0.0f) {
                                changedW -= ox;
                            }
                            initial = false;
                        }
                        drawn += changedW + amt;
                    }
                    else {
                        final float scale = ((glyph + 3145728L >>> 20 & 0xFL) + 1L) * 0.25f;
                        line.height = Math.max(line.height, currentHeight = font.cellHeight * scale);
                        float scaleX;
                        if ((char)glyph >= '\ue000' && (char)glyph < '\uf800') {
                            scaleX = scale * font.cellHeight / (tr.xAdvance * 1.25f);
                        }
                        else {
                            scaleX = font.scaleX * scale * (((glyph & 0x6000000L) != 0x0L && !font.isMono) ? 0.5f : 1.0f);
                        }
                        float changedW = tr.xAdvance * scaleX;
                        if (font.isMono) {
                            changedW += tr.offsetX * scaleX;
                        }
                        else if (initial) {
                            final float ox = ((GlyphRegion)font.mapping.get((int)(glyph & 0xFFFFL), font.defaultValue)).offsetX * scaleX;
                            if (ox < 0.0f) {
                                changedW -= ox;
                            }
                            initial = false;
                        }
                        drawn += changedW;
                    }
                }
            }
            line.width = drawn;
            w = Math.max(w, drawn);
        }
        return w;
    }
    
    public float calculateXAdvances(final Line line, final FloatArray advances) {
        advances.clear();
        final LongArray glyphs = line.glyphs;
        advances.ensureCapacity(line.glyphs.size + 1);
        boolean curly = false;
        boolean initial = true;
        int kern = -1;
        float total = 0.0f;
        line.height = 0.0f;
        for (int i = 0, n = glyphs.size; i < n; ++i) {
            final long glyph = glyphs.get(i);
            final char ch = (char)glyph;
            if (curly) {
                if (ch == '}') {
                    curly = false;
                    advances.add(0.0f);
                    continue;
                }
                if (ch != '{') {
                    advances.add(0.0f);
                    continue;
                }
                curly = false;
            }
            else if (ch == '{') {
                curly = true;
                advances.add(0.0f);
                continue;
            }
            Font font = null;
            if (this.family != null) {
                font = this.family.connected[(int)(glyph >>> 16 & 0xFL)];
            }
            if (font == null) {
                font = this;
            }
            final GlyphRegion tr = (GlyphRegion)font.mapping.get((int)ch);
            if (tr == null) {
                advances.add(0.0f);
            }
            else if (font.kerning != null) {
                kern = (kern << 16 | ch);
                final float scale = ((glyph + 3145728L >>> 20 & 0xFL) + 1L) * 0.25f;
                float scaleX;
                if ((char)glyph >= '\ue000' && (char)glyph < '\uf800') {
                    scaleX = scale * font.cellHeight / (tr.xAdvance * 1.25f);
                }
                else {
                    scaleX = font.scaleX * scale * (1.0f + 0.5f * (-(glyph & 0x6000000L) >> 63));
                }
                line.height = Math.max(line.height, font.cellHeight * scale);
                final float amt = font.kerning.get(kern, 0) * scaleX;
                float changedW = xAdvance(font, scaleX, glyph);
                if (initial) {
                    final float ox = ((GlyphRegion)font.mapping.get((int)(glyph & 0xFFFFL), font.defaultValue)).offsetX * scaleX;
                    if (ox < 0.0f) {
                        changedW -= ox;
                    }
                    initial = false;
                }
                advances.add(total);
                total += changedW + amt;
            }
            else {
                final float scale = ((glyph + 3145728L >>> 20 & 0xFL) + 1L) * 0.25f;
                line.height = Math.max(line.height, font.cellHeight * scale);
                float scaleX;
                if ((char)glyph >= '\ue000' && (char)glyph < '\uf800') {
                    scaleX = scale * font.cellHeight / (tr.xAdvance * 1.25f);
                }
                else {
                    scaleX = font.scaleX * scale * (((glyph & 0x6000000L) != 0x0L && !font.isMono) ? 0.5f : 1.0f);
                }
                float changedW = xAdvance(font, scaleX, glyph);
                if (font.isMono) {
                    changedW += tr.offsetX * scaleX;
                }
                else if (initial) {
                    final float ox = ((GlyphRegion)font.mapping.get((int)(glyph & 0xFFFFL), font.defaultValue)).offsetX * scaleX;
                    if (ox < 0.0f) {
                        changedW -= ox;
                    }
                    initial = false;
                }
                advances.add(total);
                total += changedW;
            }
        }
        return total;
    }
    
    protected float handleIntegerPosition(final float p) {
        return this.integerPosition ? ((float)MathUtils.round(p)) : p;
    }
    
    public float drawGlyph(final Batch batch, final long glyph, final float x, final float y) {
        return this.drawGlyph(batch, glyph, x, y, 0.0f, 1.0f, 1.0f, 0);
    }
    
    public float drawGlyph(final Batch batch, final long glyph, final float x, final float y, final float rotation) {
        return this.drawGlyph(batch, glyph, x, y, rotation, 1.0f, 1.0f, 0);
    }
    
    public float drawGlyph(final Batch batch, final long glyph, final float x, final float y, final float rotation, final float sizingX, final float sizingY) {
        return this.drawGlyph(batch, glyph, x, y, rotation, sizingX, sizingY, 0);
    }
    
    public float drawGlyph(final Batch batch, final long glyph, float x, float y, final float rotation, final float sizingX, final float sizingY, final int backgroundColor) {
        final float sin = MathUtils.sinDeg(rotation);
        final float cos = MathUtils.cosDeg(rotation);
        Font font = null;
        if (this.family != null) {
            font = this.family.connected[(int)(glyph >>> 16 & 0xFL)];
        }
        if (font == null) {
            font = this;
        }
        final char c;
        final GlyphRegion tr = (GlyphRegion)font.mapping.get((int)(c = (char)glyph));
        if (tr == null) {
            return 0.0f;
        }
        final float color = NumberUtils.intBitsToFloat((int)(batch.getColor().a * (glyph >>> 33 & 0x7FL)) << 25 | (int)(batch.getColor().r * (glyph >>> 56)) | (int)(batch.getColor().g * (glyph >>> 48 & 0xFFL)) << 8 | (int)(batch.getColor().b * (glyph >>> 40 & 0xFFL)) << 16);
        final float scale = ((glyph + 3145728L >>> 20 & 0xFL) + 1L) * 0.25f;
        float scaleX;
        float scaleY;
        if (c >= '\ue000' && c < '\uf800') {
            scaleY = (scaleX = scale * font.cellHeight / (tr.xAdvance * 1.25f));
        }
        else {
            scaleX = font.scaleX * scale;
            scaleY = font.scaleY * scale;
        }
        float centerX = font.cellWidth * scaleX * 0.5f;
        float centerY = font.cellHeight * scaleY * 0.5f;
        float atlasOffX = 0.0f;
        float atlasOffY = 0.0f;
        if (c >= '\ue000' && c < '\uf800') {
            atlasOffX = -font.cellWidth * 0.25f;
            atlasOffY = -font.cellHeight * 0.25f;
        }
        final float xShift = x - (x = font.handleIntegerPosition(x));
        final float yShift = y - (y = font.handleIntegerPosition(y));
        x += (centerX -= xShift);
        y += (centerY -= yShift);
        if (tr.offsetX != tr.offsetX) {
            if (backgroundColor != 0) {
                this.drawBlockSequence(batch, BlockUtils.BOX_DRAWING[136], (TextureRegion)font.mapping.get((int)this.solidBlock, tr), NumberUtils.intToFloatColor(Integer.reverseBytes(backgroundColor)), x - this.cellWidth * (sizingX - 1.0f) + centerX, y - this.cellHeight * (sizingY - 1.0f) + centerY, this.cellWidth * sizingX, this.cellHeight * sizingY, rotation);
            }
            final float[] boxes = BlockUtils.BOX_DRAWING[c - '\u2500'];
            this.drawBlockSequence(batch, boxes, (TextureRegion)font.mapping.get((int)this.solidBlock, tr), color, x - this.cellWidth * (sizingX - 1.0f) + centerX, y - this.cellHeight * (sizingY - 1.0f) + centerY, this.cellWidth * sizingX, this.cellHeight * sizingY, rotation);
            return this.cellWidth;
        }
        final Texture tex = tr.getTexture();
        float x2 = 0.0f;
        float x3 = 0.0f;
        float x4 = 0.0f;
        float y2 = 0.0f;
        float y3 = 0.0f;
        float y4 = 0.0f;
        final float iw = 1.0f / tex.getWidth();
        final float u = tr.getU();
        final float v = tr.getV();
        final float u2 = tr.getU2();
        final float v2 = tr.getV2();
        float w = tr.getRegionWidth() * scaleX * sizingX;
        float changedW = tr.xAdvance * scaleX;
        float h = tr.getRegionHeight() * scaleY * sizingY;
        float xc = tr.offsetX * scaleX - centerX * sizingX;
        float yt = font.cellHeight * scale - centerY - (tr.getRegionHeight() + tr.offsetY) * scaleY;
        if ((glyph & 0x20000000L) != 0x0L) {
            x2 += h * 0.2f;
            x3 -= h * 0.2f;
            x4 -= h * 0.2f;
        }
        final long script = glyph & 0x6000000L;
        final float scaledHeight = font.cellHeight * scale * sizingY;
        if (script == 100663296L) {
            w *= 0.5f;
            h *= 0.5f;
            yt = scaledHeight * 0.625f - h - tr.offsetY * scaleY * 0.5f - centerY * scale * sizingY;
            y3 += scaledHeight * 0.375f;
            y4 += scaledHeight * 0.375f;
            y2 += scaledHeight * 0.375f;
            if (!font.isMono) {
                changedW *= 0.5f;
            }
        }
        else if (script == 33554432L) {
            w *= 0.5f;
            h *= 0.5f;
            yt = scaledHeight * 0.625f - h - tr.offsetY * scaleY * 0.5f - centerY * scale * sizingY;
            y3 -= scaledHeight * 0.125f;
            y4 -= scaledHeight * 0.125f;
            y2 -= scaledHeight * 0.125f;
            if (!font.isMono) {
                changedW *= 0.5f;
            }
        }
        else if (script == 67108864L) {
            w *= 0.5f;
            h *= 0.5f;
            yt = scaledHeight * 0.625f - h - tr.offsetY * scaleY * 0.5f - centerY * scale * sizingY;
            y2 += scaledHeight * 0.125f;
            y3 += scaledHeight * 0.125f;
            y4 += scaledHeight * 0.125f;
            if (!font.isMono) {
                changedW *= 0.5f;
            }
        }
        if (backgroundColor != 0) {
            this.drawBlockSequence(batch, BlockUtils.BOX_DRAWING[136], (TextureRegion)font.mapping.get((int)this.solidBlock, tr), NumberUtils.intToFloatColor(Integer.reverseBytes(backgroundColor)), x - tr.xAdvance * scaleX * (sizingX - 1.0f) + atlasOffX - tr.offsetX * scaleX - 1.0f, y - this.cellHeight * (sizingY - 1.0f) + atlasOffY, tr.xAdvance * scaleX * sizingX, this.cellHeight * sizingY, rotation);
        }
        this.vertices[2] = color;
        this.vertices[3] = u;
        this.vertices[4] = v;
        this.vertices[7] = color;
        this.vertices[8] = u;
        this.vertices[9] = v2;
        this.vertices[12] = color;
        this.vertices[13] = u2;
        this.vertices[14] = v2;
        this.vertices[17] = color;
        this.vertices[18] = u2;
        this.vertices[19] = v;
        float p0x = xc + x2;
        float p0y = yt + y2 + h;
        float p1x = xc + x3;
        float p1y = yt + y3;
        float p2x = xc + x4 + w;
        float p2y = yt + y4;
        final float[] vertices = this.vertices;
        final int n = 15;
        final float[] vertices2 = this.vertices;
        final int n2 = 0;
        final float n3 = x + cos * p0x - sin * p0y;
        vertices2[n2] = n3;
        final float[] vertices3 = this.vertices;
        final int n4 = 5;
        final float n5 = x + cos * p1x - sin * p1y;
        vertices3[n4] = n5;
        vertices[n] = n3 - n5 + (this.vertices[10] = x + cos * p2x - sin * p2y);
        final float[] vertices4 = this.vertices;
        final int n6 = 16;
        final float[] vertices5 = this.vertices;
        final int n7 = 1;
        final float n8 = y + sin * p0x + cos * p0y;
        vertices5[n7] = n8;
        final float[] vertices6 = this.vertices;
        final int n9 = 6;
        final float n10 = y + sin * p1x + cos * p1y;
        vertices6[n9] = n10;
        vertices4[n6] = n8 - n10 + (this.vertices[11] = y + sin * p2x + cos * p2y);
        batch.draw(tex, this.vertices, 0, 20);
        if ((glyph & 0x40000000L) != 0x0L) {
            ++p0x;
            ++p1x;
            ++p2x;
            final float[] vertices7 = this.vertices;
            final int n11 = 15;
            final float[] vertices8 = this.vertices;
            final int n12 = 0;
            final float n13 = x + cos * p0x - sin * p0y;
            vertices8[n12] = n13;
            final float[] vertices9 = this.vertices;
            final int n14 = 5;
            final float n15 = x + cos * p1x - sin * p1y;
            vertices9[n14] = n15;
            vertices7[n11] = n13 - n15 + (this.vertices[10] = x + cos * p2x - sin * p2y);
            final float[] vertices10 = this.vertices;
            final int n16 = 16;
            final float[] vertices11 = this.vertices;
            final int n17 = 1;
            final float n18 = y + sin * p0x + cos * p0y;
            vertices11[n17] = n18;
            final float[] vertices12 = this.vertices;
            final int n19 = 6;
            final float n20 = y + sin * p1x + cos * p1y;
            vertices12[n19] = n20;
            vertices10[n16] = n18 - n20 + (this.vertices[11] = y + sin * p2x + cos * p2y);
            batch.draw(tex, this.vertices, 0, 20);
            p0x -= 2.0f;
            p1x -= 2.0f;
            p2x -= 2.0f;
            final float[] vertices13 = this.vertices;
            final int n21 = 15;
            final float[] vertices14 = this.vertices;
            final int n22 = 0;
            final float n23 = x + cos * p0x - sin * p0y;
            vertices14[n22] = n23;
            final float[] vertices15 = this.vertices;
            final int n24 = 5;
            final float n25 = x + cos * p1x - sin * p1y;
            vertices15[n24] = n25;
            vertices13[n21] = n23 - n25 + (this.vertices[10] = x + cos * p2x - sin * p2y);
            final float[] vertices16 = this.vertices;
            final int n26 = 16;
            final float[] vertices17 = this.vertices;
            final int n27 = 1;
            final float n28 = y + sin * p0x + cos * p0y;
            vertices17[n27] = n28;
            final float[] vertices18 = this.vertices;
            final int n29 = 6;
            final float n30 = y + sin * p1x + cos * p1y;
            vertices18[n29] = n30;
            vertices16[n26] = n28 - n30 + (this.vertices[11] = y + sin * p2x + cos * p2y);
            batch.draw(tex, this.vertices, 0, 20);
            p0x += 0.5f;
            p1x += 0.5f;
            p2x += 0.5f;
            final float[] vertices19 = this.vertices;
            final int n31 = 15;
            final float[] vertices20 = this.vertices;
            final int n32 = 0;
            final float n33 = x + cos * p0x - sin * p0y;
            vertices20[n32] = n33;
            final float[] vertices21 = this.vertices;
            final int n34 = 5;
            final float n35 = x + cos * p1x - sin * p1y;
            vertices21[n34] = n35;
            vertices19[n31] = n33 - n35 + (this.vertices[10] = x + cos * p2x - sin * p2y);
            final float[] vertices22 = this.vertices;
            final int n36 = 16;
            final float[] vertices23 = this.vertices;
            final int n37 = 1;
            final float n38 = y + sin * p0x + cos * p0y;
            vertices23[n37] = n38;
            final float[] vertices24 = this.vertices;
            final int n39 = 6;
            final float n40 = y + sin * p1x + cos * p1y;
            vertices24[n39] = n40;
            vertices22[n36] = n38 - n40 + (this.vertices[11] = y + sin * p2x + cos * p2y);
            batch.draw(tex, this.vertices, 0, 20);
            ++p0x;
            ++p1x;
            ++p2x;
            final float[] vertices25 = this.vertices;
            final int n41 = 15;
            final float[] vertices26 = this.vertices;
            final int n42 = 0;
            final float n43 = x + cos * p0x - sin * p0y;
            vertices26[n42] = n43;
            final float[] vertices27 = this.vertices;
            final int n44 = 5;
            final float n45 = x + cos * p1x - sin * p1y;
            vertices27[n44] = n45;
            vertices25[n41] = n43 - n45 + (this.vertices[10] = x + cos * p2x - sin * p2y);
            final float[] vertices28 = this.vertices;
            final int n46 = 16;
            final float[] vertices29 = this.vertices;
            final int n47 = 1;
            final float n48 = y + sin * p0x + cos * p0y;
            vertices29[n47] = n48;
            final float[] vertices30 = this.vertices;
            final int n49 = 6;
            final float n50 = y + sin * p1x + cos * p1y;
            vertices30[n49] = n50;
            vertices28[n46] = n48 - n50 + (this.vertices[11] = y + sin * p2x + cos * p2y);
            batch.draw(tex, this.vertices, 0, 20);
        }
        if ((glyph & 0x10000000L) != 0x0L) {
            GlyphRegion under = (GlyphRegion)font.mapping.get(9472);
            if (under != null && under.offsetX != under.offsetX) {
                p0x = -centerX;
                p0y = font.cellHeight * -0.5625f;
                this.drawBlockSequence(batch, BlockUtils.BOX_DRAWING[0], (TextureRegion)font.mapping.get((int)this.solidBlock, tr), color, x + cos * p0x - sin * p0y, y + (sin * p0x + cos * p0y), tr.xAdvance * scaleX + 2.0f, font.cellHeight, rotation);
            }
            else {
                under = (GlyphRegion)font.mapping.get(95);
                if (under != null) {
                    final float underU = under.getU() + (under.xAdvance - under.offsetX) * iw * 0.5f;
                    final float underV = under.getV();
                    final float underU2 = underU + iw;
                    final float underV2 = under.getV2();
                    final float hu = under.getRegionHeight() * scaleY;
                    final float yu = font.cellHeight * scale - hu - under.offsetY * scaleY - centerY;
                    xc = under.offsetX * scaleX - centerX * scale;
                    x2 = -scaleX * under.offsetX - scale;
                    this.vertices[2] = color;
                    this.vertices[3] = underU;
                    this.vertices[4] = underV;
                    this.vertices[7] = color;
                    this.vertices[8] = underU;
                    this.vertices[9] = underV2;
                    this.vertices[12] = color;
                    this.vertices[13] = underU2;
                    this.vertices[14] = underV2;
                    this.vertices[17] = color;
                    this.vertices[18] = underU2;
                    this.vertices[19] = underV;
                    p0x = xc + x2 - scale;
                    p0y = yu + hu;
                    p1x = xc + x2 - scale;
                    p1y = yu;
                    p2x = xc + x2 + changedW + scale;
                    p2y = yu;
                    final float[] vertices31 = this.vertices;
                    final int n51 = 15;
                    final float[] vertices32 = this.vertices;
                    final int n52 = 0;
                    final float n53 = x + cos * p0x - sin * p0y;
                    vertices32[n52] = n53;
                    final float[] vertices33 = this.vertices;
                    final int n54 = 5;
                    final float n55 = x + cos * p1x - sin * p1y;
                    vertices33[n54] = n55;
                    vertices31[n51] = n53 - n55 + (this.vertices[10] = x + cos * p2x - sin * p2y);
                    final float[] vertices34 = this.vertices;
                    final int n56 = 16;
                    final float[] vertices35 = this.vertices;
                    final int n57 = 1;
                    final float n58 = y + sin * p0x + cos * p0y;
                    vertices35[n57] = n58;
                    final float[] vertices36 = this.vertices;
                    final int n59 = 6;
                    final float n60 = y + sin * p1x + cos * p1y;
                    vertices36[n59] = n60;
                    vertices34[n56] = n58 - n60 + (this.vertices[11] = y + sin * p2x + cos * p2y);
                    batch.draw(under.getTexture(), this.vertices, 0, 20);
                }
            }
        }
        if ((glyph & 0x8000000L) != 0x0L) {
            GlyphRegion dash = (GlyphRegion)font.mapping.get(9472);
            if (dash != null && dash.offsetX != dash.offsetX) {
                p0x = -centerX;
                p0y = this.cellHeight * -0.125f;
                this.drawBlockSequence(batch, BlockUtils.BOX_DRAWING[0], (TextureRegion)font.mapping.get((int)this.solidBlock, tr), color, x + cos * p0x - sin * p0y, y + (sin * p0x + cos * p0y), tr.xAdvance * scaleX + 2.0f, font.cellHeight, rotation);
            }
            else {
                dash = (GlyphRegion)font.mapping.get(45);
                if (dash != null) {
                    final float dashU = dash.getU() + (dash.xAdvance - dash.offsetX) * iw * 0.5f;
                    final float dashV = dash.getV();
                    final float dashU2 = dashU + iw;
                    final float dashV2 = dash.getV2();
                    final float hd = dash.getRegionHeight() * scaleY;
                    final float yd = font.cellHeight * scale - hd - dash.offsetY * scaleY - centerY;
                    xc = dash.offsetX * scaleX - centerX * scale;
                    x2 = -scaleX * dash.offsetX - scale;
                    this.vertices[2] = color;
                    this.vertices[3] = dashU;
                    this.vertices[4] = dashV;
                    this.vertices[7] = color;
                    this.vertices[8] = dashU;
                    this.vertices[9] = dashV2;
                    this.vertices[12] = color;
                    this.vertices[13] = dashU2;
                    this.vertices[14] = dashV2;
                    this.vertices[17] = color;
                    this.vertices[18] = dashU2;
                    this.vertices[19] = dashV;
                    p0x = xc + x2 - scale;
                    p0y = yd + hd;
                    p1x = xc + x2 - scale;
                    p1y = yd;
                    p2x = xc + x2 + changedW + scale;
                    p2y = yd;
                    final float[] vertices37 = this.vertices;
                    final int n61 = 15;
                    final float[] vertices38 = this.vertices;
                    final int n62 = 0;
                    final float n63 = x + cos * p0x - sin * p0y;
                    vertices38[n62] = n63;
                    final float[] vertices39 = this.vertices;
                    final int n64 = 5;
                    final float n65 = x + cos * p1x - sin * p1y;
                    vertices39[n64] = n65;
                    vertices37[n61] = n63 - n65 + (this.vertices[10] = x + cos * p2x - sin * p2y);
                    final float[] vertices40 = this.vertices;
                    final int n66 = 16;
                    final float[] vertices41 = this.vertices;
                    final int n67 = 1;
                    final float n68 = y + sin * p0x + cos * p0y;
                    vertices41[n67] = n68;
                    final float[] vertices42 = this.vertices;
                    final int n69 = 6;
                    final float n70 = y + sin * p1x + cos * p1y;
                    vertices42[n69] = n70;
                    vertices40[n66] = n68 - n70 + (this.vertices[11] = y + sin * p2x + cos * p2y);
                    batch.draw(dash.getTexture(), this.vertices, 0, 20);
                }
            }
        }
        return changedW;
    }
    
    public Layout markup(final String text, final Layout appendTo) {
        boolean capitalize = false;
        boolean previousWasLetter = false;
        boolean capsLock = false;
        boolean lowerCase = false;
        boolean initial = true;
        int scale = 3;
        int fontIndex = -1;
        Font font = this;
        final long COLOR_MASK = -4294967296L;
        final long baseColor = Long.reverseBytes(NumberUtils.floatToIntBits(appendTo.getBaseColor())) & 0xFFFFFFFE00000000L;
        long current;
        long color = current = baseColor;
        if (appendTo.font == null || !appendTo.font.equals(this)) {
            appendTo.clear();
            appendTo.font(this);
        }
        appendTo.peekLine().height = 0.0f;
        final float targetWidth = appendTo.getTargetWidth();
        int kern = -1;
        for (int i = 0, n = text.length(); i < n; ++i) {
            float scaleX = font.scaleX * (scale + 1) * 0.25f;
            if (text.charAt(i) == '{' && i + 1 < n && text.charAt(i + 1) != '{') {
                final int start = i;
                int sizeChange = -1;
                int fontChange = -1;
                int end = text.indexOf(125, i);
                if (end == -1) {
                    end = text.length();
                }
                int eq = end;
                while (i < n && i <= end) {
                    final int c = text.charAt(i);
                    appendTo.add(current | (long)c);
                    if (c == 64) {
                        fontChange = i;
                    }
                    else if (c == 37) {
                        sizeChange = i;
                    }
                    else if (c == 61) {
                        eq = Math.min(eq, i);
                    }
                    ++i;
                }
                final char after = (eq + 1 >= end) ? '\0' : text.charAt(eq + 1);
                if (start + 1 == end || "RESET".equalsIgnoreCase(safeSubstring(text, start + 1, end))) {
                    scale = 3;
                    font = this;
                    fontIndex = 0;
                    current &= 0xFFFFFFFFF9FFFFFFL;
                }
                else if (after == '^' || after == '=' || after == '.') {
                    switch (after) {
                        case '^': {
                            if ((current & 0x6000000L) == 0x6000000L) {
                                current &= 0xFFFFFFFFF9FFFFFFL;
                                break;
                            }
                            current |= 0x6000000L;
                            break;
                        }
                        case '.': {
                            if ((current & 0x6000000L) == 0x2000000L) {
                                current &= 0xFFFFFFFFFDFFFFFFL;
                                break;
                            }
                            current = ((current & 0xFFFFFFFFF9FFFFFFL) | 0x2000000L);
                            break;
                        }
                        case '=': {
                            if ((current & 0x6000000L) == 0x4000000L) {
                                current &= 0xFFFFFFFFFBFFFFFFL;
                                break;
                            }
                            current = ((current & 0xFFFFFFFFF9FFFFFFL) | 0x4000000L);
                            break;
                        }
                    }
                }
                else if (fontChange >= 0 && this.family != null) {
                    fontIndex = this.family.fontAliases.get(safeSubstring(text, fontChange + 1, end), -1);
                    if (fontIndex == -1) {
                        font = this;
                        fontIndex = 0;
                    }
                    else {
                        font = this.family.connected[fontIndex];
                        if (font == null) {
                            font = this;
                            fontIndex = 0;
                        }
                    }
                }
                else if (sizeChange >= 0) {
                    if (sizeChange + 1 == end) {
                        if (eq + 1 == sizeChange) {
                            scale = 3;
                        }
                        else {
                            scale = ((intFromDec(text, eq + 1, sizeChange) - 24) / 25 & 0xF);
                        }
                    }
                    else {
                        scale = ((intFromDec(text, sizeChange + 1, end) - 24) / 25 & 0xF);
                    }
                }
                current = ((current & 0xFFFFFFFFFF00FFFFL) | (long)((scale - 3 & 0xF) << 20) | (long)((fontIndex & 0xF) << 16));
                --i;
            }
            else if (text.charAt(i) == '[') {
                int c = 91;
                if (++i < n && (c = text.charAt(i)) != 91 && c != 43) {
                    if (c == 93) {
                        color = baseColor;
                        current = (color & 0xFFFFFFFFF9FFFFFFL);
                        scale = 3;
                        font = this;
                        capitalize = false;
                        capsLock = false;
                        lowerCase = false;
                    }
                    else {
                        final int len = text.indexOf(93, i) - i;
                        if (len < 0) {
                            break;
                        }
                        switch (c) {
                            case 42: {
                                current ^= 0x40000000L;
                                break;
                            }
                            case 47: {
                                current ^= 0x20000000L;
                                break;
                            }
                            case 94: {
                                if ((current & 0x6000000L) == 0x6000000L) {
                                    current &= 0xFFFFFFFFF9FFFFFFL;
                                    break;
                                }
                                current |= 0x6000000L;
                                break;
                            }
                            case 46: {
                                if ((current & 0x6000000L) == 0x2000000L) {
                                    current &= 0xFFFFFFFFFDFFFFFFL;
                                    break;
                                }
                                current = ((current & 0xFFFFFFFFF9FFFFFFL) | 0x2000000L);
                                break;
                            }
                            case 61: {
                                if ((current & 0x6000000L) == 0x4000000L) {
                                    current &= 0xFFFFFFFFFBFFFFFFL;
                                    break;
                                }
                                current = ((current & 0xFFFFFFFFF9FFFFFFL) | 0x4000000L);
                                break;
                            }
                            case 95: {
                                current ^= 0x10000000L;
                                break;
                            }
                            case 126: {
                                current ^= 0x8000000L;
                                break;
                            }
                            case 59: {
                                capitalize = !capitalize;
                                capsLock = false;
                                lowerCase = false;
                                break;
                            }
                            case 33: {
                                capsLock = !capsLock;
                                capitalize = false;
                                lowerCase = false;
                                break;
                            }
                            case 44: {
                                lowerCase = !lowerCase;
                                capitalize = false;
                                capsLock = false;
                                break;
                            }
                            case 37: {
                                if (len >= 2) {
                                    current = ((current & 0xFFFFFFFFFF0FFFFFL) | (long)(((scale = ((intFromDec(text, i + 1, i + len) - 24) / 25 & 0xF)) - 3 & 0xF) << 20));
                                    break;
                                }
                                current &= 0xFFFFFFFFFF0FFFFFL;
                                scale = 3;
                                break;
                            }
                            case 35: {
                                if (len >= 7 && len < 9) {
                                    color = (longFromHex(text, i + 1, i + 7) << 40 | 0xFE00000000L);
                                }
                                else if (len >= 9) {
                                    color = (longFromHex(text, i + 1, i + 9) << 32 & 0xFFFFFFFE00000000L);
                                }
                                else {
                                    color = baseColor;
                                }
                                current = ((current & 0xFFFFFFFFL) | color);
                                break;
                            }
                            case 64: {
                                if (this.family == null) {
                                    font = this;
                                    fontIndex = 0;
                                    break;
                                }
                                fontIndex = this.family.fontAliases.get(safeSubstring(text, i + 1, i + len), 0);
                                current = ((current & 0xFFFFFFFFFFF0FFFFL) | ((long)fontIndex & 0xFL) << 16);
                                font = this.family.connected[fontIndex & 0xF];
                                if (font == null) {
                                    font = this;
                                    break;
                                }
                                break;
                            }
                            case 124: {
                                final int lookupColor = this.colorLookup.getRgba(safeSubstring(text, i + 1, i + len)) & 0xFFFFFFFE;
                                if (lookupColor == 256) {
                                    color = baseColor;
                                }
                                else {
                                    color = (long)lookupColor << 32;
                                }
                                current = ((current & 0xFFFFFFFFL) | color);
                                break;
                            }
                            default: {
                                final int gdxColor = this.colorLookup.getRgba(safeSubstring(text, i, i + len)) & 0xFFFFFFFE;
                                if (gdxColor == 256) {
                                    color = baseColor;
                                }
                                else {
                                    color = (long)gdxColor << 32;
                                }
                                current = ((current & 0xFFFFFFFFL) | color);
                                break;
                            }
                        }
                        i += len;
                    }
                }
                else {
                    if (c == 43 && this.nameLookup != null) {
                        final int len2 = text.indexOf(93, i) - i;
                        if (len2 >= 0) {
                            c = this.nameLookup.get(safeSubstring(text, i + 1, i + len2), 43);
                            i += len2;
                            scaleX = (scale + 1) * 0.25f * this.cellHeight / (((GlyphRegion)font.mapping.get(c, font.defaultValue)).xAdvance * 1.25f);
                        }
                    }
                    float w;
                    if (font.kerning == null) {
                        final Line peekLine = appendTo.peekLine();
                        final float width = peekLine.width + xAdvance(font, scaleX, current | (long)c);
                        peekLine.width = width;
                        w = width;
                        if (initial) {
                            final float ox = ((GlyphRegion)font.mapping.get(c, font.defaultValue)).offsetX * scaleX;
                            if (ox < 0.0f) {
                                final Line peekLine2 = appendTo.peekLine();
                                final float width2 = peekLine2.width - ox;
                                peekLine2.width = width2;
                                w = width2;
                            }
                            initial = false;
                        }
                    }
                    else {
                        kern = (kern << 16 | c);
                        final Line peekLine3 = appendTo.peekLine();
                        final float width3 = peekLine3.width + (xAdvance(font, scaleX, current | (long)c) + font.kerning.get(kern, 0) * scaleX * (1.0f + 0.5f * (-(current & 0x6000000L) >> 63)));
                        peekLine3.width = width3;
                        w = width3;
                        if (initial) {
                            final float ox = ((GlyphRegion)font.mapping.get(c, font.defaultValue)).offsetX * scaleX * (1.0f + 0.5f * (-(current & 0x6000000L) >> 63));
                            if (ox < 0.0f) {
                                final Line peekLine4 = appendTo.peekLine();
                                final float width4 = peekLine4.width - ox;
                                peekLine4.width = width4;
                                w = width4;
                            }
                            initial = false;
                        }
                    }
                    if (c == 91) {
                        appendTo.add(current | 0x2L);
                    }
                    else {
                        appendTo.add(current | (long)c);
                    }
                    if (targetWidth > 0.0f && w > targetWidth) {
                        final Line earlier = appendTo.peekLine();
                        final Line later = appendTo.pushLine();
                        if (later == null) {
                            final String ellipsis = (appendTo.ellipsis == null) ? "" : appendTo.ellipsis;
                            for (int j = earlier.glyphs.size - 1; j >= 0; --j) {
                                long curr;
                                while (j > 0 && ((curr = earlier.glyphs.get(j)) >>> 32 == 0L || Arrays.binarySearch(this.spaceChars.items, 0, this.spaceChars.size, (char)curr) < 0)) {
                                    --j;
                                }
                                while (j > 0 && ((curr = earlier.glyphs.get(j)) >>> 32 == 0L || Arrays.binarySearch(this.spaceChars.items, 0, this.spaceChars.size, (char)curr) >= 0)) {
                                    --j;
                                }
                                float change = 0.0f;
                                float changeNext = 0.0f;
                                if (font.kerning == null) {
                                    int k = j + 1;
                                    for (int e = 0; e < ellipsis.length(); ++e) {
                                        if (k < earlier.glyphs.size) {
                                            change += xAdvance(font, scaleX, earlier.glyphs.get(k));
                                        }
                                        changeNext += xAdvance(font, scaleX, current | (long)ellipsis.charAt(e));
                                        ++k;
                                    }
                                }
                                else {
                                    int k2 = (int)earlier.glyphs.get(j) & 0xFFFF;
                                    int k2e = 65535;
                                    int l = j + 1;
                                    for (int e2 = 0; e2 < ellipsis.length(); ++e2) {
                                        if (l < earlier.glyphs.size) {
                                            curr = earlier.glyphs.get(l);
                                            k2 = (k2 << 16 | (char)curr);
                                            change += xAdvance(font, scaleX, curr) + font.kerning.get(k2, 0) * scaleX * (1.0f + 0.5f * (-(curr & 0x6000000L) >> 63));
                                        }
                                        final long currE = current | (long)ellipsis.charAt(e2);
                                        k2e = (k2e << 16 | (char)currE);
                                        changeNext += xAdvance(font, scaleX, currE) + font.kerning.get(k2e, 0) * scaleX * (1.0f + 0.5f * (-(currE & 0x6000000L) >> 63));
                                        ++l;
                                    }
                                }
                                if (earlier.width + changeNext < appendTo.getTargetWidth()) {
                                    for (int e3 = 0; e3 < ellipsis.length(); ++e3) {
                                        earlier.glyphs.add(current | (long)ellipsis.charAt(e3));
                                    }
                                    earlier.width += changeNext;
                                    return appendTo;
                                }
                                if (earlier.width - change + changeNext < appendTo.getTargetWidth()) {
                                    earlier.glyphs.truncate(j + 1);
                                    for (int e3 = 0; e3 < ellipsis.length(); ++e3) {
                                        earlier.glyphs.add(current | (long)ellipsis.charAt(e3));
                                    }
                                    earlier.width = earlier.width - change + changeNext;
                                    return appendTo;
                                }
                            }
                        }
                        else {
                            for (int m = earlier.glyphs.size - 2; m >= 0; --m) {
                                long curr2;
                                if ((curr2 = earlier.glyphs.get(m)) >>> 32 == 0L || Arrays.binarySearch(this.breakChars.items, 0, this.breakChars.size, (char)curr2) >= 0) {
                                    int leading = 0;
                                    while (m > 0 && ((curr2 = earlier.glyphs.get(m)) >>> 32 == 0L || Arrays.binarySearch(this.spaceChars.items, 0, this.spaceChars.size, (char)curr2) >= 0)) {
                                        ++leading;
                                        --m;
                                    }
                                    this.glyphBuffer.clear();
                                    float change = 0.0f;
                                    float changeNext = 0.0f;
                                    if (font.kerning == null) {
                                        boolean curly = false;
                                        for (int k3 = m + 1; k3 < earlier.glyphs.size; ++k3) {
                                            curr2 = earlier.glyphs.get(k3);
                                            if (curly) {
                                                this.glyphBuffer.add(curr2);
                                                if ((char)curr2 == '{') {
                                                    curly = false;
                                                }
                                                else {
                                                    if ((char)curr2 == '}') {
                                                        curly = false;
                                                    }
                                                    continue;
                                                }
                                            }
                                            if ((char)curr2 == '{') {
                                                this.glyphBuffer.add(curr2);
                                                curly = true;
                                            }
                                            else {
                                                final float adv = xAdvance(font, scaleX, curr2);
                                                change += adv;
                                                if (--leading < 0) {
                                                    this.glyphBuffer.add(curr2);
                                                    changeNext += adv;
                                                    if (this.glyphBuffer.size == 1) {
                                                        final float ox2 = ((GlyphRegion)font.mapping.get((int)(char)curr2, font.defaultValue)).offsetX * scaleX * (1.0f + 0.5f * (-(current & 0x6000000L) >> 63));
                                                        if (ox2 < 0.0f) {
                                                            changeNext -= ox2;
                                                        }
                                                        initial = false;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    else {
                                        int k4 = (char)earlier.glyphs.get(m);
                                        int k5 = -1;
                                        boolean curly2 = false;
                                        for (int k6 = m + 1; k6 < earlier.glyphs.size; ++k6) {
                                            curr2 = earlier.glyphs.get(k6);
                                            if (curly2) {
                                                this.glyphBuffer.add(curr2);
                                                if ((char)curr2 == '{') {
                                                    curly2 = false;
                                                }
                                                else {
                                                    if ((char)curr2 == '}') {
                                                        curly2 = false;
                                                    }
                                                    continue;
                                                }
                                            }
                                            if ((char)curr2 == '{') {
                                                this.glyphBuffer.add(curr2);
                                                curly2 = true;
                                            }
                                            else {
                                                k4 = (k4 << 16 | (char)curr2);
                                                final float adv2 = xAdvance(font, scaleX, curr2);
                                                change += adv2 + font.kerning.get(k4, 0) * scaleX * (1.0f + 0.5f * (-(curr2 & 0x6000000L) >> 63));
                                                if (--leading < 0) {
                                                    k5 = (k5 << 16 | (char)curr2);
                                                    changeNext += adv2 + font.kerning.get(k5, 0) * scaleX * (1.0f + 0.5f * (-(curr2 & 0x6000000L) >> 63));
                                                    this.glyphBuffer.add(curr2);
                                                    if (this.glyphBuffer.size == 1) {
                                                        final float ox3 = ((GlyphRegion)font.mapping.get((int)(char)curr2, font.defaultValue)).offsetX * scaleX * (1.0f + 0.5f * (-(current & 0x6000000L) >> 63));
                                                        if (ox3 < 0.0f) {
                                                            changeNext -= ox3;
                                                        }
                                                        initial = false;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (earlier.width - change <= targetWidth) {
                                        earlier.glyphs.truncate(m + 1);
                                        earlier.glyphs.add(10L);
                                        later.width = changeNext;
                                        final Line line = earlier;
                                        line.width -= change;
                                        later.glyphs.addAll(this.glyphBuffer);
                                        later.height = Math.max(later.height, font.cellHeight * (scale + 1) * 0.25f);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    else {
                        appendTo.peekLine().height = Math.max(appendTo.peekLine().height, font.cellHeight * (scale + 1) * 0.25f);
                    }
                }
            }
            else {
                char ch = text.charAt(i);
                if (isLowerCase(ch)) {
                    if ((capitalize && !previousWasLetter) || capsLock) {
                        ch = Category.caseUp(ch);
                    }
                    previousWasLetter = true;
                }
                else if (isUpperCase(ch)) {
                    if ((capitalize && previousWasLetter) || lowerCase) {
                        ch = Category.caseDown(ch);
                    }
                    previousWasLetter = true;
                }
                else {
                    previousWasLetter = false;
                }
                if (ch >= '\ue000' && ch < '\uf800') {
                    scaleX = (scale + 1) * 0.25f * this.cellHeight / (((GlyphRegion)font.mapping.get((int)ch, font.defaultValue)).xAdvance * 1.25f);
                }
                float w2;
                if (font.kerning == null) {
                    final Line peekLine5 = appendTo.peekLine();
                    final float width5 = peekLine5.width + xAdvance(font, scaleX, current | (long)ch);
                    peekLine5.width = width5;
                    w2 = width5;
                }
                else {
                    kern = (kern << 16 | ch);
                    final Line peekLine6 = appendTo.peekLine();
                    final float width6 = peekLine6.width + (xAdvance(font, scaleX, current | (long)ch) + font.kerning.get(kern, 0) * scaleX * (1.0f + 0.5f * (-((current | (long)ch) & 0x6000000L) >> 63)));
                    peekLine6.width = width6;
                    w2 = width6;
                }
                if (initial) {
                    float ox4 = ((GlyphRegion)font.mapping.get((int)ch, font.defaultValue)).offsetX * scaleX;
                    if (!this.isMono) {
                        ox4 *= 1.0f + 0.5f * (-(current & 0x6000000L) >> 63);
                    }
                    if (ox4 < 0.0f) {
                        final Line peekLine7 = appendTo.peekLine();
                        final float width7 = peekLine7.width - ox4;
                        peekLine7.width = width7;
                        w2 = width7;
                    }
                    initial = false;
                }
                if (ch == '\n') {
                    appendTo.peekLine().height = Math.max(appendTo.peekLine().height, font.cellHeight * (scale + 1) * 0.25f);
                    initial = true;
                }
                appendTo.add(current | (long)ch);
                if ((targetWidth > 0.0f && w2 > targetWidth) || appendTo.atLimit) {
                    final Line earlier2 = appendTo.peekLine();
                    Line later2;
                    if (appendTo.lines.size >= appendTo.maxLines) {
                        later2 = null;
                    }
                    else {
                        later2 = (Line)Line.POOL.obtain();
                        later2.height = 0.0f;
                        appendTo.lines.add(later2);
                        initial = true;
                    }
                    if (later2 == null) {
                        final String ellipsis2 = (appendTo.ellipsis == null) ? "" : appendTo.ellipsis;
                        for (int j2 = earlier2.glyphs.size - 2; j2 >= 0; --j2) {
                            long curr3;
                            if ((curr3 = earlier2.glyphs.get(j2)) >>> 32 == 0L || Arrays.binarySearch(this.breakChars.items, 0, this.breakChars.size, (char)curr3) >= 0) {
                                while (j2 > 0 && ((curr3 = earlier2.glyphs.get(j2)) >>> 32 == 0L || Arrays.binarySearch(this.spaceChars.items, 0, this.spaceChars.size, (char)curr3) >= 0)) {
                                    --j2;
                                }
                                float change2 = 0.0f;
                                if (font.kerning == null) {
                                    boolean curly = false;
                                    for (int k3 = j2 + 1; k3 < earlier2.glyphs.size; ++k3) {
                                        curr3 = earlier2.glyphs.get(k3);
                                        if (curly) {
                                            if ((char)curr3 == '{') {
                                                curly = false;
                                            }
                                            else {
                                                if ((char)curr3 == '}') {
                                                    curly = false;
                                                }
                                                continue;
                                            }
                                        }
                                        if ((char)curr3 == '{') {
                                            curly = true;
                                        }
                                        else {
                                            final float adv = xAdvance(font, scaleX, curr3);
                                            change2 += adv;
                                        }
                                    }
                                    for (int e4 = 0; e4 < ellipsis2.length(); ++e4) {
                                        curr3 = (current | (long)ellipsis2.charAt(e4));
                                        final float adv = xAdvance(font, scaleX, curr3);
                                        change2 -= adv;
                                    }
                                }
                                else {
                                    int k4 = (char)earlier2.glyphs.get(j2);
                                    kern = -1;
                                    boolean curly3 = false;
                                    for (int k = j2 + 1; k < earlier2.glyphs.size; ++k) {
                                        curr3 = earlier2.glyphs.get(k);
                                        if (curly3) {
                                            if ((char)curr3 == '{') {
                                                curly3 = false;
                                            }
                                            else {
                                                if ((char)curr3 == '}') {
                                                    curly3 = false;
                                                }
                                                continue;
                                            }
                                        }
                                        if ((char)curr3 == '{') {
                                            curly3 = true;
                                        }
                                        else {
                                            k4 = (k4 << 16 | (char)curr3);
                                            final float adv3 = xAdvance(font, scaleX, curr3);
                                            change2 += adv3 + font.kerning.get(k4, 0) * scaleX * ((this.isMono || (curr3 & 0x6000000L) == 0x0L) ? 1.0f : 0.5f);
                                        }
                                    }
                                    for (int e3 = 0; e3 < ellipsis2.length(); ++e3) {
                                        curr3 = (current | (long)ellipsis2.charAt(e3));
                                        k4 = (k4 << 16 | (char)curr3);
                                        final float adv3 = xAdvance(font, scaleX, curr3);
                                        change2 -= adv3 + font.kerning.get(k4, 0) * scaleX * ((this.isMono || (curr3 & 0x6000000L) == 0x0L) ? 1.0f : 0.5f);
                                    }
                                }
                                if (earlier2.width - change2 <= targetWidth) {
                                    earlier2.glyphs.truncate(j2 + 1);
                                    for (int e5 = 0; e5 < ellipsis2.length(); ++e5) {
                                        earlier2.glyphs.add(current | (long)ellipsis2.charAt(e5));
                                    }
                                    final Line line2 = earlier2;
                                    line2.width -= change2;
                                    return appendTo;
                                }
                            }
                        }
                    }
                    else {
                        for (int j = earlier2.glyphs.size - 2; j >= 0; --j) {
                            long curr;
                            if ((curr = earlier2.glyphs.get(j)) >>> 32 == 0L || Arrays.binarySearch(this.breakChars.items, 0, this.breakChars.size, (char)curr) >= 0) {
                                int leading2 = 0;
                                while (j > 0 && ((curr = earlier2.glyphs.get(j)) >>> 32 == 0L || Arrays.binarySearch(this.spaceChars.items, 0, this.spaceChars.size, (char)curr) >= 0)) {
                                    ++leading2;
                                    --j;
                                }
                                this.glyphBuffer.clear();
                                float change2 = 0.0f;
                                float changeNext2 = 0.0f;
                                if (font.kerning == null) {
                                    boolean curly3 = false;
                                    for (int k = j + 1; k < earlier2.glyphs.size; ++k) {
                                        curr = earlier2.glyphs.get(k);
                                        if (curly3) {
                                            this.glyphBuffer.add(curr);
                                            if ((char)curr == '{') {
                                                curly3 = false;
                                            }
                                            else {
                                                if ((char)curr == '}') {
                                                    curly3 = false;
                                                }
                                                continue;
                                            }
                                        }
                                        if ((char)curr == '{') {
                                            this.glyphBuffer.add(curr);
                                            curly3 = true;
                                        }
                                        else {
                                            final float adv3 = xAdvance(font, scaleX, curr);
                                            change2 += adv3;
                                            if (--leading2 < 0) {
                                                this.glyphBuffer.add(curr);
                                                changeNext2 += adv3;
                                                if (this.glyphBuffer.size == 1) {
                                                    final float ox5 = ((GlyphRegion)font.mapping.get((int)ch, font.defaultValue)).offsetX * scaleX * ((this.isMono || (curr & 0x6000000L) == 0x0L) ? 1.0f : 0.5f);
                                                    if (ox5 < 0.0f) {
                                                        changeNext2 -= ox5;
                                                    }
                                                    initial = false;
                                                }
                                            }
                                        }
                                    }
                                }
                                else {
                                    int k7 = (char)earlier2.glyphs.get(j);
                                    kern = -1;
                                    boolean curly2 = false;
                                    for (int k6 = j + 1; k6 < earlier2.glyphs.size; ++k6) {
                                        curr = earlier2.glyphs.get(k6);
                                        if (curly2) {
                                            this.glyphBuffer.add(curr);
                                            if ((char)curr == '{') {
                                                curly2 = false;
                                            }
                                            else {
                                                if ((char)curr == '}') {
                                                    curly2 = false;
                                                }
                                                continue;
                                            }
                                        }
                                        if ((char)curr == '{') {
                                            this.glyphBuffer.add(curr);
                                            curly2 = true;
                                        }
                                        else {
                                            k7 = (k7 << 16 | (char)curr);
                                            final float adv2 = xAdvance(font, scaleX, curr);
                                            change2 += adv2 + font.kerning.get(k7, 0) * scaleX * ((this.isMono || (curr & 0x6000000L) == 0x0L) ? 1.0f : 0.5f);
                                            if (--leading2 < 0) {
                                                kern = (kern << 16 | (char)curr);
                                                changeNext2 += adv2 + font.kerning.get(kern, 0) * scaleX * ((this.isMono || (curr & 0x6000000L) == 0x0L) ? 1.0f : 0.5f);
                                                this.glyphBuffer.add(curr);
                                                if (this.glyphBuffer.size == 1) {
                                                    final float ox3 = ((GlyphRegion)font.mapping.get((int)ch, font.defaultValue)).offsetX * scaleX * ((this.isMono || (curr & 0x6000000L) == 0x0L) ? 1.0f : 0.5f);
                                                    if (ox3 < 0.0f) {
                                                        changeNext2 -= ox3;
                                                    }
                                                    initial = false;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (earlier2.width - change2 <= targetWidth) {
                                    earlier2.glyphs.truncate(j + 1);
                                    earlier2.glyphs.add(10L);
                                    later2.width = changeNext2;
                                    final Line line3 = earlier2;
                                    line3.width -= change2;
                                    later2.glyphs.addAll(this.glyphBuffer);
                                    later2.height = Math.max(later2.height, font.cellHeight * (scale + 1) * 0.25f);
                                    break;
                                }
                            }
                        }
                    }
                }
                else {
                    appendTo.peekLine().height = Math.max(appendTo.peekLine().height, font.cellHeight * (scale + 1) * 0.25f);
                }
            }
        }
        return appendTo;
    }
    
    public long markupGlyph(final char chr, final String markup) {
        return markupGlyph(chr, markup, this.colorLookup, this.family);
    }
    
    public long markupGlyph(final String markup) {
        boolean capitalize = false;
        boolean capsLock = false;
        boolean lowerCase = false;
        int scale = 3;
        int fontIndex = -1;
        Font font = this;
        final long COLOR_MASK = -4294967296L;
        final long baseColor = -8589934592L;
        long current;
        long color = current = baseColor;
        for (int i = 0, n = markup.length(); i <= n; ++i) {
            if (i == n) {
                return current | 0x20L;
            }
            if (markup.charAt(i) == '{' && i + 1 < n && markup.charAt(i + 1) != '{') {
                final int start = i;
                int sizeChange = -1;
                int fontChange = -1;
                int end = markup.indexOf(125, i);
                if (end == -1) {
                    end = markup.length();
                }
                int eq = end;
                while (i < n && i <= end) {
                    final int c = markup.charAt(i);
                    if (c == 64) {
                        fontChange = i;
                    }
                    else if (c == 37) {
                        sizeChange = i;
                    }
                    else if (c == 61) {
                        eq = Math.min(eq, i);
                    }
                    ++i;
                }
                final char after = (eq + 1 >= end) ? '\0' : markup.charAt(eq + 1);
                if (start + 1 == end || "RESET".equalsIgnoreCase(safeSubstring(markup, start + 1, end))) {
                    scale = 3;
                    font = this;
                    fontIndex = 0;
                    current &= 0xFFFFFFFFF9FFFFFFL;
                }
                else if (after == '^' || after == '=' || after == '.') {
                    switch (after) {
                        case '^': {
                            if ((current & 0x6000000L) == 0x6000000L) {
                                current &= 0xFFFFFFFFF9FFFFFFL;
                                break;
                            }
                            current |= 0x6000000L;
                            break;
                        }
                        case '.': {
                            if ((current & 0x6000000L) == 0x2000000L) {
                                current &= 0xFFFFFFFFFDFFFFFFL;
                                break;
                            }
                            current = ((current & 0xFFFFFFFFF9FFFFFFL) | 0x2000000L);
                            break;
                        }
                        case '=': {
                            if ((current & 0x6000000L) == 0x4000000L) {
                                current &= 0xFFFFFFFFFBFFFFFFL;
                                break;
                            }
                            current = ((current & 0xFFFFFFFFF9FFFFFFL) | 0x4000000L);
                            break;
                        }
                    }
                }
                else if (fontChange >= 0 && this.family != null) {
                    fontIndex = this.family.fontAliases.get(safeSubstring(markup, fontChange + 1, end), -1);
                    if (fontIndex == -1) {
                        font = this;
                        fontIndex = 0;
                    }
                    else {
                        font = this.family.connected[fontIndex];
                        if (font == null) {
                            font = this;
                            fontIndex = 0;
                        }
                    }
                }
                else if (sizeChange >= 0) {
                    if (sizeChange + 1 == end) {
                        if (eq + 1 == sizeChange) {
                            scale = 3;
                        }
                        else {
                            scale = ((intFromDec(markup, eq + 1, sizeChange) - 24) / 25 & 0xF);
                        }
                    }
                    else {
                        scale = ((intFromDec(markup, sizeChange + 1, end) - 24) / 25 & 0xF);
                    }
                }
                current = ((current & 0xFFFFFFFFFF00FFFFL) | (long)((scale - 3 & 0xF) << 20) | (long)((fontIndex & 0xF) << 16));
                --i;
            }
            else {
                if (markup.charAt(i) != '[') {
                    char ch = markup.charAt(i);
                    if (isLowerCase(ch)) {
                        if (capitalize || capsLock) {
                            ch = Category.caseUp(ch);
                        }
                    }
                    else if (isUpperCase(ch) && lowerCase) {
                        ch = Category.caseDown(ch);
                    }
                    return current | (long)ch;
                }
                int c = 91;
                if (++i < n && (c = markup.charAt(i)) != 91 && c != 43) {
                    if (c == 93) {
                        color = baseColor;
                        current = (color & 0xFFFFFFFFF9FFFFFFL);
                        scale = 3;
                        font = this;
                        capitalize = false;
                        capsLock = false;
                        lowerCase = false;
                    }
                    else {
                        final int len = markup.indexOf(93, i) - i;
                        if (len < 0) {
                            break;
                        }
                        switch (c) {
                            case 42: {
                                current ^= 0x40000000L;
                                break;
                            }
                            case 47: {
                                current ^= 0x20000000L;
                                break;
                            }
                            case 94: {
                                if ((current & 0x6000000L) == 0x6000000L) {
                                    current &= 0xFFFFFFFFF9FFFFFFL;
                                    break;
                                }
                                current |= 0x6000000L;
                                break;
                            }
                            case 46: {
                                if ((current & 0x6000000L) == 0x2000000L) {
                                    current &= 0xFFFFFFFFFDFFFFFFL;
                                    break;
                                }
                                current = ((current & 0xFFFFFFFFF9FFFFFFL) | 0x2000000L);
                                break;
                            }
                            case 61: {
                                if ((current & 0x6000000L) == 0x4000000L) {
                                    current &= 0xFFFFFFFFFBFFFFFFL;
                                    break;
                                }
                                current = ((current & 0xFFFFFFFFF9FFFFFFL) | 0x4000000L);
                                break;
                            }
                            case 95: {
                                current ^= 0x10000000L;
                                break;
                            }
                            case 126: {
                                current ^= 0x8000000L;
                                break;
                            }
                            case 59: {
                                capitalize = !capitalize;
                                capsLock = false;
                                lowerCase = false;
                                break;
                            }
                            case 33: {
                                capsLock = !capsLock;
                                capitalize = false;
                                lowerCase = false;
                                break;
                            }
                            case 44: {
                                lowerCase = !lowerCase;
                                capitalize = false;
                                capsLock = false;
                                break;
                            }
                            case 37: {
                                if (len >= 2) {
                                    current = ((current & 0xFFFFFFFFFF0FFFFFL) | (long)(((scale = ((intFromDec(markup, i + 1, i + len) - 24) / 25 & 0xF)) - 3 & 0xF) << 20));
                                    break;
                                }
                                current &= 0xFFFFFFFFFF0FFFFFL;
                                scale = 3;
                                break;
                            }
                            case 35: {
                                if (len >= 7 && len < 9) {
                                    color = (longFromHex(markup, i + 1, i + 7) << 40 | 0xFE00000000L);
                                }
                                else if (len >= 9) {
                                    color = (longFromHex(markup, i + 1, i + 9) << 32 & 0xFFFFFFFE00000000L);
                                }
                                else {
                                    color = baseColor;
                                }
                                current = ((current & 0xFFFFFFFFL) | color);
                                break;
                            }
                            case 64: {
                                if (this.family == null) {
                                    font = this;
                                    fontIndex = 0;
                                    break;
                                }
                                fontIndex = this.family.fontAliases.get(safeSubstring(markup, i + 1, i + len), 0);
                                current = ((current & 0xFFFFFFFFFFF0FFFFL) | ((long)fontIndex & 0xFL) << 16);
                                font = this.family.connected[fontIndex & 0xF];
                                if (font == null) {
                                    font = this;
                                    break;
                                }
                                break;
                            }
                            case 124: {
                                final int lookupColor = this.colorLookup.getRgba(safeSubstring(markup, i + 1, i + len)) & 0xFFFFFFFE;
                                if (lookupColor == 256) {
                                    color = baseColor;
                                }
                                else {
                                    color = (long)lookupColor << 32;
                                }
                                current = ((current & 0xFFFFFFFFL) | color);
                                break;
                            }
                            default: {
                                final int gdxColor = this.colorLookup.getRgba(safeSubstring(markup, i, i + len)) & 0xFFFFFFFE;
                                if (gdxColor == 256) {
                                    color = baseColor;
                                }
                                else {
                                    color = (long)gdxColor << 32;
                                }
                                current = ((current & 0xFFFFFFFFL) | color);
                                break;
                            }
                        }
                        i += len;
                    }
                }
                else {
                    if (c == 43 && this.nameLookup != null) {
                        final int len2 = markup.indexOf(93, i) - i;
                        if (len2 >= 0) {
                            c = this.nameLookup.get(safeSubstring(markup, i + 1, i + len2), 43);
                        }
                    }
                    if (c == 91) {
                        return current | 0x2L;
                    }
                    return current | (long)c;
                }
            }
        }
        return current | 0x20L;
    }
    
    public static long markupGlyph(final char chr, final String markup, final ColorLookup colorLookup) {
        return markupGlyph(chr, markup, colorLookup, null);
    }
    
    public static long markupGlyph(final char chr, final String markup, final ColorLookup colorLookup, final FontFamily family) {
        boolean capsLock = false;
        boolean lowerCase = false;
        final long COLOR_MASK = -4294967296L;
        final long baseColor = 0xFFFFFFFE00000000L | (long)chr;
        long current;
        long color = current = baseColor;
        for (int i = 0, n = markup.length(); i < n; ++i) {
            final int c;
            if (markup.charAt(i) == '[' && ++i < n && (c = markup.charAt(i)) != 91) {
                if (c == 93) {
                    color = (current = baseColor);
                    capsLock = false;
                    lowerCase = false;
                }
                else {
                    final int len = markup.indexOf(93, i) - i;
                    if (len < 0) {
                        break;
                    }
                    switch (c) {
                        case 42: {
                            current ^= 0x40000000L;
                            break;
                        }
                        case 47: {
                            current ^= 0x20000000L;
                            break;
                        }
                        case 94: {
                            if ((current & 0x6000000L) == 0x6000000L) {
                                current &= 0xFFFFFFFFF9FFFFFFL;
                                break;
                            }
                            current |= 0x6000000L;
                            break;
                        }
                        case 46: {
                            if ((current & 0x6000000L) == 0x2000000L) {
                                current &= 0xFFFFFFFFFDFFFFFFL;
                                break;
                            }
                            current = ((current & 0xFFFFFFFFF9FFFFFFL) | 0x2000000L);
                            break;
                        }
                        case 61: {
                            if ((current & 0x6000000L) == 0x4000000L) {
                                current &= 0xFFFFFFFFFBFFFFFFL;
                                break;
                            }
                            current = ((current & 0xFFFFFFFFF9FFFFFFL) | 0x4000000L);
                            break;
                        }
                        case 95: {
                            current ^= 0x10000000L;
                            break;
                        }
                        case 126: {
                            current ^= 0x8000000L;
                            break;
                        }
                        case 33:
                        case 59: {
                            capsLock = !capsLock;
                            lowerCase = false;
                            break;
                        }
                        case 44: {
                            lowerCase = !lowerCase;
                            capsLock = false;
                            break;
                        }
                        case 37: {
                            if (len >= 2) {
                                current = ((current & 0xFFFFFFFFFF0FFFFFL) | (long)((((intFromDec(markup, i + 1, i + len) - 24) / 25 & 0xF) - 3 & 0xF) << 20));
                                break;
                            }
                            current &= 0xFFFFFFFFFF0FFFFFL;
                            break;
                        }
                        case 64: {
                            if (family == null) {
                                break;
                            }
                            final int fontIndex = family.fontAliases.get(safeSubstring(markup, i + 1, i + len), 0);
                            current = ((current & 0xFFFFFFFFFFF0FFFFL) | ((long)fontIndex & 0xFL) << 16);
                            break;
                        }
                        case 35: {
                            if (len >= 7 && len < 9) {
                                color = (longFromHex(markup, i + 1, i + 7) << 40 | 0xFE00000000L);
                            }
                            else if (len >= 9) {
                                color = (longFromHex(markup, i + 1, i + 9) << 32 & 0xFFFFFFFE00000000L);
                            }
                            else {
                                color = baseColor;
                            }
                            current = ((current & 0xFFFFFFFFL) | color);
                            break;
                        }
                        case 124: {
                            final int lookupColor = colorLookup.getRgba(safeSubstring(markup, i + 1, i + len)) & 0xFFFFFFFE;
                            if (lookupColor == 256) {
                                color = baseColor;
                            }
                            else {
                                color = (long)lookupColor << 32;
                            }
                            current = ((current & 0xFFFFFFFFL) | color);
                            break;
                        }
                        default: {
                            final int gdxColor = colorLookup.getRgba(safeSubstring(markup, i, i + len)) & 0xFFFFFFFE;
                            if (gdxColor == 256) {
                                color = baseColor;
                            }
                            else {
                                color = (long)gdxColor << 32;
                            }
                            current = ((current & 0xFFFFFFFFL) | color);
                            break;
                        }
                    }
                }
            }
        }
        return current;
    }
    
    public Layout regenerateLayout(final Layout changing) {
        if (changing.font == null) {
            return changing;
        }
        if (!changing.font.equals(this)) {
            changing.font = this;
        }
        Font font = null;
        final float targetWidth = changing.getTargetWidth();
        final int oldLength = changing.lines.size;
        final Line firstLine = changing.getLine(0);
        for (int i = 1; i < oldLength; ++i) {
            firstLine.glyphs.addAll(changing.getLine(i).glyphs);
            Line.POOL.free(changing.getLine(i));
        }
        changing.lines.truncate(1);
        for (int ln = 0; ln < changing.lines(); ++ln) {
            final Line line = changing.getLine(ln);
            line.height = 0.0f;
            float drawn = 0.0f;
            int breakPoint = -2;
            int spacingPoint = -2;
            int spacingSpan = 0;
            final LongArray glyphs = line.glyphs;
            int kern = -1;
            for (int j = 0, n = glyphs.size; j < n; ++j) {
                long glyph = glyphs.get(j);
                if (this.family != null) {
                    font = this.family.connected[(int)(glyph >>> 16 & 0xFL)];
                }
                if (font == null) {
                    font = this;
                }
                if ((glyph & 0xFFFFL) == 0xAL) {
                    glyphs.set(j, glyph ^= 0x7L);
                }
                if (font.kerning == null) {
                    final int scale = (int)(glyph + 3145728L >>> 20 & 0xFL);
                    line.height = Math.max(line.height, font.cellHeight * (scale + 1) * 0.25f);
                    float scaleX;
                    if ((char)glyph >= '\ue000' && (char)glyph < '\uf800') {
                        scaleX = (scale + 1) * 0.25f * font.cellHeight / (((GlyphRegion)font.mapping.get((int)(char)glyph, font.defaultValue)).xAdvance * 1.25f);
                    }
                    else {
                        scaleX = font.scaleX * (scale + 1) * 0.25f;
                    }
                    if ((char)glyph == '\r') {
                        final Line next = changing.pushLine();
                        glyphs.pop();
                        if (next == null) {
                            break;
                        }
                        next.height = Math.max(next.height, font.cellHeight * (scale + 1) * 0.25f);
                        final long[] arr = next.glyphs.setSize(glyphs.size - j - 1);
                        System.arraycopy(glyphs.items, j + 1, arr, 0, glyphs.size - j - 1);
                        glyphs.truncate(j);
                        glyphs.add(10L);
                        break;
                    }
                    else {
                        final GlyphRegion tr = (GlyphRegion)font.mapping.get((int)(char)glyph);
                        if (tr != null) {
                            float changedW = xAdvance(font, scaleX, glyph);
                            if (j == 0) {
                                final float ox = tr.offsetX * scaleX;
                                if (ox < 0.0f) {
                                    changedW -= ox;
                                }
                            }
                            if (breakPoint >= 0 && drawn + changedW > targetWidth) {
                                final int cutoff = breakPoint - spacingSpan + 1;
                                Line next2;
                                if (changing.lines() == ln + 1) {
                                    next2 = changing.pushLine();
                                    glyphs.pop();
                                }
                                else {
                                    next2 = changing.getLine(ln + 1);
                                }
                                if (next2 == null) {
                                    glyphs.truncate(cutoff);
                                    break;
                                }
                                next2.height = Math.max(next2.height, font.cellHeight * (scale + 1) * 0.25f);
                                final int nextSize = next2.glyphs.size;
                                final long[] arr2 = next2.glyphs.setSize(nextSize + glyphs.size - cutoff);
                                System.arraycopy(arr2, 0, arr2, glyphs.size - cutoff, nextSize);
                                System.arraycopy(glyphs.items, cutoff, arr2, 0, glyphs.size - cutoff);
                                glyphs.truncate(cutoff);
                                break;
                            }
                            else {
                                if (glyph >>> 32 == 0L) {
                                    if (spacingPoint + 1 < (breakPoint = j)) {
                                        spacingSpan = 0;
                                    }
                                    else {
                                        ++spacingSpan;
                                    }
                                    spacingPoint = j;
                                }
                                else if (Arrays.binarySearch(this.breakChars.items, 0, this.breakChars.size, (char)glyph) >= 0) {
                                    breakPoint = j;
                                    if (Arrays.binarySearch(this.spaceChars.items, 0, this.spaceChars.size, (char)glyph) >= 0) {
                                        if (spacingPoint + 1 < j) {
                                            spacingSpan = 0;
                                        }
                                        else {
                                            ++spacingSpan;
                                        }
                                        spacingPoint = j;
                                    }
                                }
                                drawn += changedW;
                            }
                        }
                    }
                }
                else {
                    final int scale = (int)(glyph + 3145728L >>> 20 & 0xFL);
                    line.height = Math.max(line.height, font.cellHeight * (scale + 1) * 0.25f);
                    float scaleX;
                    if ((char)glyph >= '\ue000' && (char)glyph < '\uf800') {
                        scaleX = (scale + 1) * 0.25f * font.cellHeight / (((GlyphRegion)font.mapping.get((int)(char)glyph, font.defaultValue)).xAdvance * 1.25f);
                    }
                    else {
                        scaleX = font.scaleX * (scale + 1) * 0.25f;
                    }
                    kern = (kern << 16 | (int)(glyph & 0xFFFFL));
                    final float amt = font.kerning.get(kern, 0) * scaleX;
                    if ((char)glyph == '\r') {
                        final Line next = changing.pushLine();
                        glyphs.pop();
                        if (next == null) {
                            break;
                        }
                        next.height = Math.max(next.height, font.cellHeight * (scale + 1) * 0.25f);
                        final long[] arr = next.glyphs.setSize(glyphs.size - j - 1);
                        System.arraycopy(glyphs.items, j + 1, arr, 0, glyphs.size - j - 1);
                        glyphs.truncate(j);
                        glyphs.add(10L);
                        break;
                    }
                    else {
                        final GlyphRegion tr = (GlyphRegion)font.mapping.get((int)(char)glyph);
                        if (tr != null) {
                            float changedW = xAdvance(font, scaleX, glyph);
                            if (j == 0) {
                                final float ox = tr.offsetX * scaleX;
                                if (ox < 0.0f) {
                                    changedW -= ox;
                                }
                            }
                            if (breakPoint >= 0 && drawn + changedW + amt > targetWidth) {
                                final int cutoff = breakPoint - spacingSpan + 1;
                                Line next2;
                                if (changing.lines() == ln + 1) {
                                    next2 = changing.pushLine();
                                    glyphs.pop();
                                }
                                else {
                                    next2 = changing.getLine(ln + 1);
                                }
                                if (next2 == null) {
                                    glyphs.truncate(cutoff);
                                    break;
                                }
                                next2.height = Math.max(next2.height, font.cellHeight * (scale + 1) * 0.25f);
                                final int nextSize = next2.glyphs.size;
                                final long[] arr2 = next2.glyphs.setSize(nextSize + glyphs.size - cutoff);
                                System.arraycopy(arr2, 0, arr2, glyphs.size - cutoff, nextSize);
                                System.arraycopy(glyphs.items, cutoff, arr2, 0, glyphs.size - cutoff);
                                glyphs.truncate(cutoff);
                                break;
                            }
                            else {
                                if (glyph >>> 32 == 0L) {
                                    if (spacingPoint + 1 < (breakPoint = j)) {
                                        spacingSpan = 0;
                                    }
                                    else {
                                        ++spacingSpan;
                                    }
                                    spacingPoint = j;
                                }
                                else if (Arrays.binarySearch(this.breakChars.items, 0, this.breakChars.size, (char)glyph) >= 0) {
                                    breakPoint = j;
                                    if (Arrays.binarySearch(this.spaceChars.items, 0, this.spaceChars.size, (char)glyph) >= 0) {
                                        if (spacingPoint + 1 < j) {
                                            spacingSpan = 0;
                                        }
                                        else {
                                            ++spacingSpan;
                                        }
                                        spacingPoint = j;
                                    }
                                }
                                drawn += changedW + amt;
                            }
                        }
                    }
                }
            }
        }
        this.calculateSize(changing);
        return changing;
    }
    
    public Font setFamily(final FontFamily family) {
        this.family = family;
        return this;
    }
    
    public void resizeDistanceField(final int width, final int height) {
        if (this.distanceField == DistanceFieldType.SDF) {
            if (Gdx.graphics.getBackBufferWidth() == 0 || Gdx.graphics.getBackBufferHeight() == 0) {
                this.actualCrispness = this.distanceFieldCrispness;
            }
            else {
                this.actualCrispness = this.distanceFieldCrispness * (float)Math.pow(4.0, Math.max(width / (float)Gdx.graphics.getBackBufferWidth(), height / (float)Gdx.graphics.getBackBufferHeight()) * 1.9f - 2.0f + this.cellHeight * 0.005f);
            }
        }
        else if (this.distanceField == DistanceFieldType.MSDF) {
            if (Gdx.graphics.getBackBufferWidth() == 0 || Gdx.graphics.getBackBufferHeight() == 0) {
                this.actualCrispness = this.distanceFieldCrispness;
            }
            else {
                this.actualCrispness = this.distanceFieldCrispness * (float)Math.pow(8.0, Math.max(width / (float)Gdx.graphics.getBackBufferWidth(), height / (float)Gdx.graphics.getBackBufferHeight()) * 1.9f - 2.15f + this.cellHeight * 0.01f);
            }
        }
    }
    
    public static int extractColor(final long glyph) {
        return (int)(glyph >>> 32);
    }
    
    public static long applyColor(final long glyph, final int color) {
        return (glyph & 0xFFFFFFFFL) | ((long)color << 32 & 0xFFFFFFFE00000000L);
    }
    
    public static long extractStyle(final long glyph) {
        return glyph & 0x7E000000L;
    }
    
    public static long applyStyle(final long glyph, final long style) {
        return (glyph & 0xFFFFFFFF81FFFFFFL) | (style & 0x7E000000L);
    }
    
    public static float extractScale(final long glyph) {
        return (glyph + 4194304L >>> 20 & 0xFL) * 0.25f;
    }
    
    public static long applyScale(final long glyph, final float scale) {
        return (glyph & 0xFFFFFFFFFF0FFFFFL) | ((long)Math.floor(scale * 4.0 - 4.0) & 0xFL) << 20;
    }
    
    public static char extractChar(final long glyph) {
        final char c = (char)glyph;
        return (c == '\u0002') ? '[' : c;
    }
    
    public static long applyChar(final long glyph, final char c) {
        return (glyph & 0xFFFFFFFFFFFF0000L) | (long)c;
    }
    
    public void dispose() {
        Layout.POOL.free(this.tempLayout);
        if (this.shader != null) {
            this.shader.dispose();
        }
        if (this.whiteBlock != null) {
            this.whiteBlock.dispose();
        }
    }
    
    public static void clearStatic() {
        Line.POOL.clear();
        Layout.POOL.clear();
        TypingConfig.GLOBAL_VARS.clear();
    }
    
    @Override
    public String toString() {
        return "Font '" + this.name + "' at scale " + this.scaleX + " by " + this.scaleY;
    }
    
    static {
        hexCodes = new int[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15 };
    }
    
    public static class GlyphRegion extends TextureRegion
    {
        public float offsetX;
        public float offsetY;
        public float xAdvance;
        
        public GlyphRegion(final TextureRegion textureRegion) {
            this(textureRegion, 0.0f, 0.0f, (float)textureRegion.getRegionWidth());
        }
        
        public GlyphRegion(final TextureAtlas.AtlasRegion atlasRegion) {
            this((TextureRegion)atlasRegion, atlasRegion.offsetX, atlasRegion.offsetY, (float)atlasRegion.originalWidth);
        }
        
        public GlyphRegion(final TextureRegion textureRegion, final float offsetX, final float offsetY, final float xAdvance) {
            super(textureRegion);
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            this.xAdvance = xAdvance;
        }
        
        public GlyphRegion(final TextureRegion textureRegion, final int x, final int y, final int width, final int height) {
            super(textureRegion, x, y, width, height);
            this.offsetX = 0.0f;
            this.offsetY = 0.0f;
            this.xAdvance = (float)width;
        }
        
        public GlyphRegion(final GlyphRegion other) {
            super((TextureRegion)other);
            this.offsetX = other.offsetX;
            this.offsetY = other.offsetY;
            this.xAdvance = other.xAdvance;
        }
        
        public void flip(final boolean x, final boolean y) {
            super.flip(x, y);
            if (x) {
                this.offsetX = -this.offsetX;
                this.xAdvance = -this.xAdvance;
            }
            if (y) {
                this.offsetY = -this.offsetY;
            }
        }
    }
    
    public static class FontFamily
    {
        public final Font[] connected;
        public final CaseInsensitiveIntMap fontAliases;
        
        public FontFamily() {
            this.connected = new Font[16];
            this.fontAliases = new CaseInsensitiveIntMap(48);
        }
        
        public FontFamily(final Font[] fonts) {
            this(fonts, 0, fonts.length);
        }
        
        public FontFamily(final Font[] fonts, final int offset, final int length) {
            this.connected = new Font[16];
            this.fontAliases = new CaseInsensitiveIntMap(48);
            if (fonts == null || fonts.length == 0) {
                return;
            }
            for (int i = offset, a = 0; i < length && i < fonts.length; ++i, ++a) {
                if (fonts[i] != null) {
                    this.connected[a & 0xF] = fonts[i];
                    if (fonts[i].name != null) {
                        this.fontAliases.put(fonts[i].name, a & 0xF);
                    }
                    this.fontAliases.put(String.valueOf(a & 0xF), a & 0xF);
                }
            }
        }
        
        public FontFamily(final String[] aliases, final Font[] fonts) {
            this(aliases, fonts, 0, Math.min(aliases.length, fonts.length));
        }
        
        public FontFamily(final String[] aliases, final Font[] fonts, final int offset, final int length) {
            this.connected = new Font[16];
            this.fontAliases = new CaseInsensitiveIntMap(48);
            if (aliases == null || fonts == null || (aliases.length & fonts.length) == 0x0) {
                return;
            }
            for (int i = offset, a = 0; i < length && i < aliases.length && i < fonts.length; ++i, ++a) {
                if (fonts[i] != null) {
                    this.connected[a & 0xF] = fonts[i];
                    this.fontAliases.put(aliases[i], a & 0xF);
                    if (fonts[i].name != null) {
                        this.fontAliases.put(fonts[i].name, a & 0xF);
                    }
                    this.fontAliases.put(String.valueOf(a & 0xF), a & 0xF);
                }
            }
        }
        
        public FontFamily(final OrderedMap<String, Font> map) {
            this.connected = new Font[16];
            this.fontAliases = new CaseInsensitiveIntMap(48);
            final Array<String> ks = (Array<String>)map.orderedKeys();
            for (int i = 0; i < map.size && i < 16; ++i) {
                final String name = (String)ks.get(i);
                if ((this.connected[i] = (Font)map.get(name)) != null) {
                    this.fontAliases.put(name, i);
                    this.fontAliases.put(this.connected[i].name, i);
                    this.fontAliases.put(String.valueOf(i), i);
                }
            }
        }
        
        public FontFamily(final Skin skin) {
            this.connected = new Font[16];
            this.fontAliases = new CaseInsensitiveIntMap(48);
            final ObjectMap<String, BitmapFont> map = (ObjectMap<String, BitmapFont>)skin.getAll((Class)BitmapFont.class);
            final Array<String> keys = (Array<String>)map.keys().toArray();
            for (int i = 0; i < map.size && i < 16; ++i) {
                final String name = (String)keys.get(i);
                final Font font = new Font((BitmapFont)map.get(name));
                font.name = name;
                font.family = this;
                this.connected[i] = font;
                this.fontAliases.put(name, i);
                this.fontAliases.put(this.connected[i].name, i);
                this.fontAliases.put(String.valueOf(i), i);
            }
        }
        
        public FontFamily(final FontFamily other) {
            this.connected = new Font[16];
            this.fontAliases = new CaseInsensitiveIntMap(48);
            System.arraycopy(other.connected, 0, this.connected, 0, 16);
            this.fontAliases.putAll(other.fontAliases);
        }
        
        public Font get(final String name) {
            if (name == null) {
                return null;
            }
            return this.connected[this.fontAliases.get(name, 0) & 0xF];
        }
    }
    
    public enum DistanceFieldType
    {
        STANDARD, 
        SDF, 
        MSDF;
        
        private static /* synthetic */ DistanceFieldType[] $values() {
            return new DistanceFieldType[] { DistanceFieldType.STANDARD, DistanceFieldType.SDF, DistanceFieldType.MSDF };
        }
    }
}
