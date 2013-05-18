package com.garbri.proigo.core.controls;

public class Cont {
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accelerate == null) ? 0 : accelerate.hashCode());
		result = prime * result + ((brake == null) ? 0 : brake.hashCode());
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cont other = (Cont) obj;
		if (accelerate == null) {
			if (other.accelerate != null)
				return false;
		} else if (!accelerate.equals(other.accelerate))
			return false;
		if (brake == null) {
			if (other.brake != null)
				return false;
		} else if (!brake.equals(other.brake))
			return false;
		if (left == null) {
			if (other.left != null)
				return false;
		} else if (!left.equals(other.left))
			return false;
		if (right == null) {
			if (other.right != null)
				return false;
		} else if (!right.equals(other.right))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Cont [left=" + left + ", right=" + right + ", accelerate="
				+ accelerate + ", brake=" + brake + "]";
	}

	

}
