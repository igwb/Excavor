package me.igwb.Excavor.Entities.Lighting;

import java.awt.*;
import java.awt.image.BufferedImage;

public enum LightSourceType {
	Sphere, Cone, Sun;
	
	public Image getLight(int strength, Color color) {
		
		if(this == Sun)
			return null;
		
		BufferedImage light = new BufferedImage(strength * 10, strength * 10, 1);
		
		Graphics2D lg = light.createGraphics();
		lg.setBackground(BACKGROUNDCOLOR);
		
		float radius = (float)strength * 5;
		float[] values = new float[] { Math.min(0.5f, (float)strength * 0.025f), Math.min(1f, (float)strength * 0.1f) };		
		Color[] colors = new Color[] { color, BACKGROUNDCOLOR };		
		
		RadialGradientPaint rgp = new RadialGradientPaint(strength * 5, strength * 5, radius, values, colors);
		
		switch(this) {
		
		case Cone:
			break;
		case Sphere:
			lg.setPaint(rgp);			
			lg.fillRect(0, 0, strength * 10, strength * 10);
			lg.dispose();
			
			int width = (int) ((float)strength * 12.5f),
				height = (int) ((float)strength * 7.5f);
			
			return light.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			
		default:
			break;
		
		}
		
		return null;
	}
	
	public static Color BACKGROUNDCOLOR = new Color(0x001337);
}
