package me.igwb.Excavor.Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.org.apache.bcel.internal.generic.CASTORE;

import me.igwb.Excavor.Environment.ChunkManager;
import me.igwb.Excavor.Player.Player;


public class Core {
	
	private Logger log = Logger.getLogger(Core.class.getName());
	private MainWindow GameWindow;
	private MainCanvas GameCanvas;
	private InfoCanvas MainInfo;
	private ChunkManager CM;
	private Dimension FieldSize = new Dimension(50,50), GameCanvasSize = new Dimension(600,600), InfoCanvasSize = new Dimension(600,80);
	boolean isRunning = false, paused = false;
	private int FPS;
	
	
	public Player getActivePlayer() {
		return ActivePlayer;
	}

	private Player ActivePlayer;
	
	protected void initialize() {
		GameWindow = new MainWindow();
		GameCanvas = new MainCanvas();
		MainInfo = new InfoCanvas();
		
		GameWindow.setLayout(null);
		GameWindow.setSize(900,850);

		GameCanvas.setBounds(50, 50, GameCanvasSize.width, GameCanvasSize.height);
		MainInfo.setBounds(50, 675, InfoCanvasSize.width, InfoCanvasSize.height);
		
		
		GameWindow.add(GameCanvas);
		GameWindow.add(MainInfo);
		
		GameWindow.addKeyListener(new KeyboardListener());
		
		GameWindow.setVisible(true);
		
		GameCanvas.createBufferStrategy(2);
		MainInfo.createBufferStrategy(2);
		
		CM = new ChunkManager();
		
		ActivePlayer = new Player(new Point(0,0));
		
		//run();
		isRunning = true;
		gameLoop();
		
	}
	
	private void run() {
		log.finer("Game loop started");
		
		isRunning = true;
		paused = false;
		
		
		while(isRunning) {
			
			if(!paused) {
				
				updateGame();
				renderGame();
				renderInfo();
				
			//	Thread.yield();
			}
		}
		
		log.fine("Game loop ended");
	}
	
	private void gameLoop() {
		final int DESIRED_FRAMES = 50;
		final int SKIP_MS = 1000 / DESIRED_FRAMES;
		
		long lastFPSTime = System.nanoTime();
		
		int loops = 0;
		
		
		while(isRunning) {
			
			updateGame();
			renderGame();
			renderInfo();
			
			loops ++;
			
			if(System.nanoTime() - lastFPSTime >= 1000000000) {
				FPS = loops;
				loops = 0;
				lastFPSTime = System.nanoTime();
			}
			
		}
	}
	
	private void renderGame() {
		Graphics g = null;

		try {
			BufferStrategy buffer = GameCanvas.getBufferStrategy();

			g = buffer.getDrawGraphics();

			
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 600, 600);
			
			
			g.setColor(Color.RED);
			
			
			g.drawString(System.getProperty("user.dir"), 50, 50);
			g.drawString(ActivePlayer.getDirection().name(), 50, 100);
			g.drawString("FPS: " + FPS, 100, 100);
			g.drawString("" + ActivePlayer.isMoving(), 50, 150);
			g.drawString("X: " + ActivePlayer.getPosition().x, GameCanvasSize.width / 2 + 10, GameCanvasSize.height / 2 - 75);
			g.drawString("Y: " + ActivePlayer.getPosition().y, GameCanvasSize.width / 2 + 10, GameCanvasSize.height / 2 - 50);
			
			Image img = resources.ResourceLoader.getImage(("/resources/view.png"));
			
			
			g.drawImage(img, 0, 0, null);
			
			
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

	private void renderInfo() {
		Graphics g = null;

		try {
			BufferStrategy buffer = MainInfo.getBufferStrategy();

			g = buffer.getDrawGraphics();
			
			g.setColor(Color.GRAY);
			g.fillRect(0, 0, InfoCanvasSize.width, InfoCanvasSize.height);
				
		
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

	private void updateGame() {
		Point PlayerPos = ActivePlayer.getPosition();
		if(ActivePlayer.isMoving()) {
			switch (ActivePlayer.getDirection()) {
			case Up:
				PlayerPos = new Point(PlayerPos.x, PlayerPos.y + 1);
				break;
			case Right:
				PlayerPos = new Point(PlayerPos.x + 1, PlayerPos.y);
				break;
			case Down:
				PlayerPos = new Point(PlayerPos.x, PlayerPos.y - 1);
				break;
			case Left:
				PlayerPos = new Point(PlayerPos.x - 1, PlayerPos.y);
				break;
			default:
				break;
			}
			
			ActivePlayer.setPosition(PlayerPos);
		}
		
		
	}
	
	public ChunkManager getChunkManager() {
		return CM;
	}
	
}
