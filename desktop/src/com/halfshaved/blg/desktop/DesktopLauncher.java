package com.halfshaved.blg.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.halfshaved.blg.biglebowski;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = biglebowski.V_WIDTH*3;
		config.height = biglebowski.V_HEIGHT*3;
		new LwjglApplication(new biglebowski(), config);
	}
}
