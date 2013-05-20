package com.garbri.proigo.core.objects;

import com.garbri.proigo.core.controls.IControls;

public class Player {
	
	public IControls controls;
	public String playerName;
	public team playerTeam;
	public boolean active;
	
	public Player(String name, IControls controls)
	{
		this.playerName = name;
		this.controls = controls;
	}
	
	public static enum team{blue, red}
	
	public String getTeamName()
	{
		switch(this.playerTeam)
		{
			case blue:
				return "blue";
			case red:
				return "red";
		}
		
		return "a";
	}

}
