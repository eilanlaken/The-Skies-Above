package com.fos.game.ui;

import com.kotcrab.vis.ui.widget.VisWindow;

public class GameWindow extends VisWindow {

    final float originalAspectRatio;

    public GameWindow(String title, int x, int y, int width, int height, boolean resizeable) {
        super(title);
        VisWindow.FADE_TIME = 0.01f;
        addCloseButton();
        setResizeBorder(22);
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        setResizable(resizeable);
        originalAspectRatio = (float) width / (float) height;
        System.out.println(originalAspectRatio);
    }

}
