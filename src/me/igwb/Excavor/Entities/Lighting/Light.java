package me.igwb.Excavor.Lighting;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import me.igwb.Excavor.Environment.Field;

public class Light {

	private BufferedImage overlay = new BufferedImage(Field.SIZE.width, Field.SIZE.height, 3);
	
	private int[] LightValue = new int[] { 0, 0, 0 };
	private java.awt.Color[] Color = new java.awt.Color[] { java.awt.Color.WHITE, java.awt.Color.WHITE, java.awt.Color.WHITE };
	
	public boolean valueChanged = true;
	
	public int[] getLightValue() {
		return LightValue;
	}
	
	public int[] renderLightValue() {
		valueChanged = false;
		return LightValue;
	}
	
	public void setLightValue(int[] lightValue) {
		if(LightValue == lightValue || (LightValue[0] > 10 | LightValue[1] > 10 | LightValue[2] > 10))
			return;
		
		LightValue = lightValue;
		valueChanged = true;
	}

	public java.awt.Color[] getColor() {
		return Color;
	}

	public void setColor(java.awt.Color[] color) {
		if(Color == color)
			return;
		
		Color = color;
		valueChanged = true;
	}
	
	public BufferedImage getOverlay() {
		
		if(!valueChanged)
			return overlay;
		
		Graphics2D g2D = overlay.createGraphics();
		
		g2D.setBackground(BACKGROUNDCOLOR);
		
		AlphaComposite aLeft, aTop, aRight;		
		aLeft = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) ((float)LightValue[0] * 0.1));
		aTop = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) ((float)LightValue[1] * 0.1));
		aRight = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) ((float)LightValue[2] * 0.1));
		
		g2D.setComposite(aLeft);
		g2D.setColor(Color[0]);
		g2D.drawPolygon(Field.LEFT);
		
		g2D.setComposite(aTop);
		g2D.setColor(Color[1]);
		g2D.drawPolygon(Field.TOP);
		
		g2D.setComposite(aRight);
		g2D.setColor(Color[2]);
		g2D.drawPolygon(Field.RIGHT);

		g2D.dispose();
		
		valueChanged = false;
		return overlay;
	}
	
	public static java.awt.Color BACKGROUNDCOLOR = new java.awt.Color(0x23417f);
}
