package me.igwb.Excavor.Player;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.IOException;
import java.net.URL;

import resources.ResourceLoader;

import me.igwb.Excavor.Environment.Field;
import me.igwb.Excavor.Environment.ImageSplitter;
import me.igwb.Excavor.Logic.Delay;
import me.igwb.Excavor.UI.Label;
import me.igwb.Excavor.UI.PopUpManager;


public class Player {

	private Point Position;
	private int health, armor, maxHealth, maxArmor;
	private Direction dir = Direction.Up;
	
	private Image aBar, hBar, Armor, Health, redABar, redHBar;
	
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
	
	public void initializePlayerBasedHUD(int maxWidth, int maxHeight) throws IOException {
		
		URL image = ResourceLoader.getURL("/resources/HUD.png");
		
		Health = ImageSplitter.split(image, 10, 1)[0].getScaledInstance(maxWidth, maxHeight, 0);
		hBar = ImageSplitter.split(image, 10, 1)[1].getScaledInstance(maxWidth, maxHeight, 0);
		
		Armor = ImageSplitter.split(image, 10, 1)[2].getScaledInstance(maxWidth, maxHeight, 0);
		aBar = ImageSplitter.split(image, 10, 1)[3].getScaledInstance(maxWidth, maxHeight, 0);
		
		redHBar = ImageSplitter.split(image, 10, 1)[4].getScaledInstance(maxWidth, maxHeight, 0);
		redABar = ImageSplitter.split(image, 10, 1)[5].getScaledInstance(maxWidth, maxHeight, 0);
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
	
	public void setHealth(int newHealth) {
		
		if(newHealth <= 0) {
			killPlayer();
			health = newHealth;
		} else {
			health = newHealth;
		}
	}
	
	public int getArmor() {
		return armor;
	}


	public void setArmor(int armor) {
		this.armor = armor;
	}


	public int getMaxHealth() {
		return maxHealth;
	}


	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}


	public int getMaxArmor() {
		return maxArmor;
	}


	public void setMaxArmor(int maxArmor) {
		this.maxArmor = maxArmor;
	}


	public Direction getDirection() {
		return dir;
	}

	public void setDirection(Direction direction) {
		dir = direction;
	}

	public void killPlayer() {
		//System.exit(health);
	}
	
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	
	public boolean isMoving() {
		return moving;
	}
	
	private Delay delay;
	private boolean red = false, delayNeeded = false;
	
	public void updatePlayerBasedHUD(Graphics g) {
		
		if(delayNeeded) {
			if(delay == null)
				delay = new Delay(500);
			
			if(delay.checkDelay()) {
				red = !red;
				delay.expand();
			}
		} else {
			if(delay != null)
				delay = null;
		}
		
		if(health <= 0) {
			
			g.setColor(Color.GREEN);
			g.setFont(Font.getFont("Sans Bold"));
			g.drawString("Wow... you don't look so good...", 15, 15);
			return;
		}
		
		g.drawImage(Health, 15, 2, null);		
		g.drawImage(getHealthBar(), 15, 2, null);
		
		if(armor <= 0)
			return;
		
		g.drawImage(Armor, 15, 2, null);
		g.drawImage(getArmorBar(), 15, 2, null);
		
		if((int)((double) health / (double) maxHealth) * 100 <= 25)
			delayNeeded = true;
	}
	
	public Image getArmorBar() {
		
		int width = (int)((double) armor / (double) maxArmor * 259) + 297;

		if(width <= 0)
			width = 1;
		
		BufferedImage buffImage = new BufferedImage(width, aBar.getHeight(null), IndexColorModel.TRANSLUCENT);
		
		if((int)((double) armor / (double) maxArmor * 100) <= 25) {
			delayNeeded = true;
			
			if(red)
				buffImage.getGraphics().drawImage(redABar, 0, 0, null);
			else
				buffImage.getGraphics().drawImage(aBar, 0, 0, null);
			
		} else {
			buffImage.getGraphics().drawImage(aBar, 0, 0, null);
			delayNeeded = false;
		}
		
		
		return buffImage;
	}
	
	public Image getHealthBar() {
		
		int width = (int)((double) health / (double) maxHealth * 541) + 13;

		if(width <= 0)
			width = 1;
		
		BufferedImage buffImage = new BufferedImage(width, hBar.getHeight(null), IndexColorModel.TRANSLUCENT);
		
		if((int)((double) health / (double) maxHealth * 100) <= 25) {
			delayNeeded = true;
			
			if(red)
				buffImage.getGraphics().drawImage(redHBar, 0, 0, null);
			else
				buffImage.getGraphics().drawImage(hBar, 0, 0, null);
			
		} else {
			buffImage.getGraphics().drawImage(hBar, 0, 0, null);
			delayNeeded = false;
		}
		
		return buffImage;
	}
} 
