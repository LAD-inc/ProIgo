package com.garbri.proigo.core.utilities;

import com.badlogic.gdx.utils.TimeUtils;

public class TimerHelper {
	
	long gameStartTime;
	int elapsedTimeInSeconds;
	
	public TimerHelper()
	{
		resetTimer();
	}
	
	public void resetTimer()
	{
		gameStartTime = TimeUtils.millis();
		elapsedTimeInSeconds = 0;
	}
	
	public String getElapsedTimeAsString()
	{
		//boolean buttonPressed = controllers[0].getButton(buttonCode);
		int tempSeconds = -1* ((int) ((gameStartTime - TimeUtils.millis())/1000));
		if (tempSeconds != this.elapsedTimeInSeconds)
		{
			this.elapsedTimeInSeconds = tempSeconds;
		}
		
		return String.valueOf(this.elapsedTimeInSeconds);
	}

}
