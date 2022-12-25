package com.fos.game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;

public class TestTab extends Tab {

    private String title;
    Table content = new Table();

    public TestTab (String title) {
        super(false, true); //tab is not savable, tab is closeable by user
        this.title = title;
        content.setX(500);
        content.setY(500);
        content.add(new VisLabel("Some widget"));
        content.add(new VisTextButton("click me"));
    }

    @Override
    public String getTabTitle () {
        return title;
    }

    @Override
    public Table getContentTable () {
        return content;
    }
}