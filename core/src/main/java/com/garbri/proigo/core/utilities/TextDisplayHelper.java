package com.garbri.proigo.core.utilities;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class TextDisplayHelper {
	
	public BitmapFont font;
	
	public TextDisplayHelper()
	{
		//TextureAtlas textureAtlas = new TextureAtlas("Fonts/main");
		this.font = new BitmapFont(false);
		
		//this.font = new BitmapFont(Gdx.files.internal("data/calibri.fnt"),textureAtlas.findRegion("calibri"), false);
		
		//Make text white
		this.font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
	}
	

}
