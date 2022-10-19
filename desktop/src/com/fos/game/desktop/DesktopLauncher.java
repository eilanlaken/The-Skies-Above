package com.fos.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.fos.game.engine.context.GameContext;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.gles30ContextMajorVersion = 3;
		config.gles30ContextMinorVersion = 3;
		config.title = "Fragments of Space v-1.0.0";
		//Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//final double width = screenSize.getWidth();
		//final double height = screenSize.getHeight();
		final double width = 1920; // native game resolution
		final double height = 1080; // native game resolution
		config.width = (int) width;
		config.height = (int) height;
		config.backgroundFPS = 60;
		config.foregroundFPS = 60;
		//config.fullscreen = true;
		//config.useGL30 = true; //TODO: needed for multiple attachments of framebuffer objects.
		//config.vSyncEnabled = true; TODO
		//config.addIcon(); TODO
		new LwjglApplication(new GameContext(), config);
	}
}
