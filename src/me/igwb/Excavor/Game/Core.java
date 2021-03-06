package me.igwb.Excavor.Game;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.logging.Logger;

import resources.EnvironmentLoader;
import resources.ResourceLoader;

import me.igwb.Excavor.Environment.ChunkManager;
import me.igwb.Excavor.Environment.ImageSplitter;
import me.igwb.Excavor.Logic.GameLogic;
import me.igwb.Excavor.Media.BackgroundMusic;
import me.igwb.Excavor.Media.MediaManager;
import me.igwb.Excavor.Media.RegisteredMedia;
import me.igwb.Excavor.Player.Player;
import me.igwb.Excavor.UI.ConversationManager;
import me.igwb.Excavor.UI.DeveloperConsole;
import me.igwb.Excavor.UI.PopUpManager;


public class Core {
	
	public final Logger log = Logger.getLogger(Core.class.getName());
	
	private MainWindow GameWindow;
	private MainCanvas GameCanvas;
	private HUDCanvas MainHUD;
	
	private ChunkManager CM;
	private RenderLogic renderManager;	
	private GameLogic Brain;
	
	public final Dimension GameCanvasSize = new Dimension(600,600), HUDCanvasSize = new Dimension(600,80);
	
	boolean isRunning = false, paused = false, debug = true;
	
	private int FPS;
	
	private Player ActivePlayer;
	
	public BackgroundMusic backgroundMusic;
	
	public Player getActivePlayer() {
		return ActivePlayer;
	}

	
	
	protected void initialize() {
		try {
			ValueManager.loadValuesFrom(System.getProperty("user.dir") + "/configuration.txt");
			DeveloperConsole.initialize(GameCanvasSize.width, GameCanvasSize.height / 3);
			EnvironmentLoader.initialize();
			
			GameWindow = new MainWindow();
			GameCanvas = new MainCanvas();
			MainHUD = new HUDCanvas();
			
			GameWindow.setLayout(null);
			GameWindow.setSize(900,850);

			GameCanvas.setBounds(50, 50, GameCanvasSize.width, GameCanvasSize.height);
			MainHUD.setBounds(50, 655, HUDCanvasSize.width, HUDCanvasSize.height);
			
			
			GameWindow.add(GameCanvas);
			GameWindow.add(MainHUD);
			
			GameWindow.addKeyListener(new KeyboardListener());
			GameWindow.addFocusListener(new GameFocusListener());
			
			GameWindow.setTitle("Excavor");
			
			GameWindow.setVisible(true);
			
			GameCanvas.createBufferStrategy(2);
			MainHUD.createBufferStrategy(2);
			
			Brain = new GameLogic();
			
			renderManager = new RenderLogic();
			CM = new ChunkManager();
			
			CM.loadChunk(0,0);

			StandardListeners.registerListeners();
			ConversationManager.initialize("Scripts");
			MediaManager.initialize();
			PopUpManager.initialize(ImageSplitter.split(ResourceLoader.getURL("/resources/HUD.png"), 10, 1)[6], 1500, 2000, new Point(60, 60), new Rectangle(0, 0, GameCanvasSize.width, 80));

			backgroundMusic = new BackgroundMusic(MediaManager.getClip(RegisteredMedia.ExcavorTheme.getMediaName()));
			backgroundMusic.play(backgroundMusic.getIntro(), backgroundMusic.getLoop());
			
			ActivePlayer = new Player(new Point(0,0));
			
			isRunning = true;
			gameLoop();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
		//ConversationManager.startConversation("Test conversation");
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
					Brain.update(delta);
					renderManager.renderGame();
					renderManager.renderHud();
				}
				
				try {
					Thread.sleep((System.nanoTime() - lastLoop + OPTIMAL_TIME)/1000000);
				} catch (Exception e) {
					e.printStackTrace();
				}

				
		}
	}
	
	/**
	 * Returns the amount of frames per second.
	 * 
	 * @return frames per second as an integer
	 */
	public int getFps() {
		return FPS;
	}
	
	/**
	 * Returns the active game window.
	 * 
	 * @return active game window
	 */
	public MainWindow getGameWindow() {
		return GameWindow;
	}
	
	/**
	 * Returns the active game canvas.
	 * 
	 * @return active game canvas
	 */
	public MainCanvas getGameCanvas() {
		return GameCanvas;
	}

	/**
	 * Returns the active HUD canvas.
	 * 
	 * @return active HUD canvas
	 */
	public HUDCanvas getHudCanvas() {
		
		return MainHUD;
	}
	
	
	/**
	 * Pause or un-pause the game with this method.
	 * 
	 * @param paused Sets the game paused state to this value.
	 */
	public void setPaused(boolean paused) {
		this.paused = paused;
		if(paused) {
			GameWindow.setTitle("Excavor - PAUSED");
			MediaManager.pauseAll();
		} else {
			GameWindow.setTitle("Excavor");
			MediaManager.resumeAll();
		}
	}
	
	/**
	 * 
	 * @return Returns a boolean whether the game is paused or not.
	 */
	public boolean getPaused() {
		
		return paused;
	}
	
	/**
	 * 
	 * @return ChunkManager Returns the active ChunkManager.
	 */
	public ChunkManager getChunkManager() {
		return CM;
	}
	
}
