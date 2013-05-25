package me.igwb.Excavor.Entities.Lighting;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class LightOverlay {

	public static BufferedImage multiply(BufferedImage DST, BufferedImage SRC) {
		
		if(DST.getWidth() != SRC.getWidth() || DST.getHeight() != SRC.getHeight())
			return DST;
		
		HashMap<RGBColor, Point> colors = new HashMap<RGBColor, Point>();
		
		for(int X = 0; X < DST.getWidth(); X++)
			for(int Y = 0; Y < DST.getHeight(); Y++) {
				
				colors.put(
						getPixelData(DST, X, Y).
							multiply
								(getPixelData(SRC, X, Y)), 
							new Point(X, Y));
			}
				
		for(RGBColor color : colors.keySet())
			DST.setRGB(colors.get(color).x, colors.get(color).y, color.getColor().getRGB());
		
		return DST;
	}
	
	public static void overlay(BufferedImage DST, BufferedImage SRC) {
		
		
		
	}
	
	public static RGBColor getPixelData(BufferedImage img, int x, int y) {
		int argb = img.getRGB(x, y);
		return RGBColor.getRGBColor(argb);		
	}	
}
