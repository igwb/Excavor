package me.igwb.Excavor.Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.igwb.Excavor.Environment.ChunkManager;
import me.igwb.Excavor.Environment.Field;
import me.igwb.Excavor.Player.Player;


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
			
			
			ActivePlayer = new Player(new Point(0,0));
			ActivePlayer.initializePlayerBasedHUD(HUDCanvasSize.width, HUDCanvasSize.height);
			
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
	
	private void renderGame() {
		
		Graphics g = null;
		try {
			ArrayList<Field> Fields;
			
			BufferStrategy buffer = GameCanvas.getBufferStrategy();

			g = buffer.getDrawGraphics();

			//Resetting the canvas!
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 600, 600);
			
			
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
			
			
			//TODO: FIX THE DAMN CHUNKCLASS, MYTL
			
			//Fields = RL.getRenderFields(ActivePlayer.getPosition(),12);
			
			//for (Field field : Fields) {
			//g.drawRect((int)(0 + Math.abs(ActivePlayer.getPosition().x) + Math.floor(GameCanvasSize.getWidth()/2)), (int)(0 + Math.abs(ActivePlayer.getPosition().y) + Math.floor(GameCanvasSize.getHeight()/2)), FieldSize.width, FieldSize.height);				
			//}
			
			g.drawRect((int)(0 + ((-1) * ActivePlayer.getPosition().x) + Math.floor(GameCanvasSize.getWidth()/2)), (int)(0 + (ActivePlayer.getPosition().y) + Math.floor(GameCanvasSize.getHeight()/2)- FieldSize.height), FieldSize.width, FieldSize.height);
			
	
			
			
			//Drawing the view limiter
			g.drawImage(viewLimiter, 0, 0, null);
			
			if(!buffer.contentsLost()) {
				buffer.show();
			}
				
		
			GameWindow.repaint();
			Thread.yield();
			
		} catch(Exception e) {
			log.log(Level.SEVERE, "oh oh", e );
			
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
			
		} finally {
			if(g != null)
				g.dispose();
		}
	}

	private void updateGame(double delta) {
		Point PlayerPos = ActivePlayer.getPosition();
		
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
