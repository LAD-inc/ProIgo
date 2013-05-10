package com.gabri.proigo.core.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Ball {
	
	Body body;
	
	float ballSize = 2f;
	
	public Ball(World world, float x, float y)
	{
		
	    //Dynamic Body  
	    BodyDef bodyDef = new BodyDef();  
	    bodyDef.type = BodyType.DynamicBody;  
	    bodyDef.position.set(x, y);  
	    this.body = world.createBody(bodyDef);  
	    CircleShape dynamicCircle = new CircleShape();  
	    dynamicCircle.setRadius(ballSize);  
	    FixtureDef fixtureDef = new FixtureDef();  
	    fixtureDef.shape = dynamicCircle;  
	    fixtureDef.density = 0.25f;  
	    fixtureDef.friction = 0f;  
	    fixtureDef.restitution = 1f;  
	    this.body.createFixture(fixtureDef); 
	}
	
	public Vector2 getLocalVelocity() {
	    /*
	    returns balls's velocity vector relative to the car
	    */
		return this.body.getLocalVector(this.body.getLinearVelocityFromLocalPoint(new Vector2(0, 0)));
	}
	
	public void update()
	{
		Vector2 currentVelocity = this.getLocalVelocity();
		Vector2 position= this.getLocation();
		
		System.out.println("Ball Position - " + position + "Ball Velocity - " + currentVelocity);
		
		this.body.applyForce(this.body.getWorldVector(new Vector2(-(currentVelocity.x/3), -(currentVelocity.y/3))), position, true );
	}
	
	public Vector2 getLocation()
	{
		return this.body.getWorldCenter();
	}
	
	

}
