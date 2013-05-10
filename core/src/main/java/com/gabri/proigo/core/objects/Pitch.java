package com.gabri.proigo.core.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.garbri.proigo.core.utilities.BoxProp;

public class Pitch {

	public Goal leftGoal;
	public Goal rightGoal;
	
	
	public Pitch (World world, float worldWidth, float worldHeight, Vector2 center)
	{
		//Create Goal Objects
	    this.leftGoal = new Goal(world, 1f, center.y, "right");
	    this.rightGoal = new Goal(world, (worldWidth -1f), center.y, "left");
	    
	    float touchlineLength = (worldHeight/2) - (leftGoal.getGoalLength()/2);
	    
	    //outer walls
	    BoxProp wall1 = new BoxProp(world, worldWidth, 1, new Vector2 (worldWidth/2,10f)); //bottom
	    
	    BoxProp wall22 = new BoxProp(world, 1, touchlineLength, new Vector2 (leftGoal.getGoalDepth(), touchlineLength/2));//left
	    BoxProp wall222 = new BoxProp(world, 1, touchlineLength, new Vector2 (leftGoal.getGoalDepth(), worldHeight-(touchlineLength/2)));//left
	    
	    BoxProp wall3 = new BoxProp(world,  worldWidth, 1, new Vector2 (worldWidth/2,worldHeight-10f));//top
	    
	    BoxProp wall44 = new BoxProp(world, 1, touchlineLength, new Vector2 (worldWidth - (rightGoal.getGoalDepth()), touchlineLength/2));//right
	    BoxProp wall444 = new BoxProp(world, 1, touchlineLength, new Vector2 (worldWidth - (rightGoal.getGoalDepth()), worldHeight-(touchlineLength/2)));//right 
	}
}
