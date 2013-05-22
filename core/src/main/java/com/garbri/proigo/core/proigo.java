package com.garbri.proigo.core;

import java.util.ArrayList;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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
	public ArrayList<Player> players;
	
	ArrayList<IControls> controls =  new ArrayList<IControls>();
	
	@Override
	public void create() {	
		
		
		this.initilizeControls();
		
		int numPlayers = 4;
		
		createPlayers(numPlayers);
		
		maze1 = new Maze1(this);
		soccerScreen = new SoccerScreen(this);
		
		this.soccerScreen.ballOffsetX = 0f;
		
		setScreen(maze1); 
		
	 
	}
	
	private void createPlayers(int numberOfPlayers)
	{
		this.players = new ArrayList<Player>();
		Player tempPlayer;
		
		
		for(int i=0; i < numberOfPlayers; i++)
		{
			tempPlayer = new Player("Player " + String.valueOf(i+1), this.controls.get(i));
			
			tempPlayer.active = true;
			
			if (i%2 == 0)
			{
				tempPlayer.playerTeam = Player.team.blue;
			}
			else
			{
				tempPlayer.playerTeam = Player.team.red;
			}
			
			this.players.add(tempPlayer);				
		}
	}
	
	public void changeNumberPlayers(int numberOfPlayers, Screen screen)
	{
		createPlayers(numberOfPlayers);
		setScreen(screen);
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