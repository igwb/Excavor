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
import me.igwb.Excavor.Environment.Position;
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
				Field[] zFields = field.getZFields();
				
				Point pos = new Point();
				
				pos.x = field.getRenderLocation().x - ActivePlayer.getPosition().x + (int)Math.floor(GC.GameCanvasSize.width/2);
				pos.y = field.getRenderLocation().y - ActivePlayer.getPosition().y + (int)Math.floor(GC.GameCanvasSize.height/2);
				
				
				for (FieldType t: field.getTypes()) {
					
					g.drawImage(EnvironmentLoader.getImage(t), pos.x, pos.y, null);
				}
				
				if(zFields != null) {
					

					for(Field zField : zFields) {
						pos.x = zField.getRenderLocation().x - ActivePlayer.getPosition().x + (int)Math.floor(GC.GameCanvasSize.width/2);
						pos.y = zField.getRenderLocation().y - ActivePlayer.getPosition().y + (int)Math.floor(GC.GameCanvasSize.height/2);

						
						for (FieldType t: zField.getTypes()) {

							g.drawImage(EnvironmentLoader.getImage(t), pos.x, pos.y, null);
						}
					}
				}
				
			//	GC.log.info("X: " + pos.x + " Y: " + pos.y);
			//	GC.log.info("Field: " + field.getLocation().getX() + " " + field.getLocation().getY() + "  " + field.toString());
				
				g.drawRect(pos.x, pos.y, 104, 52);

				g.setColor(Color.GRAY);
				g.drawString(field.getLocation().getX() + " " + field.getLocation().getY(), pos.x, pos.y + 20);
				g.setColor(Color.BLACK);
			}
			
			
			//Drawing the view limiter
			g.drawImage(viewLimiter, 0, 0, null);
			
			
			if(GC.debug) {
				g.setColor(Color.WHITE);

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
		
		ArrayList<Field> List = new ArrayList<Field>(), adjicentFields, allFields = new ArrayList<Field>();
		Point CenterField, max, min;
		Position centerPos;
		
		//CenterField = new Point((int)(Math.floor(Center.x / Field.SIZE.width)), (int)(Math.floor(Center.y / (Field.SIZE.height * 0.75))));
		centerPos = GC.getChunkManager().getFieldFromAbsolute(Center).getLocation();
		CenterField = new Point(centerPos.getX(), centerPos.getY());
		
		
		min = new Point(CenterField.x - ViewDistance, CenterField.y - (int)(ViewDistance / 0.25));
		max = new Point(CenterField.x + ViewDistance, CenterField.y + (int)(ViewDistance / 0.25));
		
		for (int y = min.y; y < max.y; y++) {
			for (int x = min.x; x < max.x; x++) {


				allFields.add(Programm.getCore().getChunkManager().getFieldAt(new Point(x,y)));
			}
		}
		
		
		GC.log.info("Center: " + CenterField.x + " " + CenterField.y);
		//GC.log.info("L: " + allFields.size());
		
		return allFields;
		/*
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
		
		
		return List;*/
	}
	

	/**
	 * Tells if a point is within a defined area.
	 * 
	 * @param p Point to check
	 * @param min Upper left corner of the area
	 * @param max Lower right corner of the area
	 * @return boolean - true if the point is in the area
	 */
	private boolean pointIsInArea(Position p, Point min, Point max) {
		
		boolean greaterThanMin, lowerThanMax;
		
		greaterThanMin = p.getX() >= min.x && p.getY() >= min.y;
		lowerThanMax = p.getX() <= max.x && p.getY() <= max.y;
		
		
		return greaterThanMin && lowerThanMax;
	}

}
