// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.LifecycleListener;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData;
import com.badlogic.gdx.utils.*;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Comparator;

public final class KnownFonts implements LifecycleListener
{
    private static KnownFonts instance;
    private String prefix;
    private Font astarry;
    private Font astarryMSDF;
    private Font bitter;
    private Font canada;
    private Font cascadiaMono;
    private Font cascadiaMonoMSDF;
    private Font caveat;
    private Font cozette;
    private Font dejaVuSansMono;
    private Font gentium;
    private Font gentiumSDF;
    private Font hanazono;
    private Font ibm8x16;
    private Font inconsolata;
    private Font inconsolataMSDF;
    private Font iosevka;
    private Font iosevkaMSDF;
    private Font iosevkaSDF;
    private Font iosevkaSlab;
    private Font iosevkaSlabMSDF;
    private Font iosevkaSlabSDF;
    private Font kingthingsFoundation;
    private Font libertinusSerif;
    private Font openSans;
    private Font oxanium;
    private Font quanPixel;
    private Font robotoCondensed;
    private Font tangerine;
    private Font tangerineSDF;
    private Font kaffeesatz;
    private TextureAtlas twemoji;
    
    private KnownFonts() {
        this.prefix = "";
        if (Gdx.app == null) {
            throw new IllegalStateException("Gdx.app cannot be null; initialize KnownFonts in create() or later.");
        }
        Gdx.app.addLifecycleListener((LifecycleListener)this);
    }
    
    private static void initialize() {
        if (KnownFonts.instance == null) {
            KnownFonts.instance = new KnownFonts();
        }
    }
    
    public static void setAssetPrefix(final String prefix) {
        initialize();
        if (prefix != null) {
            KnownFonts.instance.prefix = prefix;
        }
    }
    
    public static Font getAStarry() {
        initialize();
        if (KnownFonts.instance.astarry == null) {
            try {
                KnownFonts.instance.astarry = new Font(KnownFonts.instance.prefix + "AStarry-standard.fnt", KnownFonts.instance.prefix + "AStarry-standard.png", Font.DistanceFieldType.STANDARD, 0.0f, 16.0f, 0.0f, 0.0f, true).scaleTo(8.0f, 8.0f).setTextureFilter().setName("A Starry");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (KnownFonts.instance.astarry != null) {
            return new Font(KnownFonts.instance.astarry);
        }
        throw new RuntimeException("Assets for getAStarry() not found.");
    }
    
    public static Font getAStarryMSDF() {
        initialize();
        if (KnownFonts.instance.astarryMSDF == null) {
            try {
                KnownFonts.instance.astarryMSDF = new Font(KnownFonts.instance.prefix + "AStarry-msdf.fnt", KnownFonts.instance.prefix + "AStarry-msdf.png", Font.DistanceFieldType.MSDF, -12.0f, -12.0f, 0.0f, 0.0f, false).scaleTo(10.0f, 10.0f).setCrispness(2.0f).setName("A Starry (MSDF)");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (KnownFonts.instance.astarryMSDF != null) {
            return new Font(KnownFonts.instance.astarryMSDF);
        }
        throw new RuntimeException("Assets for getAStarryMSDF() not found.");
    }
    
    public static Font getBitter() {
        initialize();
        if (KnownFonts.instance.bitter == null) {
            try {
                KnownFonts.instance.bitter = new Font(KnownFonts.instance.prefix + "Bitter-standard.fnt", KnownFonts.instance.prefix + "Bitter-standard.png", Font.DistanceFieldType.STANDARD, 0.0f, -48.0f, 0.0f, 0.0f, true).scaleTo(33.0f, 30.0f).setTextureFilter().setName("Bitter");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (KnownFonts.instance.bitter != null) {
            return new Font(KnownFonts.instance.bitter);
        }
        throw new RuntimeException("Assets for getBitter() not found.");
    }
    
    public static Font getCanada() {
        initialize();
        if (KnownFonts.instance.canada == null) {
            try {
                KnownFonts.instance.canada = new Font(KnownFonts.instance.prefix + "Canada1500-standard.fnt", KnownFonts.instance.prefix + "Canada1500-standard.png", Font.DistanceFieldType.STANDARD, 0.0f, 0.0f, 0.0f, 0.0f, true).scaleTo(30.0f, 35.0f).setTextureFilter().setName("Canada1500");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (KnownFonts.instance.canada != null) {
            return new Font(KnownFonts.instance.canada);
        }
        throw new RuntimeException("Assets for getCanada() not found.");
    }
    
    public static Font getCascadiaMono() {
        initialize();
        if (KnownFonts.instance.cascadiaMono == null) {
            try {
                KnownFonts.instance.cascadiaMono = new Font(KnownFonts.instance.prefix + "CascadiaMono-standard.fnt", KnownFonts.instance.prefix + "CascadiaMono-standard.png", Font.DistanceFieldType.STANDARD, 0.0f, 0.0f, 0.0f, 0.0f, true).setTextureFilter().scaleTo(10.0f, 20.0f).setName("Cascadia Mono");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (KnownFonts.instance.cascadiaMono != null) {
            return new Font(KnownFonts.instance.cascadiaMono);
        }
        throw new RuntimeException("Assets for getCascadiaMono() not found.");
    }
    
    public static Font getCascadiaMonoMSDF() {
        initialize();
        if (KnownFonts.instance.cascadiaMonoMSDF == null) {
            try {
                KnownFonts.instance.cascadiaMonoMSDF = new Font(KnownFonts.instance.prefix + "CascadiaMono-msdf.fnt", KnownFonts.instance.prefix + "CascadiaMono-msdf.png", Font.DistanceFieldType.MSDF, 0.0f, 0.0f, 0.0f, 0.0f, true).scaleTo(9.0f, 16.0f).setName("Cascadia Mono (MSDF)");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (KnownFonts.instance.cascadiaMonoMSDF != null) {
            return new Font(KnownFonts.instance.cascadiaMonoMSDF);
        }
        throw new RuntimeException("Assets for getCascadiaMonoMSDF() not found.");
    }
    
    public static Font getCaveat() {
        initialize();
        if (KnownFonts.instance.caveat == null) {
            try {
                KnownFonts.instance.caveat = new Font(KnownFonts.instance.prefix + "Caveat-standard.fnt", KnownFonts.instance.prefix + "Caveat-standard.png", Font.DistanceFieldType.STANDARD, 0.0f, 16.0f, 0.0f, 0.0f, true).scaleTo(32.0f, 32.0f).setTextureFilter().setName("Caveat");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (KnownFonts.instance.caveat != null) {
            return new Font(KnownFonts.instance.caveat);
        }
        throw new RuntimeException("Assets for getCaveat() not found.");
    }
    
    public static Font getCozette() {
        initialize();
        if (KnownFonts.instance.cozette == null) {
            try {
                KnownFonts.instance.cozette = new Font(KnownFonts.instance.prefix + "Cozette-standard.fnt", KnownFonts.instance.prefix + "Cozette-standard.png", Font.DistanceFieldType.STANDARD, 0.0f, 2.0f, 0.0f, 0.0f, false).useIntegerPositions(true).setName("Cozette");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (KnownFonts.instance.cozette != null) {
            return new Font(KnownFonts.instance.cozette);
        }
        throw new RuntimeException("Assets for getCozette() not found.");
    }
    
    public static Font getDejaVuSansMono() {
        initialize();
        if (KnownFonts.instance.dejaVuSansMono == null) {
            try {
                KnownFonts.instance.dejaVuSansMono = new Font(KnownFonts.instance.prefix + "DejaVuSansMono-msdf.fnt", KnownFonts.instance.prefix + "DejaVuSansMono-msdf.png", Font.DistanceFieldType.MSDF, 0.0f, -8.0f, 0.0f, 0.0f, true).scaleTo(9.0f, 20.0f).setName("DejaVu Sans Mono (MSDF)");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (KnownFonts.instance.dejaVuSansMono != null) {
            return new Font(KnownFonts.instance.dejaVuSansMono);
        }
        throw new RuntimeException("Assets for getDejaVuSansMono() not found.");
    }
    
    public static Font getGentium() {
        initialize();
        if (KnownFonts.instance.gentium == null) {
            try {
                KnownFonts.instance.gentium = new Font(KnownFonts.instance.prefix + "Gentium-standard.fnt", KnownFonts.instance.prefix + "Gentium-standard.png", Font.DistanceFieldType.STANDARD, 0.0f, 6.0f, 0.0f, 0.0f, true).scaleTo(31.0f, 35.0f).setTextureFilter().setName("Gentium");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (KnownFonts.instance.gentium != null) {
            return new Font(KnownFonts.instance.gentium);
        }
        throw new RuntimeException("Assets for getGentium() not found.");
    }
    
    public static Font getGentiumSDF() {
        initialize();
        if (KnownFonts.instance.gentiumSDF == null) {
            try {
                KnownFonts.instance.gentiumSDF = new Font(KnownFonts.instance.prefix + "Gentium-sdf.fnt", KnownFonts.instance.prefix + "Gentium-sdf.png", Font.DistanceFieldType.SDF, 4.0f, -20.0f, 0.0f, 0.0f, true).scaleTo(50.0f, 45.0f).adjustLineHeight(0.625f).setCrispness(1.5f).setName("Gentium (SDF)");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (KnownFonts.instance.gentiumSDF != null) {
            return new Font(KnownFonts.instance.gentiumSDF);
        }
        throw new RuntimeException("Assets for getGentiumSDF() not found.");
    }
    
    public static Font getHanazono() {
        initialize();
        if (KnownFonts.instance.hanazono == null) {
            try {
                KnownFonts.instance.hanazono = new Font(KnownFonts.instance.prefix + "Hanazono-standard.fnt", KnownFonts.instance.prefix + "Hanazono-standard.png", Font.DistanceFieldType.STANDARD, 0.0f, 0.0f, 0.0f, 0.0f, false).scaleTo(16.0f, 20.0f).setTextureFilter().setName("Hanazono");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (KnownFonts.instance.hanazono != null) {
            return new Font(KnownFonts.instance.hanazono);
        }
        throw new RuntimeException("Assets for getHanazono() not found.");
    }
    
    public static Font getIBM8x16() {
        initialize();
        if (KnownFonts.instance.ibm8x16 == null) {
            try {
                KnownFonts.instance.ibm8x16 = new Font(KnownFonts.instance.prefix, "IBM-8x16-standard.font", true).fitCell(8.0f, 16.0f, false).setName("IBM 8x16");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (KnownFonts.instance.ibm8x16 != null) {
            return new Font(KnownFonts.instance.ibm8x16);
        }
        throw new RuntimeException("Assets for getIBM8x16() not found.");
    }
    
    public static Font getInconsolata() {
        initialize();
        if (KnownFonts.instance.inconsolata == null) {
            try {
                KnownFonts.instance.inconsolata = new Font(KnownFonts.instance.prefix + "Inconsolata-LGC-Custom-standard.fnt", KnownFonts.instance.prefix + "Inconsolata-LGC-Custom-standard.png", Font.DistanceFieldType.STANDARD, 0.0f, 6.0f, -4.0f, 0.0f, true).scaleTo(10.0f, 26.0f).setTextureFilter().setName("Inconsolata LGC");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (KnownFonts.instance.inconsolata != null) {
            return new Font(KnownFonts.instance.inconsolata);
        }
        throw new RuntimeException("Assets for getInconsolata() not found.");
    }
    
    public static Font getInconsolataMSDF() {
        initialize();
        if (KnownFonts.instance.inconsolataMSDF == null) {
            try {
                KnownFonts.instance.inconsolataMSDF = new Font(KnownFonts.instance.prefix + "Inconsolata-LGC-Custom-msdf.fnt", KnownFonts.instance.prefix + "Inconsolata-LGC-Custom-msdf.png", Font.DistanceFieldType.MSDF, 0.0f, -5.0f, -12.0f, 0.0f, true).scaleTo(10.0f, 26.0f).setName("Inconsolata LGC (MSDF)");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (KnownFonts.instance.inconsolataMSDF != null) {
            return new Font(KnownFonts.instance.inconsolataMSDF);
        }
        throw new RuntimeException("Assets for getInconsolataMSDF() not found.");
    }
    
    public static Font getIosevka() {
        initialize();
        if (KnownFonts.instance.iosevka == null) {
            try {
                KnownFonts.instance.iosevka = new Font(KnownFonts.instance.prefix + "Iosevka-standard.fnt", KnownFonts.instance.prefix + "Iosevka-standard.png", Font.DistanceFieldType.STANDARD, -2.0f, 12.0f, 0.0f, 0.0f, true).scaleTo(10.0f, 24.0f).fitCell(10.0f, 24.0f, false).setTextureFilter().setName("Iosevka");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (KnownFonts.instance.iosevka != null) {
            return new Font(KnownFonts.instance.iosevka);
        }
        throw new RuntimeException("Assets for getIosevka() not found.");
    }
    
    public static Font getIosevkaMSDF() {
        initialize();
        if (KnownFonts.instance.iosevkaMSDF == null) {
            try {
                KnownFonts.instance.iosevkaMSDF = new Font(KnownFonts.instance.prefix + "Iosevka-msdf.fnt", KnownFonts.instance.prefix + "Iosevka-msdf.png", Font.DistanceFieldType.MSDF, 0.0f, -15.0f, 0.0f, 0.0f, true).setCrispness(0.75f).scaleTo(12.0f, 26.0f).fitCell(10.0f, 25.0f, false).setName("Iosevka (MSDF)");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (KnownFonts.instance.iosevkaMSDF != null) {
            return new Font(KnownFonts.instance.iosevkaMSDF);
        }
        throw new RuntimeException("Assets for getIosevkaMSDF() not found.");
    }
    
    public static Font getIosevkaSDF() {
        initialize();
        if (KnownFonts.instance.iosevkaSDF == null) {
            try {
                KnownFonts.instance.iosevkaSDF = new Font(KnownFonts.instance.prefix + "Iosevka-sdf.fnt", KnownFonts.instance.prefix + "Iosevka-sdf.png", Font.DistanceFieldType.SDF, 2.0f, -18.0f, -2.0f, 0.0f, true).setCrispness(0.75f).scaleTo(12.0f, 26.0f).fitCell(10.0f, 25.0f, false).setName("Iosevka (SDF)");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (KnownFonts.instance.iosevkaSDF != null) {
            return new Font(KnownFonts.instance.iosevkaSDF);
        }
        throw new RuntimeException("Assets for getIosevkaSDF() not found.");
    }
    
    public static Font getIosevkaSlab() {
        initialize();
        if (KnownFonts.instance.iosevkaSlab == null) {
            try {
                KnownFonts.instance.iosevkaSlab = new Font(KnownFonts.instance.prefix + "Iosevka-Slab-standard.fnt", KnownFonts.instance.prefix + "Iosevka-Slab-standard.png", Font.DistanceFieldType.STANDARD, 0.0f, 12.0f, 0.0f, 0.0f, true).scaleTo(10.0f, 24.0f).fitCell(10.0f, 24.0f, false).setTextureFilter().setName("Iosevka Slab");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (KnownFonts.instance.iosevkaSlab != null) {
            return new Font(KnownFonts.instance.iosevkaSlab);
        }
        throw new RuntimeException("Assets for getIosevkaSlab() not found.");
    }
    
    public static Font getIosevkaSlabMSDF() {
        initialize();
        if (KnownFonts.instance.iosevkaSlabMSDF == null) {
            try {
                KnownFonts.instance.iosevkaSlabMSDF = new Font(KnownFonts.instance.prefix + "Iosevka-Slab-msdf.fnt", KnownFonts.instance.prefix + "Iosevka-Slab-msdf.png", Font.DistanceFieldType.MSDF, 0.0f, -15.0f, 0.0f, 0.0f, true).setCrispness(0.75f).scaleTo(12.0f, 26.0f).fitCell(10.0f, 25.0f, false).setName("Iosevka Slab (MSDF)");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (KnownFonts.instance.iosevkaSlabMSDF != null) {
            return new Font(KnownFonts.instance.iosevkaSlabMSDF);
        }
        throw new RuntimeException("Assets for getIosevkaSlabMSDF() not found.");
    }
    
    public static Font getIosevkaSlabSDF() {
        initialize();
        if (KnownFonts.instance.iosevkaSlabSDF == null) {
            try {
                KnownFonts.instance.iosevkaSlabSDF = new Font(KnownFonts.instance.prefix + "Iosevka-Slab-sdf.fnt", KnownFonts.instance.prefix + "Iosevka-Slab-sdf.png", Font.DistanceFieldType.SDF, 2.0f, -18.0f, -2.0f, 0.0f, true).setCrispness(0.75f).scaleTo(12.0f, 26.0f).fitCell(10.0f, 25.0f, false).setName("Iosevka Slab (SDF)");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (KnownFonts.instance.iosevkaSlabSDF != null) {
            return new Font(KnownFonts.instance.iosevkaSlabSDF);
        }
        throw new RuntimeException("Assets for getIosevkaSlabSDF() not found.");
    }
    
    public static Font getKingthingsFoundation() {
        initialize();
        if (KnownFonts.instance.kingthingsFoundation == null) {
            try {
                KnownFonts.instance.kingthingsFoundation = new Font(KnownFonts.instance.prefix + "KingthingsFoundation-standard.fnt", KnownFonts.instance.prefix + "KingthingsFoundation-standard.png", Font.DistanceFieldType.STANDARD, 0.0f, 40.0f, 0.0f, 0.0f, true).scaleTo(23.0f, 30.0f).adjustLineHeight(1.125f).setTextureFilter().setName("KingThings Foundation");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (KnownFonts.instance.kingthingsFoundation != null) {
            return new Font(KnownFonts.instance.kingthingsFoundation);
        }
        throw new RuntimeException("Assets for getKingthingsFoundation() not found.");
    }
    
    public static Font getLibertinusSerif() {
        initialize();
        if (KnownFonts.instance.libertinusSerif == null) {
            try {
                KnownFonts.instance.libertinusSerif = new Font(KnownFonts.instance.prefix + "LibertinusSerif-standard.fnt", KnownFonts.instance.prefix + "LibertinusSerif-standard.png", Font.DistanceFieldType.STANDARD, 0.0f, 0.0f, 0.0f, 0.0f, true).scaleTo(40.0f, 34.0f).setTextureFilter().setName("Libertinus Serif");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (KnownFonts.instance.libertinusSerif != null) {
            return new Font(KnownFonts.instance.libertinusSerif);
        }
        throw new RuntimeException("Assets for getLibertinusSerif() not found.");
    }
    
    public static Font getOpenSans() {
        initialize();
        if (KnownFonts.instance.openSans == null) {
            try {
                KnownFonts.instance.openSans = new Font(KnownFonts.instance.prefix + "OpenSans-standard.fnt", KnownFonts.instance.prefix + "OpenSans-standard.png", Font.DistanceFieldType.STANDARD, 0.0f, 4.0f, 0.0f, 0.0f, true).scaleTo(20.0f, 28.0f).adjustLineHeight(0.9f).setTextureFilter().setName("OpenSans");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (KnownFonts.instance.openSans != null) {
            return new Font(KnownFonts.instance.openSans);
        }
        throw new RuntimeException("Assets for getOpenSans() not found.");
    }
    
    public static Font getOxanium() {
        initialize();
        if (KnownFonts.instance.oxanium == null) {
            try {
                KnownFonts.instance.oxanium = new Font(KnownFonts.instance.prefix + "Oxanium-standard.fnt", KnownFonts.instance.prefix + "Oxanium-standard.png", Font.DistanceFieldType.STANDARD, 0.0f, -2.0f, -4.0f, 0.0f, true).scaleTo(31.0f, 35.0f).setTextureFilter().setName("Oxanium");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (KnownFonts.instance.oxanium != null) {
            return new Font(KnownFonts.instance.oxanium);
        }
        throw new RuntimeException("Assets for getOxanium() not found.");
    }
    
    public static Font getQuanPixel() {
        initialize();
        if (KnownFonts.instance.quanPixel == null) {
            try {
                KnownFonts.instance.quanPixel = new Font(KnownFonts.instance.prefix + "QuanPixel-standard.fnt", KnownFonts.instance.prefix + "QuanPixel-standard.png", Font.DistanceFieldType.STANDARD, 0.0f, 2.0f, 0.0f, 0.0f, false).useIntegerPositions(true).setName("QuanPixel");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (KnownFonts.instance.quanPixel != null) {
            return new Font(KnownFonts.instance.quanPixel);
        }
        throw new RuntimeException("Assets for getQuanPixel() not found.");
    }
    
    public static Font getRobotoCondensed() {
        initialize();
        if (KnownFonts.instance.robotoCondensed == null) {
            try {
                KnownFonts.instance.robotoCondensed = new Font(KnownFonts.instance.prefix + "RobotoCondensed-standard.fnt", KnownFonts.instance.prefix + "RobotoCondensed-standard.png", Font.DistanceFieldType.STANDARD, 0.0f, 0.0f, 0.0f, 0.0f, true).scaleTo(25.0f, 30.0f).adjustLineHeight(0.9f).setTextureFilter().setName("Roboto Condensed");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (KnownFonts.instance.robotoCondensed != null) {
            return new Font(KnownFonts.instance.robotoCondensed);
        }
        throw new RuntimeException("Assets for getRobotoCondensed() not found.");
    }
    
    public static Font getTangerine() {
        initialize();
        if (KnownFonts.instance.tangerine == null) {
            try {
                KnownFonts.instance.tangerine = new Font(KnownFonts.instance.prefix + "Tangerine-standard.fnt", KnownFonts.instance.prefix + "Tangerine-standard.png", Font.DistanceFieldType.STANDARD, 0.0f, 19.0f, 0.0f, 0.0f, true).scaleTo(48.0f, 32.0f).setTextureFilter().setName("Tangerine");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (KnownFonts.instance.tangerine != null) {
            return new Font(KnownFonts.instance.tangerine);
        }
        throw new RuntimeException("Assets for getTangerine() not found.");
    }
    
    public static Font getTangerineSDF() {
        initialize();
        if (KnownFonts.instance.tangerineSDF == null) {
            try {
                KnownFonts.instance.tangerineSDF = new Font(KnownFonts.instance.prefix + "Tangerine-sdf.fnt", KnownFonts.instance.prefix + "Tangerine-sdf.png", Font.DistanceFieldType.SDF, 0.0f, 0.0f, 0.0f, 0.0f, false).scaleTo(48.0f, 32.0f).setCrispness(0.375f).setName("Tangerine (SDF)");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (KnownFonts.instance.tangerineSDF != null) {
            return new Font(KnownFonts.instance.tangerineSDF);
        }
        throw new RuntimeException("Assets for getTangerineSDF() not found.");
    }
    
    public static Font getYanoneKaffeesatz() {
        initialize();
        if (KnownFonts.instance.kaffeesatz == null) {
            try {
                KnownFonts.instance.kaffeesatz = new Font(KnownFonts.instance.prefix + "YanoneKaffeesatz-standard.fnt", KnownFonts.instance.prefix + "YanoneKaffeesatz-standard.png", Font.DistanceFieldType.STANDARD, 2.0f, -2.0f, 0.0f, 0.0f, true).scaleTo(30.0f, 35.0f).setTextureFilter().setName("Yanone Kaffeesatz");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (KnownFonts.instance.kaffeesatz != null) {
            return new Font(KnownFonts.instance.kaffeesatz);
        }
        throw new RuntimeException("Assets for getYanoneKaffeesatz() not found.");
    }
    
    public static TextureAtlas loadUnicodeAtlas(final FileHandle packFile, final FileHandle imagesDir, final boolean flip) {
        return new TextureAtlas((TextureAtlas.TextureAtlasData)new TextureAtlas.TextureAtlasData(packFile, imagesDir, flip) {
            private int readEntry(final String[] entry, @Null String line) {
                if (line == null) {
                    return 0;
                }
                line = line.trim();
                if (line.length() == 0) {
                    return 0;
                }
                final int colon = line.indexOf(58);
                if (colon == -1) {
                    return 0;
                }
                entry[0] = line.substring(0, colon).trim();
                int i = 1;
                int lastMatch = colon + 1;
                while (true) {
                    final int comma = line.indexOf(44, lastMatch);
                    if (comma == -1) {
                        entry[i] = line.substring(lastMatch).trim();
                        return i;
                    }
                    entry[i] = line.substring(lastMatch, comma).trim();
                    lastMatch = comma + 1;
                    if (i == 4) {
                        return 4;
                    }
                    ++i;
                }
            }
            
            public void load(final FileHandle packFile, final FileHandle imagesDir, final boolean flip) {
                final String[] entry = new String[5];
                final ObjectMap<String, Field<TextureAtlasData.Page>> pageFields = (ObjectMap<String, Field<TextureAtlasData.Page>>)new ObjectMap(15, 0.99f);
                pageFields.put("size", new Field<TextureAtlasData.Page>() {
                    @Override
                    public void parse(final TextureAtlasData.Page page) {
                        page.width = (float)Integer.parseInt(entry[1]);
                        page.height = (float)Integer.parseInt(entry[2]);
                    }
                });
                pageFields.put("format", new Field<TextureAtlasData.Page>() {
                    @Override
                    public void parse(final TextureAtlasData.Page page) {
                        page.format = Pixmap.Format.valueOf(entry[1]);
                    }
                });
                pageFields.put("filter", new Field<TextureAtlasData.Page>() {
                    @Override
                    public void parse(final TextureAtlasData.Page page) {
                        page.minFilter = Texture.TextureFilter.valueOf(entry[1]);
                        page.magFilter = Texture.TextureFilter.valueOf(entry[2]);
                        page.useMipMaps = page.minFilter.isMipMap();
                    }
                });
                pageFields.put("repeat", new Field<TextureAtlasData.Page>() {
                    @Override
                    public void parse(final TextureAtlasData.Page page) {
                        if (entry[1].indexOf(120) != -1) {
                            page.uWrap = Texture.TextureWrap.Repeat;
                        }
                        if (entry[1].indexOf(121) != -1) {
                            page.vWrap = Texture.TextureWrap.Repeat;
                        }
                    }
                });
                pageFields.put("pma", new Field<TextureAtlasData.Page>() {
                    @Override
                    public void parse(final TextureAtlasData.Page page) {
                        page.pma = entry[1].equals("true");
                    }
                });
                final boolean[] hasIndexes = { false };
                final ObjectMap<String, Field<TextureAtlasData.Region>> regionFields = (ObjectMap<String, Field<TextureAtlasData.Region>>)new ObjectMap(127, 0.99f);
                regionFields.put("xy", new Field<TextureAtlasData.Region>() {
                    @Override
                    public void parse(final TextureAtlasData.Region region) {
                        region.left = Integer.parseInt(entry[1]);
                        region.top = Integer.parseInt(entry[2]);
                    }
                });
                regionFields.put("size", new Field<TextureAtlasData.Region>() {
                    @Override
                    public void parse(final TextureAtlasData.Region region) {
                        region.width = Integer.parseInt(entry[1]);
                        region.height = Integer.parseInt(entry[2]);
                    }
                });
                regionFields.put("bounds", new Field<TextureAtlasData.Region>() {
                    @Override
                    public void parse(final TextureAtlasData.Region region) {
                        region.left = Integer.parseInt(entry[1]);
                        region.top = Integer.parseInt(entry[2]);
                        region.width = Integer.parseInt(entry[3]);
                        region.height = Integer.parseInt(entry[4]);
                    }
                });
                regionFields.put("offset", new Field<TextureAtlasData.Region>() {
                    @Override
                    public void parse(final TextureAtlasData.Region region) {
                        region.offsetX = (float)Integer.parseInt(entry[1]);
                        region.offsetY = (float)Integer.parseInt(entry[2]);
                    }
                });
                regionFields.put("orig", new Field<TextureAtlasData.Region>() {
                    @Override
                    public void parse(final TextureAtlasData.Region region) {
                        region.originalWidth = Integer.parseInt(entry[1]);
                        region.originalHeight = Integer.parseInt(entry[2]);
                    }
                });
                regionFields.put("offsets", new Field<TextureAtlasData.Region>() {
                    @Override
                    public void parse(final TextureAtlasData.Region region) {
                        region.offsetX = (float)Integer.parseInt(entry[1]);
                        region.offsetY = (float)Integer.parseInt(entry[2]);
                        region.originalWidth = Integer.parseInt(entry[3]);
                        region.originalHeight = Integer.parseInt(entry[4]);
                    }
                });
                regionFields.put("rotate", new Field<TextureAtlasData.Region>() {
                    @Override
                    public void parse(final TextureAtlasData.Region region) {
                        final String value = entry[1];
                        if (value.equals("true")) {
                            region.degrees = 90;
                        }
                        else if (!value.equals("false")) {
                            region.degrees = Integer.parseInt(value);
                        }
                        region.rotate = (region.degrees == 90);
                    }
                });
                regionFields.put("index", new Field<TextureAtlasData.Region>() {
                    @Override
                    public void parse(final TextureAtlasData.Region region) {
                        region.index = Integer.parseInt(entry[1]);
                        if (region.index != -1) {
                            hasIndexes[0] = true;
                        }
                    }
                });
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(packFile.read(), "UTF-8"), 1024);
                }
                catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                try {
                    String line;
                    for (line = reader.readLine(); line != null && line.trim().length() == 0; line = reader.readLine()) {}
                    while (line != null) {
                        if (line.trim().length() == 0) {
                            break;
                        }
                        if (this.readEntry(entry, line) == 0) {
                            break;
                        }
                        line = reader.readLine();
                    }
                    TextureAtlasData.Page page = null;
                    Array<String> names = null;
                    Array<int[]> values = null;
                    while (line != null) {
                        if (line.trim().length() == 0) {
                            page = null;
                            line = reader.readLine();
                        }
                        else if (page == null) {
                            page = new TextureAtlasData.Page();
                            page.textureFile = imagesDir.child(line);
                            while (this.readEntry(entry, line = reader.readLine()) != 0) {
                                final Field<TextureAtlasData.Page> field = (Field<TextureAtlasData.Page>)pageFields.get(entry[0]);
                                if (field != null) {
                                    field.parse(page);
                                }
                            }
                            this.getPages().add(page);
                        }
                        else {
                            final TextureAtlasData.Region region = new TextureAtlasData.Region();
                            region.page = page;
                            region.name = line.trim();
                            if (flip) {
                                region.flip = true;
                            }
                            while (true) {
                                final int count = this.readEntry(entry, line = reader.readLine());
                                if (count == 0) {
                                    break;
                                }
                                final Field<TextureAtlasData.Region> field2 = (Field<TextureAtlasData.Region>)regionFields.get(entry[0]);
                                if (field2 != null) {
                                    field2.parse(region);
                                }
                                else {
                                    if (names == null) {
                                        names = (Array<String>)new Array(8);
                                        values = (Array<int[]>)new Array(8);
                                    }
                                    names.add(entry[0]);
                                    final int[] entryValues = new int[count];
                                    for (int i = 0; i < count; ++i) {
                                        try {
                                            entryValues[i] = Integer.parseInt(entry[i + 1]);
                                        }
                                        catch (NumberFormatException ex2) {}
                                    }
                                    values.add(entryValues);
                                }
                            }
                            if (region.originalWidth == 0 && region.originalHeight == 0) {
                                region.originalWidth = region.width;
                                region.originalHeight = region.height;
                            }
                            if (names != null && names.size > 0) {
                                region.names = (String[])names.toArray((Class)String.class);
                                region.values = (int[][])values.toArray((Class)int[].class);
                                names.clear();
                                values.clear();
                            }
                            this.getRegions().add(region);
                        }
                    }
                }
                catch (Exception ex) {
                    throw new GdxRuntimeException("Error reading texture atlas file: " + packFile, (Throwable)ex);
                }
                finally {
                    StreamUtils.closeQuietly((Closeable)reader);
                }
                if (hasIndexes[0]) {
                    this.getRegions().sort((Comparator)new Comparator<TextureAtlasData.Region>() {
                        @Override
                        public int compare(final TextureAtlasData.Region region1, final TextureAtlasData.Region region2) {
                            return (region1.index & Integer.MAX_VALUE) - (region2.index & Integer.MAX_VALUE);
                        }
                    });
                }
            }
        });
    }
    
    public static Font addEmoji(final Font changing) {
        initialize();
        if (KnownFonts.instance.twemoji == null) {
            try {
                FileHandle atlas = Gdx.files.internal(KnownFonts.instance.prefix + "Twemoji.atlas");
                if (!atlas.exists() && Gdx.files.isLocalStorageAvailable()) {
                    atlas = Gdx.files.local(KnownFonts.instance.prefix + "Twemoji.atlas");
                }
                if (Gdx.files.internal(KnownFonts.instance.prefix + "Twemoji.png").exists()) {
                    KnownFonts.instance.twemoji = loadUnicodeAtlas(atlas, Gdx.files.internal(KnownFonts.instance.prefix).isDirectory() ? Gdx.files.internal(KnownFonts.instance.prefix) : Gdx.files.internal(KnownFonts.instance.prefix).parent(), false);
                }
                else if (Gdx.files.isLocalStorageAvailable() && Gdx.files.local(KnownFonts.instance.prefix + "Twemoji.png").exists()) {
                    KnownFonts.instance.twemoji = loadUnicodeAtlas(atlas, Gdx.files.local(KnownFonts.instance.prefix).isDirectory() ? Gdx.files.local(KnownFonts.instance.prefix) : Gdx.files.local(KnownFonts.instance.prefix).parent(), false);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (KnownFonts.instance.twemoji != null) {
            return changing.addAtlas(KnownFonts.instance.twemoji);
        }
        throw new RuntimeException("Assets 'Twemoji.atlas' and 'Twemoji.png' not found.");
    }
    
    public void pause() {
    }
    
    public void resume() {
    }
    
    public static Font[] getAll() {
        return new Font[] { getAStarry(), getAStarry().scaleTo(8.0f, 16.0f).setName("A Starry Tall"), getAStarryMSDF(), getBitter(), getCanada(), getCascadiaMono(), getCascadiaMonoMSDF(), getCaveat(), getCozette(), getDejaVuSansMono(), getGentium(), getGentiumSDF(), getHanazono(), getIBM8x16(), getInconsolata(), getInconsolataMSDF(), getIosevka(), getIosevkaMSDF(), getIosevkaSDF(), getIosevkaSlab(), getIosevkaSlabMSDF(), getIosevkaSlabSDF(), getKingthingsFoundation(), getLibertinusSerif(), getOpenSans(), getOxanium(), getQuanPixel(), getRobotoCondensed(), getTangerine(), getTangerineSDF(), getYanoneKaffeesatz() };
    }
    
    public static Font[] getAllStandard() {
        return new Font[] { getAStarry(), getAStarry().scaleTo(8.0f, 16.0f).setName("A Starry Tall"), getBitter(), getCanada(), getCascadiaMono(), getCaveat(), getCozette(), getGentium(), getHanazono(), getIBM8x16(), getInconsolata(), getIosevka(), getIosevkaSlab(), getKingthingsFoundation(), getLibertinusSerif(), getOpenSans(), getOxanium(), getQuanPixel(), getRobotoCondensed(), getTangerine(), getYanoneKaffeesatz() };
    }
    
    public static Font getStandardFamily() {
        final Font.FontFamily family = new Font.FontFamily(new String[] { "Serif", "Sans", "Mono", "Condensed", "Humanist", "Retro", "Slab", "Handwriting", "Canada", "Cozette", "Iosevka", "Medieval", "Future", "Console", "Code" }, new Font[] { getGentium(), getOpenSans(), getInconsolata(), getRobotoCondensed(), getYanoneKaffeesatz(), getIBM8x16(), getIosevkaSlab(), getCaveat(), getCanada(), getCozette(), getIosevka(), getKingthingsFoundation(), getOxanium(), getAStarry(), getCascadiaMono() });
        family.fontAliases.put("Bitter", 0);
        return family.connected[0].setFamily(family);
    }
    
    public static Font[] getAllSDF() {
        return new Font[] { getGentiumSDF(), getIosevkaSDF(), getIosevkaSlabSDF(), getTangerineSDF() };
    }
    
    public static Font[] getAllMSDF() {
        return new Font[] { getAStarryMSDF(), getCascadiaMonoMSDF(), getDejaVuSansMono(), getInconsolataMSDF(), getIosevkaMSDF(), getIosevkaSlabMSDF() };
    }
    
    public void dispose() {
        if (this.astarry != null) {
            this.astarry.dispose();
            this.astarry = null;
        }
        if (this.astarryMSDF != null) {
            this.astarryMSDF.dispose();
            this.astarryMSDF = null;
        }
        if (this.bitter != null) {
            this.bitter.dispose();
            this.bitter = null;
        }
        if (this.canada != null) {
            this.canada.dispose();
            this.canada = null;
        }
        if (this.cascadiaMono != null) {
            this.cascadiaMono.dispose();
            this.cascadiaMono = null;
        }
        if (this.cascadiaMonoMSDF != null) {
            this.cascadiaMonoMSDF.dispose();
            this.cascadiaMonoMSDF = null;
        }
        if (this.caveat != null) {
            this.caveat.dispose();
            this.caveat = null;
        }
        if (this.cozette != null) {
            this.cozette.dispose();
            this.cozette = null;
        }
        if (this.dejaVuSansMono != null) {
            this.dejaVuSansMono.dispose();
            this.dejaVuSansMono = null;
        }
        if (this.gentium != null) {
            this.gentium.dispose();
            this.gentium = null;
        }
        if (this.gentiumSDF != null) {
            this.gentiumSDF.dispose();
            this.gentiumSDF = null;
        }
        if (this.hanazono != null) {
            this.hanazono.dispose();
            this.hanazono = null;
        }
        if (this.ibm8x16 != null) {
            this.ibm8x16.dispose();
            this.ibm8x16 = null;
        }
        if (this.inconsolata != null) {
            this.inconsolata.dispose();
            this.inconsolata = null;
        }
        if (this.inconsolataMSDF != null) {
            this.inconsolataMSDF.dispose();
            this.inconsolataMSDF = null;
        }
        if (this.iosevka != null) {
            this.iosevka.dispose();
            this.iosevka = null;
        }
        if (this.iosevkaMSDF != null) {
            this.iosevkaMSDF.dispose();
            this.iosevkaMSDF = null;
        }
        if (this.iosevkaSDF != null) {
            this.iosevkaSDF.dispose();
            this.iosevkaSDF = null;
        }
        if (this.iosevkaSlab != null) {
            this.iosevkaSlab.dispose();
            this.iosevkaSlab = null;
        }
        if (this.iosevkaSlabMSDF != null) {
            this.iosevkaSlabMSDF.dispose();
            this.iosevkaSlabMSDF = null;
        }
        if (this.iosevkaSlabSDF != null) {
            this.iosevkaSlabSDF.dispose();
            this.iosevkaSlabSDF = null;
        }
        if (this.kingthingsFoundation != null) {
            this.kingthingsFoundation.dispose();
            this.kingthingsFoundation = null;
        }
        if (this.libertinusSerif != null) {
            this.libertinusSerif.dispose();
            this.libertinusSerif = null;
        }
        if (this.openSans != null) {
            this.openSans.dispose();
            this.openSans = null;
        }
        if (this.oxanium != null) {
            this.oxanium.dispose();
            this.oxanium = null;
        }
        if (this.quanPixel != null) {
            this.quanPixel.dispose();
            this.quanPixel = null;
        }
        if (this.robotoCondensed != null) {
            this.robotoCondensed.dispose();
            this.robotoCondensed = null;
        }
        if (this.tangerine != null) {
            this.tangerine.dispose();
            this.tangerine = null;
        }
        if (this.tangerineSDF != null) {
            this.tangerineSDF.dispose();
            this.tangerineSDF = null;
        }
        if (this.kaffeesatz != null) {
            this.kaffeesatz.dispose();
            this.kaffeesatz = null;
        }
        if (this.twemoji != null) {
            this.twemoji.dispose();
            this.twemoji = null;
        }
    }
    
    private interface Field<T>
    {
        void parse(final T p0);
    }
}
