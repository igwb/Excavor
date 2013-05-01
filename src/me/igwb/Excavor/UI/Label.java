package me.igwb.Excavor.UI;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import resources.ResourceLoader;

public class Label {
	
	public Point position = new Point();
	
	public float FontSize = 18f;
	
	public Font font;
	
	public Color color = Color.GREEN;
	
	public String Text;
	
	public Label(String text) {
		Text = text;
		position = new Point();
		FontSize = 18f;
		try {
			font = Font.createFont(Font.PLAIN, ResourceLoader.getURL("/resources/Sans Bold.ttf").openStream());
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		color = Color.GREEN;
	}
	
	public void Render(Graphics g) {
		font = font.
				deriveFont(FontSize);
		g.setFont(font);
		g.setColor(color);
		g.drawString(Text, position.x, position.y);
	}
	
	@Deprecated
	public void RenderImage(BufferedImage image) {
		
		font = font.
				deriveFont(FontSize);

		Graphics gIm = image.getGraphics();

		gIm.setFont(font);
		gIm.setColor(color);

		gIm.drawString(Text, position.x, position.y);
	}
}
