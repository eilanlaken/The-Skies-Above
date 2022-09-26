package com.fos.game.scripts.test;

import com.fos.game.engine.components.scripts.Script;

public class ConsolePrintScript extends Script {

    @Override
    public void start() {

    }

    @Override
    public void update(float delta) {

        System.out.println("Hello from entity: ");
    }
}
