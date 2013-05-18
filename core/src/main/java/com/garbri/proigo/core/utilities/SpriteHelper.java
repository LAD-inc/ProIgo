package com.garbri.proigo.core.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;

public class SpriteHelper {
	
	private Texture carTexture;
	private Texture wheelTexture;
	private Texture ballTexture;
	
	public SpriteHelper()
	{
		this.carTexture = new Texture(Gdx.files.internal("NewCars.png"));
		this.wheelTexture = new Texture(Gdx.files.internal("Wheel.png"));
		this.ballTexture = new Texture(Gdx.files.internal("Ball.png"));
	}
	
	public static void updateSprite(Sprite sprite, SpriteBatch spriteBatch, int PIXELS_PER_METER, Body body)
	{
		setSpritePosition(sprite, PIXELS_PER_METER, body);

		sprite.draw(spriteBatch);
	}
	
	public static void setSpritePosition(Sprite sprite, int PIXELS_PER_METER, Body body)
	{
		
		sprite.setPosition(PIXELS_PER_METER * body.getPosition().x - sprite.getWidth()/2,
				PIXELS_PER_METER * body.getPosition().y  - sprite.getHeight()/2);
		sprite.setRotation((MathUtils.radiansToDegrees * body.getAngle()));
	}
	
	//0=blue,1=red,2=green
	public Sprite getCarSprite(int colour)
	{
		//limit to max number of cars
		if(colour < 0 || colour > 2)
			colour = 0;
		
		return new Sprite(carTexture,(20*colour),0,20, 40);
	}
	
	public Sprite getBallSprite()
	{
		return new Sprite(ballTexture,
							0,  //X
							0,  //Y
							40, //Width
							40 //Height
							);
	}
	
	public Sprite getWheelSprite()
	{
		return new Sprite(wheelTexture,
							0,  //X
							0,  //Y
							4, //Width
							8 //Height
							);
	}
	
}