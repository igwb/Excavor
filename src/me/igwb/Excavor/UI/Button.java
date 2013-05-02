package me.igwb.Excavor.UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;

public class Button {

	public char ShortcutKey = 'x';
	
	public Image Background = null;
	
	public Rectangle Position = new Rectangle(0, 0, 0, 0);
	
	public Label label;
	
	public Button(Image background, Label label, Rectangle position, char shortcutKey) {
		ShortcutKey = shortcutKey;
		Position = position;
		Background = background.getScaledInstance(Position.width, Position.height, 0);
		this.label = label;
	}
	
	public void Render(Graphics g) {
		BufferedImage image = new BufferedImage(Background.getWidth(null), Background.getHeight(null), IndexColorModel.TRANSLUCENT);		
		Graphics G = image.getGraphics();
		G.drawImage(Background, 0, 0, null);
		
		label.Render(G);
		g.drawImage(image, Position.x, Position.y, null);
	}
	
	
	public static Button Yes(int X, int Y, int Width, int Height, Color color) {
		BufferedImage i = new BufferedImage(Width, Height, IndexColorModel.TRANSLUCENT);
		Graphics g = i.getGraphics();
		g.setColor(color);
		g.fillRect(0, 0, Width, Height);
		
		Label label = new Label("Yes");
		int x = (Width - g.getFontMetrics().stringWidth("Yes")) / 2;
		int y = (g.getFontMetrics().getAscent() + (Height - (g.getFontMetrics().getDescent() + g.getFontMetrics().getAscent())) / 2);
		
		label.position = new Point(x, y);
		
		Rectangle pos = new Rectangle(X, Y, Width, Height);
		
		return new Button(i, label, pos, 'y');
	}
	
	public static Button No(int X, int Y, int Width, int Height, Color color) {
		BufferedImage i = new BufferedImage(Width, Height, IndexColorModel.TRANSLUCENT);
		Graphics g = i.getGraphics();
		g.setColor(color);
		g.fillRect(0, 0, Width, Height);
		
		Label label = new Label("No");
		int x = (Width - g.getFontMetrics().stringWidth("No")) / 2;
		int y = (g.getFontMetrics().getAscent() + (Height - (g.getFontMetrics().getDescent() + g.getFontMetrics().getAscent())) / 2);
		
		label.position = new Point(x, y);
		
		Rectangle pos = new Rectangle(X, Y, Width, Height);
		
		return new Button(i, label, pos, 'y');
	}
	
	public static Button Ok(int X, int Y, int Width, int Height, Color color) {
		BufferedImage i = new BufferedImage(Width, Height, IndexColorModel.TRANSLUCENT);
		Graphics g = i.getGraphics();
		g.setColor(color);
		g.fillRect(0, 0, Width, Height);
		
		Label label = new Label("Ok");
		int x = (Width - g.getFontMetrics().stringWidth("Ok")) / 2;
		int y = (g.getFontMetrics().getAscent() + (Height - (g.getFontMetrics().getDescent() + g.getFontMetrics().getAscent())) / 2);
		
		label.position = new Point(x, y);
		
		Rectangle pos = new Rectangle(X, Y, Width, Height);
		
		return new Button(i, label, pos, 'y');
	}
}
