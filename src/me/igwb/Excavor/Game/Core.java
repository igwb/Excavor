package me.igwb.Excavor.Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import me.igwb.Excavor.Environment.Field;
import me.igwb.Excavor.Environment.FieldType;
import me.mytl.Excavor.Logic.Delay;

import resources.ResourceLoader;


public class Core {
	
	private Logger log = Logger.getLogger(Core.class.getName());
	private MainWindow GameWindow;
	private MainCanvas GameCanvas;
	private int FieldSize = 50, GameCanvasSize = 600;
	int fps = 0;
	
	URL texture;
	
	GraphicsRenderer gr;
	
	
	protected void initialize() {
		GameWindow = new MainWindow();
		GameCanvas = new MainCanvas();
		
		GameWindow.setSize(900,850);

		
		GameWindow.add(GameCanvas);
		
		
		
		GameWindow.setVisible(true);
		
		GameCanvas.createBufferStrategy(2);
		GameCanvas.setBounds(50, 50, GameCanvasSize, GameCanvasSize);
		
		run();
		
	}
	
	private void run() {
		log.finer("Game loop started");
		
		int frames = 0;
		long totalTime = 0;
		long curTime = 0;
		long lastTime = 0;
		
		int i = 0;
		GameCanvas.setSize(new Dimension(700, 700));
		
		int delay = 1000 / 60;

		Delay d = new Delay(delay);
		
		while(i < 190000000) {
			lastTime = curTime;
			curTime = System.currentTimeMillis();
			
			if(d.checkDelay()) {
				d.expand(1000 / 60);
				
				GameCanvas.setSize(new Dimension(700, 700));
				render();
			}
			
			if(totalTime > 1000) {
				totalTime = 0;
				fps = frames;
				frames = 0;
			}
			frames ++;			
			
			i ++;
		}
		System.exit(0);
		log.fine("Game loop ended");
	}
	
	private void render() {
		Graphics g = null;
		
		try {
			BufferStrategy buffer = GameCanvas.getBufferStrategy();

			g = buffer.getDrawGraphics();

			g.setColor(Color.WHITE);
			
			g.fillRect(0, 0, 1000, 800);
			
			g.setColor(Color.RED);
			
			g.drawRect(0, 0, GameCanvas.getSize().width, GameCanvas.getSize().height);
			
			g.setColor(Color.BLACK);
			
			
			for (int i = 0; i < Math.pow(GameCanvasSize / FieldSize,2); i++) {
				
				
				
			}
			
			g.drawString(System.getProperty("user.dir"), 50, 50);
			g.drawString("FPS: " + fps, 50, 100);
			
			
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
