package com.garbri.proigo.core.controls;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

public class XboxListener implements ControllerListener {
	XboxControls controls = new XboxControls();
	
	public XboxControls getControls() {
		return controls;
	}

	public void setControls(XboxControls controls) {
		this.controls = controls;
	}

	@Override
	public boolean accelerometerMoved(Controller arg0, int arg1, Vector3 arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean axisMoved(Controller arg0, int arg1, float arg2) {
		if(arg1 == 1){
			if(arg2 < -0.2){
				controls.left = true;
				controls.right = false;
			} else if(arg2 > 0.2){
				controls.left = false;
				controls.right = true;
			} else{
				controls.left = false;
				controls.right = false;
			}
		} 
		
		return false;
	}

	@Override
	public boolean buttonDown(Controller arg0, int buttonCode) {
		if(buttonCode == 0){
		controls.accelerate  = true;}
		else if (buttonCode == 1){
			controls.brake = true;
		}
		
		return true;
	}

	@Override
	public boolean buttonUp(Controller arg0, int buttonCode) {
		if(buttonCode == 0){
			controls.accelerate  = false;}
			else if (buttonCode == 1){
				controls.brake = false;
			}
		return true;
	}

	@Override
	public void connected(Controller arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnected(Controller arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean povMoved(Controller arg0, int arg1, PovDirection arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean xSliderMoved(Controller arg0, int arg1, boolean arg2) {
		controls.left = true;
		return false;
	}

	@Override
	public boolean ySliderMoved(Controller arg0, int arg1, boolean arg2) {
		// TODO Auto-generated method stub
		return false;
	}

}
