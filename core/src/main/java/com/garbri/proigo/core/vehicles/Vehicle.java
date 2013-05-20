package com.garbri.proigo.core.vehicles;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.garbri.proigo.core.controls.KeyboardControls;
import com.garbri.proigo.core.controls.XboxControls;
import com.garbri.proigo.core.objects.Player;
import com.garbri.proigo.core.objects.Wheel;
import com.garbri.proigo.core.utilities.SpriteHelper;

public class Vehicle {

	public Body body;
	float width, length, angle, maxSteerAngle, maxSpeed, power;
	float wheelAngle;
	public int steer, accelerate;
	public Vector2 position;
	public List<Wheel> wheels;
	
	public Player player;
	
	public static final int STEER_NONE=0;
	public static final int STEER_RIGHT=1;
	public static final int STEER_LEFT=2;

	public static final int ACC_NONE=0;
	public static final int ACC_ACCELERATE=1;
	public static final int ACC_BRAKE=2;
	
	public Sprite sprite;
	
	
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
	
	private void xboxControlRead(XboxControls xboxcontrols)
	{
		//Read Acceleration/Braking
		if (xboxcontrols.getAccelerate())
			this.accelerate = Vehicle.ACC_ACCELERATE;
		else if (xboxcontrols.getBrake())
			this.accelerate = Vehicle.ACC_BRAKE;
		else
			this.accelerate = Vehicle.ACC_NONE;
		
		// Read Stearing Command
		if (xboxcontrols.getLeft())
			this.steer = Vehicle.STEER_LEFT;
		else if (xboxcontrols.getRight())
			this.steer = Vehicle.STEER_RIGHT;
		else
			this.steer = Vehicle.STEER_NONE;
	}
	
	private void keyboardControlRead(KeyboardControls keyboardControls)
	{
		if (Gdx.input.isKeyPressed(keyboardControls.controlUp))
			this.accelerate = Vehicle.ACC_ACCELERATE;
		else if (Gdx.input.isKeyPressed(keyboardControls.controlDown))
			this.accelerate = Vehicle.ACC_BRAKE;
		else
			this.accelerate = Vehicle.ACC_NONE;

		if (Gdx.input.isKeyPressed(keyboardControls.controlLeft))
			this.steer = Vehicle.STEER_LEFT;
		else if (Gdx.input.isKeyPressed(keyboardControls.controlRight))
			this.steer = Vehicle.STEER_RIGHT;
		else
			this.steer = Vehicle.STEER_NONE;
	}
	
	public void controlVehicle()
	{
		if(this.player.controls instanceof XboxControls)
		{
			xboxControlRead((XboxControls)this.player.controls);
		}	
		else if (this.player.controls instanceof KeyboardControls)
		{
			keyboardControlRead((KeyboardControls)this.player.controls); 

		}
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
	
	public void destroyVehicle()
	{
		World world = this.body.getWorld();
		
        //update revolving wheels
        for(Wheel wheel:this.wheels) {
        	world.destroyBody(wheel.body);
        }
        
        world.destroyBody(this.body);
	}
	
	public void update (float deltaTime){
	    
        //1. KILL SIDEWAYS VELOCITY
        
        for(Wheel wheel:wheels){
        	wheel.killSidewaysVelocity();
        }
    
        //2. SET WHEEL ANGLE
  
        //calculate the change in wheel's angle for this update
        float incr=(this.maxSteerAngle) * deltaTime * 5;
        
        if(this.steer==Vehicle.STEER_LEFT){
            this.wheelAngle=Math.min(Math.max(this.wheelAngle, 0)+incr, this.maxSteerAngle); //increment angle without going over max steer
        }else if(this.steer==Vehicle.STEER_RIGHT){
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
        if((this.accelerate==Vehicle.ACC_ACCELERATE) && (this.getSpeedKMH() < this.maxSpeed)){
        	
        	if(this.getLocalVelocity().y<20)
        		baseVector= new Vector2(0, -2.2f);
        	else
        		baseVector= new Vector2(0, -1.1f);
        }
        else if(this.accelerate==Vehicle.ACC_BRAKE){
            //braking, but still moving forwards - increased force
            if(this.getLocalVelocity().y<0)
            	baseVector= new Vector2(0f, 2f);
            //going in reverse - less force
            else 
            	baseVector=new Vector2(0f, 1f);
        }
        else if (this.accelerate==Vehicle.ACC_NONE ) {
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
