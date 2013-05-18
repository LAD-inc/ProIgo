package com.garbri.proigo.core.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.garbri.proigo.core.utilities.BoxProp;

public class Maze 
{
	public Vector2[] playerStartPoint;
	
	public boolean gameFinished = false;
	
	private Vector2 center;
	float winBoxSize;
	
	
	public Maze(World world, float worldWidth, float worldHeight, Vector2 center)
	{
		int numberOfPlayers = 2;
		float gapFromOuterEdge = 1f;
		float playerGapX = 7f;
		float playerGapY = 7f;
		
		this.winBoxSize = 10f;
		
		this.center = center;
		
		
		playerStartPoint = new Vector2[numberOfPlayers];
		
		createOuterWalls(world, worldWidth, worldHeight, center, gapFromOuterEdge);
		
		//BottomLeft
		playerStartPoint[0] = new Vector2 (gapFromOuterEdge + playerGapX , gapFromOuterEdge + playerGapY);
		
		//Top Right
		playerStartPoint[1] = new Vector2 (worldWidth - (gapFromOuterEdge + playerGapX) , worldHeight - (gapFromOuterEdge + playerGapY));
		
		
		
	}
	
	public boolean checkForWin(Vector2 carCenter, String playerName)
    {
		
		//TODO: this is completly based on the center of the car. More advanced things need to be done to notice when the car is actually in the win box, not just its center
		
		if(!this.gameFinished)
		{
			if(carCenter.x <= (this.center.x - winBoxSize/2))
	    	{
				//Car is too far left
	    		return false;
	    	}
			
			if(carCenter.x >= (this.center.x + winBoxSize/2))
	    	{
				//Car is too far right
				
				System.out.println(carCenter.x + " >= " + (this.center.x + winBoxSize/2));
				
				
	    		return false;
	    	}
			
			
	    	if(carCenter.y >= (this.center.y + winBoxSize/2))
	    	{
	    		//Car is too far up
	    		return false;
	    	}
	    	
	    	if(carCenter.y <= (this.center.y - winBoxSize/2))
	    	{
	    		//Car is too far down
	    		return false;
	    	}
	    	
	
			System.out.println(playerName + " Wins!");
			this.gameFinished = true;
			return true;
		}
		
		return false;

    }
	
	private void createOuterWalls(World world, float worldWidth, float worldHeight, Vector2 center, float gapFromOuterEdge)
	{
		
	    //outer walls
	    BoxProp bottomWall = new BoxProp(world, worldWidth, 1, new Vector2 (worldWidth/2, gapFromOuterEdge)); //bottom Wall
	    
	    BoxProp leftWall = new BoxProp(world, 1, worldHeight, new Vector2 (gapFromOuterEdge, worldHeight/2));//left Wall
	    
	    BoxProp topWall = new BoxProp(world,  worldWidth, 1, new Vector2 (worldWidth/2,worldHeight-gapFromOuterEdge));//top
	    
	    BoxProp rightWall = new BoxProp(world, 1, worldHeight, new Vector2 (worldWidth - gapFromOuterEdge, worldHeight/2));//left Wall
	}

}
