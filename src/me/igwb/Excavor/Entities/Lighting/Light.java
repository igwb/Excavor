package me.igwb.Excavor.Entities.Lighting;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import me.igwb.Excavor.Environment.Field;

@Deprecated
public class Light {
	
	private BufferedImage overlay = new BufferedImage(Field.SIZE.width, Field.SIZE.height, 3);
	
	private float LightValue = 0;
	private java.awt.Color Color = java.awt.Color.WHITE;
	
	public boolean valueChanged = true, denyRender = false;
	
	public float getLightValue() {
		return LightValue;
	}
	
	public float renderLightValue() {
		valueChanged = false;
		return LightValue;
	}
	
	public void setLightValue(int lightValue) {
		if(LightValue == lightValue || LightValue > 10)
			return;
		
		LightValue = lightValue;
		valueChanged = true;
	}

	public java.awt.Color getColor() {
		return Color;
	}

	public void setColor(java.awt.Color color) {
		if(Color == color)
			return;
		
		Color = color;
		valueChanged = true;
	}
	
	public BufferedImage getOverlay() {
		
		if(denyRender)
			return NULL;
		
		if(!valueChanged)
			return overlay;
		
		overlay = new BufferedImage(Field.SIZE.width, Field.SIZE.height, 3);
		Graphics2D g2D = overlay.createGraphics();
		
		
		//g2D.setColor(BACKGROUNDCOLOR);
		g2D.fillPolygon(Field.LEFT);
		g2D.fillPolygon(Field.TOP);
		g2D.fillPolygon(Field.RIGHT);
		
		
		AlphaComposite ac;		
		ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (LightValue * 0.1f));		
		//g2D.fillPolygon();

		g2D.dispose();
		
		valueChanged = false;
		return overlay;
	}
	
	private static final BufferedImage NULL = new BufferedImage(Field.SIZE.width, Field.SIZE.height, 3);
}
