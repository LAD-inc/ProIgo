package com.garbri.proigo.core.vehicles;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.garbri.proigo.core.objects.Player;
import com.garbri.proigo.core.objects.Wheel;

public class Car extends Vehicle {
	
	public Car(Player player, World world, Vector2 position,
			float angle, Sprite carSprite, Sprite wheelSprite) {
		super();
		
		//TODO: Default Car Settings - Should be read from config?
		//Size
		this.width = 2f;
		this.length = 4f;
		
		//Handling
		this.maxSteerAngle = 20f;
		this.maxSpeed = 180f;
		this.power = 60f;
		
		
		this.player = player;
		
		this.steer = Vehicle.STEER_NONE;
		this.accelerate = Vehicle.ACC_NONE;
		

		this.angle = angle;
		this.position = position;
		this.wheelAngle = 0;
		
		//init body 
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(position);
		bodyDef.angle = angle;
		this.body = world.createBody(bodyDef);
		
		//init shape
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1.0f;
		fixtureDef.friction = 0.6f; //friction when rubbing against other shapes
		fixtureDef.restitution  = 0.4f; //amount of force feedback when hitting something. >0 makes the car bounce off, it's fun!
		PolygonShape carShape = new PolygonShape();
		carShape.setAsBox(this.width / 2, this.length / 2);
		fixtureDef.shape = carShape;
		this.body.createFixture(fixtureDef);
		
		//initialize wheels
		this.wheels = new ArrayList<Wheel>();
		this.wheels.add(new Wheel(world, this, -1f, -1.2f, 0.4f, 0.8f, true,  true, wheelSprite)); //top left
		this.wheels.add(new Wheel(world, this, 1f, -1.2f, 0.4f, 0.8f, true,  true, wheelSprite)); //top right
		this.wheels.add(new Wheel(world, this, -1f, 1.2f, 0.4f, 0.8f, false,  false, wheelSprite)); //back left
		this.wheels.add(new Wheel(world, this, 1f, 1.2f, 0.4f, 0.8f, false,  false, wheelSprite)); //back right
		
		
		this.sprite = carSprite;
	}
	
}
