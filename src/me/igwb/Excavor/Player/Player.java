package me.igwb.Excavor.Player;

import java.awt.Point;

import me.igwb.Excavor.Environment.Field;


public class Player {

	private Point Position;
	private int health;
	private Direction dir = Direction.Up;
	
	public enum Direction{Up, Right, Left, Down};
	
	private boolean moving;
	
	
	public Player(Point Position) {
		this.Position = Position;
	}
	
	
	public Point getPosition(){
		return Position;
	}
	
	public void setPosition(Point newPos){
		Position = newPos;
	}
	
	public Point getFieldPosition() {
		Point p = new Point();
		
		p.x = Position.x / Field.SIZE;
		p.y = Position.y / Field.SIZE;
		
		return p;
	}
	
	public void setFieldPosition(Point position) {
		
		Position.x = position.x * Field.SIZE;
		Position.y = position.y * Field.SIZE;
		
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int newHealth) throws PlayerDiedException {
		
		if(newHealth <= 0) {
			killPlayer();
		} else {
			health = newHealth;
		}
	}
	
	public Direction getDirection() {
		return dir;
	}

	public void setDirection(Direction direction) {
		dir = direction;
	}

	public void killPlayer() {
		
	}
	
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	
	public boolean isMoving() {
		return moving;
	}
} 
