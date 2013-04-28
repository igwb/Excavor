package me.igwb.Excavor.UI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Conversation {
		
		public Image Background;
		public Rectangle Position;
		public ButtonLayout Layout;
		
		public Conversation(Image background, Rectangle position, ButtonLayout layout) {
			Background = background;
			Position = position;
			Layout = layout;
		}
		
		public void Render(Graphics g) {
			Layout.Render(g);
		}
		
	}