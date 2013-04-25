package me.igwb.Excavor.Player;

import java.awt.Point;


public class Player {

	private Point Position;
	private int health;
	private Direction Direction;
	
	public enum Direction{Up, Right, Left, Down};
	
	public Point getPosition(){
		return Position;
	}
	
	public void setPosition(Point newPos){
		Position = newPos;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int newHealth) throws PlayerDiedException {
		
		if(newHealth <= 0) {
			throw new PlayerDiedException();
		} else {
			health = newHealth;
		}
			
		
		
	}
	
	public Direction getDirection() {
		return Direction;
	}

	public void setDirection(Direction direction) {
		Direction = direction;
	}
} 
