package me.igwb.Excavor.Game;

import java.awt.*;
import java.awt.image.ImageObserver;

public class Drawable implements ImageObserver {

	protected Point Position;
	
	protected Image Texture;
	
	public void Draw(Graphics g) {
		g.drawImage(Texture, Position.x, Position.y, this);
	}
	
	public Image getImage() {
		return Texture;
	}
	
	public Point getLocation() {
		return Position;
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {		
		return true;
	}
}
