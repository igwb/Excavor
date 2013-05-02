package me.igwb.Excavor.UI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Conversation {
		
		public Image Background;
		public Rectangle Position;
		public ButtonLayout Layout;
		
		public Conversation(Image background, Rectangle position, ButtonLayout layout) {
			Background = background.getScaledInstance(position.width, position.height, 1);
			Position = position;
			Layout = layout;
		}
		
		public void Render(Graphics g) {
			g.drawImage(Background, Position.x, Position.y, null);
			Layout.Render(g);
		}
		
	}