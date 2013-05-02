package me.igwb.Excavor.Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import resources.ResourceLoader;

import me.igwb.Excavor.Environment.ChunkManager;
import me.igwb.Excavor.Environment.Field;
import me.igwb.Excavor.Environment.ImageSplitter;
import me.igwb.Excavor.Player.Player;
import me.igwb.Excavor.UI.Button;
import me.igwb.Excavor.UI.ButtonLayout;
import me.igwb.Excavor.UI.ConversationManager;
import me.igwb.Excavor.UI.DeveloperConsole;
import me.igwb.Excavor.UI.Label;
import me.igwb.Excavor.UI.PopUpManager;


public class Core {
	
	private Logger log = Logger.getLogger(Core.class.getName());
	private MainWindow GameWindow;
	private MainCanvas GameCanvas;
	private HUDCanvas MainHUD;
	private ChunkManager CM;
	private RenderLogic RL;	
	private Dimension FieldSize = new Dimension(50,50), GameCanvasSize = new Dimension(600,600), HUDCanvasSize = new Dimension(600,80);
	boolean isRunning = false, paused = false, debug = true;
	private int FPS;
	
	private Image viewLimiter;
	
	public Player getActivePlayer() {
		return ActivePlayer;
	}

	private Player ActivePlayer;
	
	protected void initialize() {
		try {
			GameWindow = new MainWindow();
			GameCanvas = new MainCanvas();
			MainHUD = new HUDCanvas();
			
			GameWindow.setLayout(null);
			GameWindow.setSize(900,850);

			GameCanvas.setBounds(50, 50, GameCanvasSize.width, GameCanvasSize.height);
			MainHUD.setBounds(50, 675, HUDCanvasSize.width, HUDCanvasSize.height);
			
			
			GameWindow.add(GameCanvas);
			GameWindow.add(MainHUD);
			
			GameWindow.addKeyListener(new KeyboardListener());
			GameWindow.addFocusListener(new GameFocusListener());
			
			GameWindow.setTitle("Excavor");
			
			GameWindow.setVisible(true);
			
			GameCanvas.createBufferStrategy(2);
			MainHUD.createBufferStrategy(2);
			
			RL = new RenderLogic(FieldSize);
			CM = new ChunkManager();
			
			//---
			CM.loadChunk(System.getProperty("user.dir") + "/map/Chunk;0,0");
			//---
			
			PopUpManager.initialize(ImageSplitter.split(ResourceLoader.getURL("/resources/HUD.png"), 10, 1)[6], 1500, 2000, new Point(60, 60), new Rectangle(0, 0, GameCanvasSize.width, 80));

			ActivePlayer = new Player(new Point(0,0));
			ActivePlayer.initializePlayerBasedHUD(HUDCanvasSize.width, HUDCanvasSize.height);
			ActivePlayer.setPosition(new Point(10, 10));
			
			DeveloperConsole.initialize(GameCanvasSize.width, GameCanvasSize.height / 3);
			
			viewLimiter = resources.ResourceLoader.getImage(("/resources/view.png")) ;
			
			isRunning = true;
			gameLoop();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void gameLoop() {
		final int DESIRED_FRAMES = 150;
		final int OPTIMAL_TIME = 1000000000 / DESIRED_FRAMES;
		
		long lastLoop = System.nanoTime();
		long lastFPSTime = System.nanoTime();
		long updateLength = System.nanoTime();
		
		int loops = 0;
		
		//---
		layout = new ButtonLayout(new Button[] { Button.Yes(10, 50, 200, 80, Color.WHITE), Button.No(10, 150, 200, 80, Color.WHITE) }, viewLimiter);		
		//---
		while(isRunning) {
				updateLength = System.nanoTime() - lastLoop;
				lastLoop = System.nanoTime();

				double delta = updateLength / ((double)OPTIMAL_TIME);

				loops ++;

				if(System.nanoTime() - lastFPSTime >= 1000000000) {
					FPS = loops;
					loops = 0;
					lastFPSTime = System.nanoTime();
				}
				
				if(!paused) {
					updateGame(delta);
					renderGame();
					renderHUD();
				}
				
				try {
					Thread.sleep((System.nanoTime() - lastLoop + OPTIMAL_TIME)/1000000);
				} catch (Exception e) {
					e.printStackTrace();
				}

				
		}
	}
	
	private boolean show = true;
	private ButtonLayout layout;
	
	private void renderGame() {
		
		Graphics g = null;
		try {
			ArrayList<Field> Fields;
			
			BufferStrategy buffer = GameCanvas.getBufferStrategy();

			
			g = buffer.getDrawGraphics();

			//Resetting the canvas!
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 600, 600);

			//---
			if(show) {
				BufferedImage im = new BufferedImage(600, 600, IndexColorModel.TRANSLUCENT);
				Graphics g1 = im.getGraphics();
				Label l = new Label(" Do you want to continue?");
				l.position = new Point(10, 40);
				l.color = Color.WHITE;
				g1.setColor(Color.GREEN);
				g1.fillRect(0, 0, 600, 600);
				l.Render(g1);
				ConversationManager.show(im, new Rectangle(0, 0, 600, 600), layout, g);
				show = false;
			}
			//---
			
			if(debug) {
				g.setColor(Color.RED);

				g.drawString(System.getProperty("user.dir"), 50, 50);
				g.drawString(ActivePlayer.getDirection().name(), 50, 100);
				g.drawString("FPS: " + FPS, 100, 100);
				g.drawString("" + ActivePlayer.isMoving(), 50, 150);
				g.drawString("X: " + ActivePlayer.getPosition().x, GameCanvasSize.width / 2 + 10, GameCanvasSize.height / 2 - 75);
				g.drawString("Y: " + ActivePlayer.getPosition().y, GameCanvasSize.width / 2 + 10, GameCanvasSize.height / 2 - 50);
				
				g.drawLine(0, 0, GameCanvasSize.width, GameCanvasSize.height);
				g.drawLine(GameCanvasSize.width, 0, 0, GameCanvasSize.height);
			}
			
			g.setColor(Color.BLACK);
			
			
			Fields = RL.getRenderFields(ActivePlayer.getPosition(),12);
			
			for (Field field : Fields) {
				g.drawRect((int)(field.getPosition().x * FieldSize.width + ((-1) * ActivePlayer.getPosition().x) + Math.floor(GameCanvasSize.getWidth()/2)), (int)(field.getPosition().y * FieldSize.height + (ActivePlayer.getPosition().y) + Math.floor(GameCanvasSize.getHeight()/2)- FieldSize.height), FieldSize.width, FieldSize.height);	

			}
			
			//g.drawRect((int)(0 + ((-1) * ActivePlayer.getPosition().x) + Math.floor(GameCanvasSize.getWidth()/2)), (int)(0 + (ActivePlayer.getPosition().y) + Math.floor(GameCanvasSize.getHeight()/2)- FieldSize.height), FieldSize.width, FieldSize.height);
			
			//Drawing the view limiter
			g.drawImage(viewLimiter, 0, 0, null);
			
			ConversationManager.Render(g);
			PopUpManager.Render(g);
			
			DeveloperConsole.render(g);
			
			if(!buffer.contentsLost()) {
				buffer.show();
			}
			
		
			GameWindow.repaint();
			Thread.yield();
			
		} catch(Exception e) {
			log.log(Level.SEVERE, "oh oh", e );
			DeveloperConsole.printException(e);
			
		} finally {
			if(g != null)
				g.dispose();
		}
	}

	private void renderHUD() {
		Graphics g = null;

		try {
			BufferStrategy buffer = MainHUD.getBufferStrategy();

			g = buffer.getDrawGraphics();
			
			g.setColor(Color.GRAY);
			g.fillRect(0, 0, HUDCanvasSize.width, HUDCanvasSize.height);
				
		
			ActivePlayer.updatePlayerBasedHUD(g);
			
			if(!buffer.contentsLost()) {
				buffer.show();
			}
			
			GameWindow.repaint();
			Thread.yield();
			
		} catch(Exception e) {
			log.log(Level.SEVERE, "oh oh", e );
			DeveloperConsole.printException(e);
			
		} finally {
			if(g != null)
				g.dispose();
		}
	}

	private void updateGame(double delta) {
		Point PlayerPos = ActivePlayer.getPosition();
		
		//---
		if(!DeveloperConsole.allowUpdate())
			return;
		
		if(!ConversationManager.allowUpdate()) {
			if(layout.wasButtonPressed()) {
				if(layout.getIndex() == 0)
					ConversationManager.destroy();
				else
					System.exit(0);
			}
			return;
		}
		//---
		
		int moveDistance = (int)Math.ceil(1 * delta);
		
		if(ActivePlayer.isMoving()) {
			switch (ActivePlayer.getDirection()) {
			case Up:
				PlayerPos = new Point(PlayerPos.x, PlayerPos.y + moveDistance);
				break;
			case UpLeft:
				PlayerPos = new Point(PlayerPos.x - moveDistance, PlayerPos.y + moveDistance);
				break;
			case UpRight:
				PlayerPos = new Point(PlayerPos.x + moveDistance, PlayerPos.y + moveDistance);
				break;
			case Right:
				PlayerPos = new Point(PlayerPos.x + moveDistance, PlayerPos.y);
				break;
			case Down:
				PlayerPos = new Point(PlayerPos.x, PlayerPos.y - moveDistance);
				break;
			case DownLeft:
				PlayerPos = new Point(PlayerPos.x - moveDistance, PlayerPos.y - moveDistance);
				break;
			case DownRight:
				PlayerPos = new Point(PlayerPos.x + moveDistance, PlayerPos.y - moveDistance);
				break;
			case Left:
				PlayerPos = new Point(PlayerPos.x - moveDistance, PlayerPos.y);
				break;
			default:
				break;
			}
			
			ActivePlayer.setPosition(PlayerPos);
		}	
	}
	
	
	public void setPaused(boolean paused) {
		this.paused = paused;
		if(paused) {
			GameWindow.setTitle("Excavor - PAUSED");
		} else {
			GameWindow.setTitle("Excavor");
		}
	}
	
	public boolean getPaused() {
		return paused;
	}
	
	public ChunkManager getChunkManager() {
		return CM;
	}
	
}
