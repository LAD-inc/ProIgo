package com.garbri.proigo.core.objects;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.garbri.proigo.core.proigo;
import com.garbri.proigo.core.utilities.Controls;
import com.garbri.proigo.core.utilities.SpriteHelper;

public class Car {
	public Body body;
	float width, length, angle, maxSteerAngle, maxSpeed, power;
	float wheelAngle;
	public int steer, accelerate;
	public Vector2 position;
	public List<Wheel> wheels;
	
	public String playerName;
	
	public static final int STEER_NONE=0;
	public static final int STEER_RIGHT=1;
	public static final int STEER_LEFT=2;

	public static final int ACC_NONE=0;
	public static final int ACC_ACCELERATE=1;
	public static final int ACC_BRAKE=2;
	
	Controls controls;
	public Sprite sprite;
	
	
	public Car(String playerName, World world, float width, float length, Vector2 position,
			float angle, float power, float maxSteerAngle, float maxSpeed, Controls controls, Sprite carSprite, Sprite wheelSprite) {
		super();
		
		this.playerName = playerName;
		
		this.steer = this.STEER_NONE;
		this.accelerate = this.ACC_NONE;
		
		this.width = width;
		this.length = length;
		this.angle = angle;
		this.maxSteerAngle = maxSteerAngle;
		this.maxSpeed = maxSpeed;
		this.power = power;
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
		
		
		this.controls = controls;
		
		this.sprite = carSprite;
	}
	
	public List<Wheel> getPoweredWheels () {
		List<Wheel> poweredWheels = new ArrayList<Wheel>();
		for (Wheel wheel:this.wheels) {
			if (wheel.powered)
				poweredWheels.add(wheel);
		}
		return poweredWheels;
	}
	
	public Vector2 getLocalVelocity() {
	    /*
	    returns car's velocity vector relative to the car
	    */
		return this.body.getLocalVector(this.body.getLinearVelocityFromLocalPoint(new Vector2(0, 0)));
	}
	
	public void controlCar()
	{
		if (Gdx.input.isKeyPressed(this.controls.controlUp))
			this.accelerate = this.ACC_ACCELERATE;
		else if (Gdx.input.isKeyPressed(this.controls.controlDown))
			this.accelerate = this.ACC_BRAKE;
		else
			this.accelerate = this.ACC_NONE;
		
		if (Gdx.input.isKeyPressed(this.controls.controlLeft))
			this.steer = this.STEER_LEFT;
		else if (Gdx.input.isKeyPressed(this.controls.controlRight))
			this.steer = this.STEER_RIGHT;
		else
			this.steer = this.STEER_NONE;
		
		this.update(Gdx.app.getGraphics().getDeltaTime());
	}

	
	public List<Wheel> getRevolvingWheels () {
		List<Wheel> revolvingWheels = new ArrayList<Wheel>();
		for (Wheel wheel:this.wheels) {
			if (wheel.revolving)
				revolvingWheels.add(wheel);
		}
		return revolvingWheels;
	}
	
	public float getSpeedKMH(){
	    Vector2 velocity=this.body.getLinearVelocity();
	    float len = velocity.len();
	    return (len/1000)*3600;
	}
	
	public void setSpeed (float speed){
	    /*
	    speed - speed in kilometers per hour
	    */
	    Vector2 velocity=this.body.getLinearVelocity();
	    velocity=velocity.nor();
	    velocity=new Vector2(velocity.x*((speed*1000.0f)/3600.0f),
	    				velocity.y*((speed*1000.0f)/3600.0f));
	    this.body.setLinearVelocity(velocity);
	}
	
	public void update (float deltaTime){
	    
        //1. KILL SIDEWAYS VELOCITY
        
        for(Wheel wheel:wheels){
        	wheel.killSidewaysVelocity();
        }
    
        //2. SET WHEEL ANGLE
  
        //calculate the change in wheel's angle for this update
        float incr=(this.maxSteerAngle) * deltaTime * 5;
        
        if(this.steer==this.STEER_LEFT){
            this.wheelAngle=Math.min(Math.max(this.wheelAngle, 0)+incr, this.maxSteerAngle); //increment angle without going over max steer
        }else if(this.steer==this.STEER_RIGHT){
            this.wheelAngle=Math.max(Math.min(this.wheelAngle, 0)-incr, -this.maxSteerAngle); //decrement angle without going over max steer
        }else{
            this.wheelAngle=0;        
        }

        //update revolving wheels
        for(Wheel wheel:this.getRevolvingWheels()) {
        	wheel.setAngle(this.wheelAngle);
        }
        
        //3. APPLY FORCE TO WHEELS
        Vector2 baseVector; //vector pointing in the direction force will be applied to a wheel ; relative to the wheel.
        
        //if accelerator is pressed down and speed limit has not been reached, go forwards
        if((this.accelerate==this.ACC_ACCELERATE) && (this.getSpeedKMH() < this.maxSpeed)){
        	
        	if(this.getLocalVelocity().y<20)
        		baseVector= new Vector2(0, -2);
        	else
        		baseVector= new Vector2(0, -1);
        }
        else if(this.accelerate==this.ACC_BRAKE){
            //braking, but still moving forwards - increased force
            if(this.getLocalVelocity().y<0)
            	baseVector= new Vector2(0f, 1.3f);
            //going in reverse - less force
            else 
            	baseVector=new Vector2(0f, 0.7f);
        }
        else if (this.accelerate==this.ACC_NONE ) {
        	//slow down if not accelerating
        	baseVector=new Vector2(0, 0);
            if (this.getSpeedKMH()<7)
                this.setSpeed(0);
            else if (this.getLocalVelocity().y<0)
        		baseVector=new Vector2(0, 0.7f);
            else if (this.getLocalVelocity().y>0)
        		baseVector=new Vector2(0, -0.7f);
        }
        else 
        	baseVector=new Vector2(0, 0);

        //multiply by engine power, which gives us a force vector relative to the wheel
        Vector2 forceVector= new Vector2(this.power*baseVector.x, this.power*baseVector.y);

        //apply force to each wheel
        for(Wheel wheel:this.getPoweredWheels()){
           Vector2 position= wheel.body.getWorldCenter();
           wheel.body.applyForce(wheel.body.getWorldVector(new Vector2(forceVector.x, forceVector.y)), position, true );
        }
        
        //System.out.println(this.playerName + "'s Car Speed: " + this.getSpeedKMH());
        //if going very slow, stop - to prevent endless sliding

	}
	
	public void updateSprite(SpriteBatch spriteBatch, int PIXELS_PER_METER)
	{
		
		//Update Car Body Sprite
		SpriteHelper.updateSprite(this.sprite, spriteBatch, PIXELS_PER_METER, this.body);
		
		//Update Wheels Sprites
        for(Wheel wheel:wheels)
        {
        	wheel.updateSprite(spriteBatch, PIXELS_PER_METER);
        }
	}
	
}
