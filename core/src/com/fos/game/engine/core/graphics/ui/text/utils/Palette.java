// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.utils;

import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.ObjectIntMap;

public final class Palette
{
    public static final ObjectIntMap<String> ALIASES;
    public static final ObjectIntMap<String> NAMED;
    public static final IntArray LIST;
    public static final int transparent = 0;
    public static final int black = 255;
    public static final int gray = -2139062017;
    public static final int silver = -1229539585;
    public static final int white = -1;
    public static final int red = -16776961;
    public static final int orange = -8453889;
    public static final int yellow = -65281;
    public static final int green = 16711935;
    public static final int blue = 65535;
    public static final int indigo = 1376772351;
    public static final int violet = -1874792449;
    public static final int purple = -1073676289;
    public static final int brown = -1890108417;
    public static final int pink = -6233857;
    public static final int magenta = -184486401;
    public static final int brick = -716027137;
    public static final int ember = -178638081;
    public static final int salmon = -10329345;
    public static final int chocolate = 1748506879;
    public static final int tan = -759919361;
    public static final int bronze = -829541889;
    public static final int cinnamon = -764862977;
    public static final int apricot = -5756673;
    public static final int peach = -4226561;
    public static final int pear = -740085505;
    public static final int saffron = -2813697;
    public static final int butter = -882433;
    public static final int chartreuse = -922795521;
    public static final int cactus = 815792383;
    public static final int lime = -1814888193;
    public static final int olive = -2122317569;
    public static final int fern = 1316569855;
    public static final int moss = 541460735;
    public static final int celery = 2113893375;
    public static final int sage = -1411136001;
    public static final int jade = 1069498367;
    public static final int cyan = 16777215;
    public static final int mint = 2147472639;
    public static final int teal = 8355839;
    public static final int turquoise = 785828351;
    public static final int sky = 281075967;
    public static final int cobalt = 4631551;
    public static final int denim = 814266623;
    public static final int navy = 33023;
    public static final int lavender = -1181614081;
    public static final int plum = -1106393345;
    public static final int mauve = -1418482689;
    public static final int rose = -434210561;
    public static final int raspberry = -1860945921;
    public static final int YELLOW = -65281;
    public static final int BLUE = 65535;
    public static final int GOLD = -2686721;
    public static final int GRAY = 2139062271;
    public static final int ORANGE = -5963521;
    public static final int MAGENTA = -16711681;
    public static final int FIREBRICK = -1306385665;
    public static final int SCARLET = -13361921;
    public static final int WHITE = -1;
    public static final int SKY = -2016482305;
    public static final int FOREST = 579543807;
    public static final int GREEN = 16711935;
    public static final int CHARTREUSE = 2147418367;
    public static final int MAROON = -1339006721;
    public static final int RED = -16776961;
    public static final int CYAN = 16777215;
    public static final int BLACK = 255;
    public static final int VIOLET = -293409025;
    public static final int CORAL = -8433409;
    public static final int ROYAL = 1097458175;
    public static final int LIME = 852308735;
    public static final int CLEAR = 0;
    public static final int LIGHT_GRAY = -1077952513;
    public static final int NAVY = 32767;
    public static final int BROWN = -1958407169;
    public static final int SALMON = -92245249;
    public static final int PURPLE = -1608453889;
    public static final int DARK_GRAY = 1061109759;
    public static final int SLATE = 1887473919;
    public static final int TAN = -759919361;
    public static final int PINK = -9849601;
    public static final int OLIVE = 1804477439;
    public static final int TEAL = 8355839;
    public static final int GOLDENROD = -626712321;
    public static final Array<String> NAMES;
    
    private Palette() {
    }
    
    public static void appendToKnownColors() {
        for (final ObjectIntMap.Entry<String> ent : Palette.NAMED) {
            final Color editing = new Color();
            Color.rgba8888ToColor(editing, ent.value);
            Colors.put((String)ent.key, editing);
        }
    }
    
    public static boolean addColor(final String name, final int rgba8888) {
        if (Palette.NAMED.containsKey(name)) {
            return false;
        }
        Palette.NAMED.put(name, rgba8888);
        Palette.LIST.add(rgba8888);
        Palette.NAMES.add(name);
        Palette.NAMES.sort();
        return true;
    }
    
    static {
        ALIASES = new ObjectIntMap(20);
        NAMED = new ObjectIntMap(84);
        LIST = new IntArray(84);
        Palette.NAMED.put("transparent", 0);
        Palette.LIST.add(0);
        Palette.NAMED.put("black", 255);
        Palette.LIST.add(255);
        Palette.NAMED.put("gray", -2139062017);
        Palette.LIST.add(-2139062017);
        Palette.NAMED.put("silver", -1229539585);
        Palette.LIST.add(-1229539585);
        Palette.NAMED.put("white", -1);
        Palette.LIST.add(-1);
        Palette.NAMED.put("red", -16776961);
        Palette.LIST.add(-16776961);
        Palette.NAMED.put("orange", -8453889);
        Palette.LIST.add(-8453889);
        Palette.NAMED.put("yellow", -65281);
        Palette.LIST.add(-65281);
        Palette.NAMED.put("green", 16711935);
        Palette.LIST.add(16711935);
        Palette.NAMED.put("blue", 65535);
        Palette.LIST.add(65535);
        Palette.NAMED.put("indigo", 1376772351);
        Palette.LIST.add(1376772351);
        Palette.NAMED.put("violet", -1874792449);
        Palette.LIST.add(-1874792449);
        Palette.NAMED.put("purple", -1073676289);
        Palette.LIST.add(-1073676289);
        Palette.NAMED.put("brown", -1890108417);
        Palette.LIST.add(-1890108417);
        Palette.NAMED.put("pink", -6233857);
        Palette.LIST.add(-6233857);
        Palette.NAMED.put("magenta", -184486401);
        Palette.LIST.add(-184486401);
        Palette.NAMED.put("brick", -716027137);
        Palette.LIST.add(-716027137);
        Palette.NAMED.put("ember", -178638081);
        Palette.LIST.add(-178638081);
        Palette.NAMED.put("salmon", -10329345);
        Palette.LIST.add(-10329345);
        Palette.NAMED.put("chocolate", 1748506879);
        Palette.LIST.add(1748506879);
        Palette.NAMED.put("tan", -759919361);
        Palette.LIST.add(-759919361);
        Palette.NAMED.put("bronze", -829541889);
        Palette.LIST.add(-829541889);
        Palette.NAMED.put("cinnamon", -764862977);
        Palette.LIST.add(-764862977);
        Palette.NAMED.put("apricot", -5756673);
        Palette.LIST.add(-5756673);
        Palette.NAMED.put("peach", -4226561);
        Palette.LIST.add(-4226561);
        Palette.NAMED.put("pear", -740085505);
        Palette.LIST.add(-740085505);
        Palette.NAMED.put("saffron", -2813697);
        Palette.LIST.add(-2813697);
        Palette.NAMED.put("butter", -882433);
        Palette.LIST.add(-882433);
        Palette.NAMED.put("chartreuse", -922795521);
        Palette.LIST.add(-922795521);
        Palette.NAMED.put("cactus", 815792383);
        Palette.LIST.add(815792383);
        Palette.NAMED.put("lime", -1814888193);
        Palette.LIST.add(-1814888193);
        Palette.NAMED.put("olive", -2122317569);
        Palette.LIST.add(-2122317569);
        Palette.NAMED.put("fern", 1316569855);
        Palette.LIST.add(1316569855);
        Palette.NAMED.put("moss", 541460735);
        Palette.LIST.add(541460735);
        Palette.NAMED.put("celery", 2113893375);
        Palette.LIST.add(2113893375);
        Palette.NAMED.put("sage", -1411136001);
        Palette.LIST.add(-1411136001);
        Palette.NAMED.put("jade", 1069498367);
        Palette.LIST.add(1069498367);
        Palette.NAMED.put("cyan", 16777215);
        Palette.LIST.add(16777215);
        Palette.NAMED.put("mint", 2147472639);
        Palette.LIST.add(2147472639);
        Palette.NAMED.put("teal", 8355839);
        Palette.LIST.add(8355839);
        Palette.NAMED.put("turquoise", 785828351);
        Palette.LIST.add(785828351);
        Palette.NAMED.put("sky", 281075967);
        Palette.LIST.add(281075967);
        Palette.NAMED.put("cobalt", 4631551);
        Palette.LIST.add(4631551);
        Palette.NAMED.put("denim", 814266623);
        Palette.LIST.add(814266623);
        Palette.NAMED.put("navy", 33023);
        Palette.LIST.add(33023);
        Palette.NAMED.put("lavender", -1181614081);
        Palette.LIST.add(-1181614081);
        Palette.NAMED.put("plum", -1106393345);
        Palette.LIST.add(-1106393345);
        Palette.NAMED.put("mauve", -1418482689);
        Palette.LIST.add(-1418482689);
        Palette.NAMED.put("rose", -434210561);
        Palette.LIST.add(-434210561);
        Palette.NAMED.put("raspberry", -1860945921);
        Palette.LIST.add(-1860945921);
        Palette.NAMED.put("YELLOW", -65281);
        Palette.LIST.add(-65281);
        Palette.NAMED.put("BLUE", 65535);
        Palette.LIST.add(65535);
        Palette.NAMED.put("GOLD", -2686721);
        Palette.LIST.add(-2686721);
        Palette.NAMED.put("GRAY", 2139062271);
        Palette.LIST.add(2139062271);
        Palette.NAMED.put("ORANGE", -5963521);
        Palette.LIST.add(-5963521);
        Palette.NAMED.put("MAGENTA", -16711681);
        Palette.LIST.add(-16711681);
        Palette.NAMED.put("FIREBRICK", -1306385665);
        Palette.LIST.add(-1306385665);
        Palette.NAMED.put("SCARLET", -13361921);
        Palette.LIST.add(-13361921);
        Palette.NAMED.put("WHITE", -1);
        Palette.LIST.add(-1);
        Palette.NAMED.put("SKY", -2016482305);
        Palette.LIST.add(-2016482305);
        Palette.NAMED.put("FOREST", 579543807);
        Palette.LIST.add(579543807);
        Palette.NAMED.put("GREEN", 16711935);
        Palette.LIST.add(16711935);
        Palette.NAMED.put("CHARTREUSE", 2147418367);
        Palette.LIST.add(2147418367);
        Palette.NAMED.put("MAROON", -1339006721);
        Palette.LIST.add(-1339006721);
        Palette.NAMED.put("RED", -16776961);
        Palette.LIST.add(-16776961);
        Palette.NAMED.put("CYAN", 16777215);
        Palette.LIST.add(16777215);
        Palette.NAMED.put("BLACK", 255);
        Palette.LIST.add(255);
        Palette.NAMED.put("VIOLET", -293409025);
        Palette.LIST.add(-293409025);
        Palette.NAMED.put("CORAL", -8433409);
        Palette.LIST.add(-8433409);
        Palette.NAMED.put("ROYAL", 1097458175);
        Palette.LIST.add(1097458175);
        Palette.NAMED.put("LIME", 852308735);
        Palette.LIST.add(852308735);
        Palette.NAMED.put("CLEAR", 0);
        Palette.LIST.add(0);
        Palette.NAMED.put("LIGHT_GRAY", -1077952513);
        Palette.LIST.add(-1077952513);
        Palette.NAMED.put("NAVY", 32767);
        Palette.LIST.add(32767);
        Palette.NAMED.put("BROWN", -1958407169);
        Palette.LIST.add(-1958407169);
        Palette.NAMED.put("SALMON", -92245249);
        Palette.LIST.add(-92245249);
        Palette.NAMED.put("PURPLE", -1608453889);
        Palette.LIST.add(-1608453889);
        Palette.NAMED.put("DARK_GRAY", 1061109759);
        Palette.LIST.add(1061109759);
        Palette.NAMED.put("SLATE", 1887473919);
        Palette.LIST.add(1887473919);
        Palette.NAMED.put("TAN", -759919361);
        Palette.LIST.add(-759919361);
        Palette.NAMED.put("PINK", -9849601);
        Palette.LIST.add(-9849601);
        Palette.NAMED.put("OLIVE", 1804477439);
        Palette.LIST.add(1804477439);
        Palette.NAMED.put("TEAL", 8355839);
        Palette.LIST.add(8355839);
        Palette.NAMED.put("GOLDENROD", -626712321);
        Palette.LIST.add(-626712321);
        Palette.ALIASES.put("grey", -2139062017);
        Palette.ALIASES.put("gold", -2813697);
        Palette.ALIASES.put("puce", -1418482689);
        Palette.ALIASES.put("sand", -759919361);
        Palette.ALIASES.put("skin", -4226561);
        Palette.ALIASES.put("coral", -10329345);
        Palette.ALIASES.put("azure", 281075967);
        Palette.ALIASES.put("ocean", 8355839);
        Palette.ALIASES.put("sapphire", 4631551);
        Palette.NAMED.putAll((ObjectIntMap)Palette.ALIASES);
        (NAMES = Palette.NAMED.keys().toArray()).sort();
    }
}
