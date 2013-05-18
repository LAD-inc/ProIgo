package com.garbri.proigo.core.utilities;

import com.badlogic.gdx.utils.TimeUtils;

public class TimerHelper {
	
	public long gameStartTime;
	public int elapsedTimeInSeconds;
	public int countDownTimer;
	
	
	public TimerHelper()
	{
		resetTimer();
	}
	
	public void resetTimer()
	{
		gameStartTime = TimeUtils.millis();
		elapsedTimeInSeconds = 0;
	}
	
	public void progressTime()
	{
		//boolean buttonPressed = controllers[0].getButton(buttonCode);
		int tempSeconds = -1* ((int) ((gameStartTime - TimeUtils.millis())/1000));
		if (tempSeconds != this.elapsedTimeInSeconds)
		{
			this.elapsedTimeInSeconds = tempSeconds;
			
			if (this.countDownTimer > 0)
			{
				this.countDownTimer --;
			}
		}
	}
	
	public String getElapsedTimeAsString()
	{	
		return String.valueOf(this.elapsedTimeInSeconds);
	}
	
	public void startCountDown(int timeInSeconds)
	{
		this.countDownTimer = timeInSeconds;
	}

}
