package com.fos.game.scripts.test;

import com.fos.game.engine.ecs.components.scripts.Script;

public class SimplePrintScript extends Script {

    private int count;

    @Override
    public void start() {
        count = 0;
    }

    @Override
    public void update(float delta) {
        count++;
        System.out.println("hello, I'm a print script " + count);
    }
}
