package me.igwb.Excavor.Environment;



public class Position {

	private Integer x, y, z;
	

	Position() {
		x = 0;
		y = 0;
		z = 0;
	}
	
	Position(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public Integer getZ() {
		return z;
	}

	public void setZ(Integer z) {
		this.z = z;
	}


	public boolean equals(Object obj) {
		
		if(obj instanceof Position) {
			if(((Position) obj).getX() == x && ((Position) obj).getY() == y && ((Position) obj).getZ() == z) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	
}
