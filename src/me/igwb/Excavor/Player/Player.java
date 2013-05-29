package me.igwb.Excavor.Player;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

import me.igwb.Excavor.Environment.Field;


public class Player {

	private Point Position;
	private int health, armor, maxHealth, maxArmor;
	private Direction dir = Direction.Up;
	
	
	private Image[] player;
	
	public enum Direction{Up, Right, Left, Down, UpLeft, UpRight, DownLeft, DownRight};
	
	private boolean moving;
	
	
	public Player(Point Position) {
		this.Position = Position;
		
		health = 50;
		armor = 0;
		
		maxHealth = 100;
		maxArmor = 100;
		
	}
	

	
	
	public Point getPosition(){
		return Position;
	}
	
	public void setPosition(Point newPos){
		Position = newPos;
	}
	
	public Point getFieldPosition() {
		Point p = new Point();
		
		p.x = Position.x / Field.SIZE.width;
		p.y = Position.y / Field.SIZE.height;
		
		return p;
	}
	
	public void setFieldPosition(Point position) {
		
		Position.x = position.x * Field.SIZE.width;
		Position.y = position.y * Field.SIZE.height;
		
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int newHealth) {
		
		if(newHealth > maxHealth)
			return;
		
		if(newHealth <= 0) {
			//TODO: The player should die here!
		} else {
			health = newHealth;
		}
	}
	
	public int getArmor() {
		return armor;
	}


	public void setArmor(int newArmor) {
		
		if(newArmor > maxArmor)
			return;
		
		if(newArmor <= 0) {
			//TODO: Armor should be destroyed / removed. 
		} else {
			armor = newArmor;
		}
		
		
	}


	public int getMaxHealth() {
		return maxHealth;
	}


	public void setMaxHealth(int maxHealth) {
		
		if(maxHealth <= 0)
			return;
		
		if(health > maxHealth) {
			health = maxHealth;
		}
		
		this.maxHealth = maxHealth;
		
	}


	public int getMaxArmor() {
		return maxArmor;
	}


	public void setMaxArmor(int maxArmor) {
		if(maxArmor <= 0)
			return;
		
		if(armor > maxArmor) {
			armor = maxArmor;
		}
		
		this.maxArmor = maxArmor;
		
	}


	public Direction getDirection() {
		return dir;
	}

	public void setDirection(Direction direction) {
		dir = direction;
	}
	
	public void setMoving(boolean moving) {
		
		this.moving = moving;
	}
	
	public boolean isMoving() {
		
		return moving;
	}
	
	
	
	public String[] getPlayerData() {
		
		ArrayList<String> lines = new ArrayList<String>();
		
		lines.add("#Playerstats: " + new java.util.Date().toString());
		lines.add("#Player condition");
		lines.add("health=" + health + "/" + maxHealth);
		lines.add("armor=" + armor + "/" + maxArmor);
		lines.add("#Player -> world");
		lines.add("position=" + Position.x + "," + Position.y);
		lines.add("direction=" + dir.toString());
		
		return lines.toArray(new String[lines.size()]);
	}
} 
