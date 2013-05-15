package com.garbri.proigo.core.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.garbri.proigo.core.utilities.BoxProp;

public class Goal {
	
    BoxProp side1;
    BoxProp side2;
    BoxProp back;
    
	private static float postLength = 9;
	private static float goalThickness = 1;
	private static float barLength = 15;
	
	String facing;
	
	float goalLineX;
	float goalY;
    
    public Goal(World world, float x, float y, String facing)
    {
    	
    	int direction = 1;
    	
    	this.facing = facing;
    	
    	if (facing == "right" || facing == "left")
    	{
    		if(facing == "left")
    			direction = -1;
    		
    	
    		this.back = new BoxProp(world, goalThickness, barLength, new Vector2 (x , y));
        	
        	float postsXCoord = x + (direction * (-(goalThickness/2) + (postLength/2))); 
        	
        	this.side1 = new BoxProp(world, postLength, goalThickness, new Vector2 ( postsXCoord  , y -(barLength/2 +  goalThickness/2)));
        	this.side2 = new BoxProp(world, postLength, goalThickness, new Vector2 ( postsXCoord  , y +(barLength/2 +  goalThickness/2)));
        	
        	this.goalLineX = x + (direction *(postLength - (goalThickness/2)));
        	this.goalY = y;
    		
    	}	
    }
    
    public boolean checkForGoal(Vector2 ballLocation, float ballSize)
    {
    	if(this.facing == "left")
    	{	
    		if(ballLocation.x <= goalLineX)
    		{
    			return false;
    		}
    	}
    	else
    	{
    		if(ballLocation.x >= goalLineX)
    		{
    			return false;
    		}
    	}
    	
    	//if it made it this far the ball is more than likely in, just santity checking
    	
		if(ballLocation.y > (goalY - barLength/2) &&  ballLocation.y < (goalY + barLength/2))
		{
			System.out.println("GOAAAAAALLLLL");
			return true;
		}
		
		return false;
    }
    
    public float getGoalLength()
    {
    	return (this.barLength + (goalThickness*2));
    }
    
    public float getGoalDepth()
    {
    	return (this.postLength);
    }
    
    
}
