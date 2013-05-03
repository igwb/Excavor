package me.igwb.Excavor.Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;

import resources.EnvironmentLoader;
import resources.ResourceLoader;

import me.igwb.Excavor.Environment.Field;
import me.igwb.Excavor.Environment.FieldType;
import me.igwb.Excavor.Environment.ImageSplitter;
import me.igwb.Excavor.Logic.Delay;
import me.igwb.Excavor.Player.Player;
//import me.igwb.Excavor.UI.ConversationManager;
import me.igwb.Excavor.UI.DeveloperConsole;
import me.igwb.Excavor.UI.PopUpManager;

public class RenderLogic {

	private MainCanvas GameCanvas;
	private MainWindow GameWindow;
	private HUDCanvas MainHud;
	private Core GC;
	private Image aBar, hBar, Armor, Health, redABar, redHBar, viewLimiter;
	
	/**
	 * Creates an instance of the RenderLogic class.
	 * 
	 * @param FieldSize
	 * @throws IOException Thrown if graphics are missing.
	 */
	public RenderLogic() throws IOException{
			
			GC = Programm.getCore();
			
			GameCanvas = GC.getGameCanvas();
			GameWindow = GC.getGameWindow();
			MainHud = GC.getHudCanvas();
			
			initializePlayerBasedHUD(GC.HUDCanvasSize.width, GC.HUDCanvasSize.height);
			viewLimiter = resources.ResourceLoader.getImage(("/resources/view.png"));
	}

	
	/**
	 *  Loads the graphics needed for the PlayerBased HUD.
	 *  
	 * @param maxWidth Maximum width of the canvas the HUD is drawn on.
	 * @param maxHeight Maximum height of the canvas the HUD is drawn on.
	 * @throws IOException Thrown if there is a problem with some graphics.
	 */
	private void initializePlayerBasedHUD(int maxWidth, int maxHeight) throws IOException {
		
		URL image = ResourceLoader.getURL("/resources/HUD.png");
		
		Health = ImageSplitter.split(image, 10, 1)[0].getScaledInstance(maxWidth, maxHeight, 0);
		hBar = ImageSplitter.split(image, 10, 1)[1].getScaledInstance(maxWidth, maxHeight, 0);
		
		Armor = ImageSplitter.split(image, 10, 1)[2].getScaledInstance(maxWidth, maxHeight, 0);
		aBar = ImageSplitter.split(image, 10, 1)[3].getScaledInstance(maxWidth, maxHeight, 0);
		
		redHBar = ImageSplitter.split(image, 10, 1)[4].getScaledInstance(maxWidth, maxHeight, 0);
		redABar = ImageSplitter.split(image, 10, 1)[5].getScaledInstance(maxWidth, maxHeight, 0);
	}

	protected void renderGame() {
		
		Graphics g = null;
		
		try {
			ArrayList<Field> Fields;
			Player ActivePlayer = GC.getActivePlayer();
			
			
			BufferStrategy buffer = GameCanvas.getBufferStrategy();

			
			g = buffer.getDrawGraphics();

			//Resetting the canvas!
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 600, 600);
			

			
			g.setColor(Color.BLACK);
			
			
			Fields = getRenderFields(ActivePlayer.getPosition(),7);
			
			for (Field field : Fields) {
				Point pos = new Point();
				
				pos.x = field.getPosition().x - ActivePlayer.getPosition().x + (int)Math.floor(GC.GameCanvasSize.width/2);
				pos.y = field.getPosition().y - ActivePlayer.getPosition().y + (int)Math.floor(GC.GameCanvasSize.height/2);
				
				for (FieldType t: field.getTypes()) {
					
					g.drawImage(EnvironmentLoader.getImage(t), pos.x, pos.y, null);
				}
				
				g.drawRect(pos.x, pos.y, Field.SIZE, Field.SIZE);

				//g.drawString(field.getPosition().x + " " + field.getPosition().y, pos.x, pos.y + 20);
			}
			
			
			//Drawing the view limiter
			g.drawImage(viewLimiter, 0, 0, null);
			
			
			if(GC.debug) {
				g.setColor(Color.RED);

				g.drawString(System.getProperty("user.dir"), 50, 50);
				g.drawString(ActivePlayer.getDirection().name(), 50, 100);
				g.drawString("FPS: " + GC.getFps(), 100, 100);
				g.drawString("" + ActivePlayer.isMoving(), 50, 150);
				g.drawString("X: " + ActivePlayer.getPosition().x, GC.GameCanvasSize.width / 2 + 10, GC.GameCanvasSize.height / 2 - 75);
				g.drawString("Y: " + ActivePlayer.getPosition().y, GC.GameCanvasSize.width / 2 + 10, GC.GameCanvasSize.height / 2 - 50);

				g.drawLine(0, 0, GC.GameCanvasSize.width, GC.GameCanvasSize.height);
				g.drawLine(GC.GameCanvasSize.width, 0, 0, GC.GameCanvasSize.height);
			}
			
			//ConversationManager.Render(g);
			PopUpManager.Render(g);
			
			DeveloperConsole.render(g);
			

			
			if(!buffer.contentsLost()) {
				buffer.show();
			}
			
		
			GameWindow.repaint();
			Thread.yield();
			
		} catch(Exception e) {
			GC.log.log(Level.SEVERE, "oh oh", e );
			DeveloperConsole.printException(e);
			
		} finally {
			if(g != null)
				g.dispose();
		}
	}
	
	private Delay delay;
	private boolean red = false, delayNeeded = false;
	
	protected void renderHud() {

		Graphics g = null;

		try {
			BufferStrategy buffer = MainHud.getBufferStrategy();

			g = buffer.getDrawGraphics();

			g.setColor(Color.WHITE);
			g.fillRect(0, 0, GC.HUDCanvasSize.width, GC.HUDCanvasSize.height);


			Player ActivePlayer = GC.getActivePlayer();
			int health, maxHealth, armor;

			health = ActivePlayer.getHealth();
			maxHealth = ActivePlayer.getMaxHealth();
			armor = ActivePlayer.getArmor();

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

			if(armor > 0) {

				g.drawImage(Armor, 15, 2, null);
				g.drawImage(getArmorBar(), 15, 2, null);

				if((int)((double) health / (double) maxHealth) * 100 <= 25) {
					delayNeeded = true;
				}

			}
			
			if(!buffer.contentsLost()) {
				buffer.show();
			}

			GameWindow.repaint();
			Thread.yield();

		} catch(Exception e) {
			GC.log.log(Level.SEVERE, "oh oh", e );
			DeveloperConsole.printException(e);

		} finally {
			if(g != null)
				g.dispose();
		}
	}
	
	private Image getArmorBar() {
		Player ActivePlayer = GC.getActivePlayer();
		int  maxArmor, armor;
		
		maxArmor = ActivePlayer.getMaxHealth();
		armor = ActivePlayer.getArmor();
		
		
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
	
	private Image getHealthBar() {
		
		Player ActivePlayer = GC.getActivePlayer();
		int health, maxHealth;
		
		health = ActivePlayer.getHealth();
		maxHealth = ActivePlayer.getMaxHealth();
		
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
	
	
	public ArrayList<Field> getRenderFields(Point Center, int ViewDistance) {
		
		ArrayList<Field> List = new ArrayList<Field>(), adjicentFields;
		Point CenterField, max, min;
		
		
		CenterField = new Point((int)(Field.SIZE * Math.floor(Center.x / Field.SIZE)), (int)(Field.SIZE * Math.floor(Center.y / Field.SIZE)));
		
		
		min = new Point(CenterField.x - ViewDistance * Field.SIZE , CenterField.y - ViewDistance * Field.SIZE);
		max = new Point(CenterField.x + ViewDistance * Field.SIZE, CenterField.y + ViewDistance * Field.SIZE);
		

		
		//Add CenterField
		List.add(Programm.getCore().getChunkManager().getFieldAt((CenterField)));
		
		for (int i = 0; i < List.size(); i++) {
			
			adjicentFields = getAdjicentRender(List.get(i), max, min);
			
			for (int j = 0; j < adjicentFields.size(); j++) {
				Field cur = adjicentFields.get(j);
				
				if(!List.contains(cur)) {
				
					List.add(cur);
				}
			}
		}
		
		
		return List;
	}
	
	private ArrayList<Field> getAdjicentRender(Field RootField, Point max, Point min) {
		
		ArrayList<Field> List = new ArrayList<Field>();
		Point curPoint;
		Field curField;
		
		
		//return if field is not transparent
		if(!RootField.getSeeThru()) {
			return List;
		}
		
		
		curPoint = new Point(RootField.getPosition().x, RootField.getPosition().y + Field.SIZE);
		curField = Programm.getCore().getChunkManager().getFieldAt(curPoint);
		if(curField != null && curField.getSeeThru() && pointIsInArea(curField.getPosition(), min, max)) {
			List.add(curField);
		}
		
		curPoint = new Point(RootField.getPosition().x + Field.SIZE, RootField.getPosition().y + Field.SIZE);
		curField = Programm.getCore().getChunkManager().getFieldAt(curPoint);
		if(curField != null && curField.getSeeThru() && pointIsInArea(curField.getPosition(), min, max)) {
			List.add(curField);
		}
		
		curPoint = new Point(RootField.getPosition().x + Field.SIZE, RootField.getPosition().y);
		curField = Programm.getCore().getChunkManager().getFieldAt(curPoint);
		if(curField != null && curField.getSeeThru() && pointIsInArea(curField.getPosition(), min, max)) {
			List.add(curField);
		}
		
		curPoint = new Point(RootField.getPosition().x + Field.SIZE , RootField.getPosition().y - Field.SIZE);
		curField = Programm.getCore().getChunkManager().getFieldAt(curPoint);
		if(curField != null && curField.getSeeThru() && pointIsInArea(curField.getPosition(), min, max)) {
			List.add(curField);
		}
		
		curPoint = new Point(RootField.getPosition().x, RootField.getPosition().y - Field.SIZE);
		curField = Programm.getCore().getChunkManager().getFieldAt(curPoint);
		if(curField != null && curField.getSeeThru() && pointIsInArea(curField.getPosition(), min, max)) {
			List.add(curField);
		}
		
		curPoint = new Point(RootField.getPosition().x - Field.SIZE, RootField.getPosition().y - Field.SIZE);
		curField = Programm.getCore().getChunkManager().getFieldAt(curPoint);
		if(curField != null && curField.getSeeThru() && pointIsInArea(curField.getPosition(), min, max)) {
			List.add(curField);
		}
		
		curPoint = new Point(RootField.getPosition().x - Field.SIZE, RootField.getPosition().y);
		curField = Programm.getCore().getChunkManager().getFieldAt(curPoint);
		if(curField != null && curField.getSeeThru() && pointIsInArea(curField.getPosition(), min, max)) {
			List.add(curField);
		}
		
		curPoint = new Point(RootField.getPosition().x - Field.SIZE, RootField.getPosition().y + Field.SIZE);
		curField = Programm.getCore().getChunkManager().getFieldAt(curPoint);
		if(curField != null && curField.getSeeThru() && pointIsInArea(curField.getPosition(), min, max)) {
			List.add(curField);
		}
		
		return List;
	}


	/**
	 * Tells if a point is within a defined area.
	 * 
	 * @param p Point to check
	 * @param min Upper left corner of the area
	 * @param max Lower right corner of the area
	 * @return boolean - true if the point is in the area
	 */
	private boolean pointIsInArea(Point p, Point min, Point max) {
		
		boolean greaterThanMin, lowerThanMax;
		
		greaterThanMin = p.x >= min.x && p.y >= min.y;
		lowerThanMax = p.x <= max.x && p.y <= max.y;
		
		
		return greaterThanMin && lowerThanMax;
	}

}
