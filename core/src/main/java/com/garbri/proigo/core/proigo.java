package com.garbri.proigo.core;

import com.badlogic.gdx.Game;

public class proigo extends Game {
	
	public Maze1 maze1;
	public Maze2 maze2;
	public SoccerScreen soccerScreen;
	
	@Override
	public void create() {	
		
		
		maze1 = new Maze1(this);
		maze2 = new Maze2(this);
		soccerScreen = new SoccerScreen(this);
		
		this.soccerScreen.ballOffsetX = 0f;
		
		setScreen(maze1); 
		
	 
	}
	
}