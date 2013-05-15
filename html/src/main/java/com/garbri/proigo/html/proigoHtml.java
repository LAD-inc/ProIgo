package com.garbri.proigo.html;

import com.garbri.proigo.core.proigo;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class proigoHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new proigo();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(800, 600);
	}
}
