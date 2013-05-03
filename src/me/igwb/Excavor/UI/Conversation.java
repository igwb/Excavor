package me.igwb.Excavor.UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import me.igwb.Excavor.Game.Programm;

public class Conversation {

		private static Image Background;
		private static Rectangle Position;
		private static ButtonLayout Layout;
		
		public static void Show(Rectangle position, Image background, ButtonLayout layout) {
			
			Position = position;
			Background = background.getScaledInstance(Position.width, Position.height, 0);
			Layout = layout;
			
			//Programm.getCore().converse();			
			
		}
		
		public static void loop() {
			Layout.Update();
			
			if(Layout.wasButtonPressed()) {
				Position = null;
				Background = null;
				Layout = null;			
				
				//Programm.getCore().converse();
			}
		}
		
		public static void render(Graphics g) {
			
			g.drawImage(Background, Position.x, Position.y, null);
			Layout.Render(g);
			
		}
		
		public static boolean keyPressed(KeyEvent key) {
			
			if(Layout == null)
				return false;
			
			if(key.getKeyCode() == KeyEvent.VK_S)
				Layout.moveIndexUp();
			
			else if(key.getKeyCode() == KeyEvent.VK_W)
				Layout.moveIndexDown();
			
			else if(key.getKeyCode() == KeyEvent.VK_SPACE)
				Layout.setButtonPressed(true);
			
			else
				Layout.setButtonPressed(Layout.getKey(key.getKeyCode()));
			
			
			return true;
		}
}
		
		