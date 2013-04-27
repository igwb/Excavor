package me.igwb.Excavor.Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Core {
	
	private Logger log = Logger.getLogger(Core.class.getName());
	private MainWindow GameWindow;
	private MainCanvas GameCanvas;
	private InfoCanvas MainInfo;
	private Dimension FieldSize = new Dimension(50,50), GameCanvasSize = new Dimension(600,600), InfoCanvasSize = new Dimension(600,80);
	boolean isRunning = false;
	
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
		

		
		GameWindow.setVisible(true);
		
		GameCanvas.createBufferStrategy(2);
		MainInfo.createBufferStrategy(2);
		
		run();
		
	}
	
	private void run() {
		log.finer("Game loop started");
		
		isRunning = true;
		
		while(isRunning) {

			RenderGame();
			RenderInfo();
		}
		
		log.fine("Game loop ended");
	}
	
	private void RenderGame() {
		Graphics g = null;

		try {
			BufferStrategy buffer = GameCanvas.getBufferStrategy();

			g = buffer.getDrawGraphics();

			
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 600, 600);
			
			
			g.setColor(Color.BLACK);
			
			
			g.drawString(System.getProperty("user.dir"), 50, 50);
			
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

	private void RenderInfo() {
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

}
