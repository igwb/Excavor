package me.igwb.Excavor.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.logging.Logger;


public class Core {
	
	private Logger log = Logger.getLogger(Core.class.getName());
	private MainWindow GameWindow;
	private MainCanvas GameCanvas;
	private int FieldSize = 50, GameCanvasSize = 600;
	
	
	protected void initialize() {
		GameWindow = new MainWindow();
		GameCanvas = new MainCanvas();
		
		GameWindow.setSize(900,850);
		GameCanvas.setBounds(50, 50, GameCanvasSize, GameCanvasSize);
		
		GameWindow.add(GameCanvas);
		
		
		GameWindow.setVisible(true);
		
		GameCanvas.createBufferStrategy(2);
		
		run();
		
	}
	
	private void run() {
		log.finer("Game loop started");
		
		int i = 0;
		
		while(i < 1000) {
			
			render();
			i ++;
		}
		
		log.fine("Game loop ended");
	}
	
	private void render() {
		Graphics g = null;
		
		try {
			BufferStrategy buffer = GameCanvas.getBufferStrategy();

			g = buffer.getDrawGraphics();

			g.setColor(Color.BLACK);

			
			g.fillRect(0, 0, 20, 20);
			g.drawRect(0, 0, 600, 600);
			
			
			for (int i = 0; i < Math.pow(GameCanvasSize / FieldSize,2); i++) {
				
				g.drawRect((int)(FieldSize * Math.ceil(i/(GameCanvasSize / FieldSize))), (int)Math.floor((FieldSize * Math.floor(i/(GameCanvasSize / FieldSize)))),FieldSize, FieldSize);
			}
			
			g.drawString(System.getProperty("user.dir"), 50, 50);
			
			if(!buffer.contentsLost()) {
				buffer.show();
			}
				
				
			Thread.yield();
			
		} finally {
			if(g != null)
				g.dispose();
		}
	}
	
}
