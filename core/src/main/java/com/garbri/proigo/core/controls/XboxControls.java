package com.garbri.proigo.core.controls;

public class XboxControls implements IControls{
	Boolean left = false;
	Boolean right = false;
	Boolean accelerate = false;
	Boolean brake = false;
	
	
	public Boolean getBrake() {
		return brake;
	}
	public void setBrake(Boolean brake) {
		this.brake = brake;
	}
	public Boolean getLeft() {
		return left;
	}
	public void setLeft(Boolean left) {
		this.left = left;
	}
	public Boolean getRight() {
		return right;
	}
	public void setRight(Boolean right) {
		this.right = right;
	}
	public Boolean getAccelerate() {
		return accelerate;
	}
	public void setAccelerate(Boolean accelerate) {
		this.accelerate = accelerate;
	}

}
