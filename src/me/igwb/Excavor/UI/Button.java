package me.igwb.Excavor.UI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Button {

	public char ShortcutKey = 'x';
	
	public Image Background = null;
	
	public Rectangle Position = new Rectangle(0, 0, 0, 0);
	
	public Button(Image background, Rectangle position, char shortcutKey) {
		ShortcutKey = shortcutKey;
		Position = position;
		Background = background.getScaledInstance(Position.width, Position.height, 0);
	}
	
	public void Render(Graphics g) {
		g.drawImage(Background, Position.x, Position.y, null);
	}
	
}
