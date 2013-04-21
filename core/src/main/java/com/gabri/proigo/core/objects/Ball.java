package com.gabri.proigo.core.objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Ball {
	
	float ballSize = 1f;
	
	public Ball(World world, float x, float y)
	{
		
	    //Dynamic Body  
	    BodyDef bodyDef = new BodyDef();  
	    bodyDef.type = BodyType.DynamicBody;  
	    bodyDef.position.set(x, y);  
	    Body body = world.createBody(bodyDef);  
	    CircleShape dynamicCircle = new CircleShape();  
	    dynamicCircle.setRadius(ballSize);  
	    FixtureDef fixtureDef = new FixtureDef();  
	    fixtureDef.shape = dynamicCircle;  
	    fixtureDef.density = 0.5f;  
	    fixtureDef.friction = 0f;  
	    fixtureDef.restitution = 1f;  
	    body.createFixture(fixtureDef); 
	}
	

}
