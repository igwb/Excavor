package me.igwb.Excavor.UI;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Conversation {

		private Image Background;
		private Rectangle Position;
		private ButtonLayout Layout;
		
		public Conversation(Rectangle position, Image background, ButtonLayout layout) {
			
			Position = position;
			Background = background.getScaledInstance(Position.width, Position.height, 0);
			Layout = layout;		
			
		}
		
		public void loop() {
			Layout.Update();
			
			if(Layout.wasButtonPressed()) {
				Position = null;
				Background = null;
				Layout = null;			

			}
		}
		
		public void render(Graphics g) {
			
			g.drawImage(Background, Position.x, Position.y, null);
			Layout.Render(g);
			
		}
		
		public boolean keyPressed(KeyEvent key) {
			
			if(key.getKeyCode() == KeyEvent.VK_S)
				Layout.moveIndexUp();
			
			else if(key.getKeyCode() == KeyEvent.VK_W)
				Layout.moveIndexDown();
			
			else if(key.getKeyCode() == KeyEvent.VK_SPACE)
				Layout.setButtonPressed(true);
			
			else
				return false;
			
			return true;
		}
}
		
		