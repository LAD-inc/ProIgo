package com.garbri.proigo.java;

import com.garbri.proigo.core.proigo;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class proigoDesktop {
	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.useGL20 = true;
		config.width = 1024;
		config.height = 800;
		new LwjglApplication(new proigo(), config);
	}
}
