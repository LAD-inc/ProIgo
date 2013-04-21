package com.gabri.proigo.core.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.garbri.proigo.core.utilities.BoxProp;

public class Goal {
	
    BoxProp side1;
    BoxProp side2;
    BoxProp back;
    
	private static float postLength = 6;
	private static float goalThickness = 1;
	private static float barLength = 10;
    
    public Goal(World world, float x, float y, String facing)
    {
    	
    	int direction = 1;
    	
    	if (facing == "right" || facing == "left")
    	{
    		if(facing == "left")
    			direction = -1;
    		
    	
    		this.back = new BoxProp(world, goalThickness, barLength, new Vector2 (x , y));
        	
        	float postsXCoord = x + (direction * (-(goalThickness/2) + (postLength/2))); 
        	
        	this.side1 = new BoxProp(world, postLength, goalThickness, new Vector2 ( postsXCoord  , y -(barLength/2 +  goalThickness/2)));
        	this.side2 = new BoxProp(world, postLength, goalThickness, new Vector2 ( postsXCoord  , y +(barLength/2 +  goalThickness/2)));
    		
    	}	
    }
    
    public float getGoalLength()
    {
    	return (this.barLength + (goalThickness*2));
    }
    
}
