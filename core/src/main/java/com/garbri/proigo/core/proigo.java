package com.garbri.proigo.core;

import java.util.ArrayList;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.garbri.proigo.core.controls.IControls;
import com.garbri.proigo.core.controls.KeyboardControls;
import com.garbri.proigo.core.controls.XboxListener;
import com.garbri.proigo.core.objects.Player;

public class proigo extends Game {
	
	public Maze1 maze1;
	public SoccerScreen soccerScreen;
	
	//Number of players;
	public Player[] players = new Player[4];
	
	ArrayList<IControls> controls =  new ArrayList<IControls>();
	
	@Override
	public void create() {	
		
		
		this.initilizeControls();
		
		for(int i=0; i < this.players.length; i++)
		{
			this.players[i] = new Player("Player " + String.valueOf(i+1), this.controls.get(i));
			this.players[i].active = true;
			
			if (i%2 == 0)
			{
				this.players[i].playerTeam = Player.team.blue;
			}
			else
			{
				this.players[i].playerTeam = Player.team.red;
			}
				
		}
		
		maze1 = new Maze1(this);
		soccerScreen = new SoccerScreen(this);
		
		this.soccerScreen.ballOffsetX = 0f;
		
		setScreen(maze1); 
		
	 
	}
	
	private void initilizeControls()
	{
		for(Controller controller: Controllers.getControllers()) 
		{
		   Gdx.app.log("Main", controller.getName());
		   XboxListener listener = new XboxListener();
		   controller.addListener(listener);
		   listener.getControls();
		   controls.add(listener.getControls());
//		   this.controllers[i] = controller;
//		   i++;
			   
		}
			
				controls.add( new KeyboardControls(Input.Keys.DPAD_UP, Input.Keys.DPAD_DOWN, Input.Keys.DPAD_LEFT, Input.Keys.DPAD_RIGHT));
				controls.add( new KeyboardControls(Input.Keys.W, Input.Keys.S, Input.Keys.A, Input.Keys.D));
				controls.add( new KeyboardControls(Input.Keys.DPAD_UP, Input.Keys.DPAD_DOWN, Input.Keys.DPAD_LEFT, Input.Keys.DPAD_RIGHT));
				controls.add( new KeyboardControls(Input.Keys.W, Input.Keys.S, Input.Keys.A, Input.Keys.D));
	}
	
}