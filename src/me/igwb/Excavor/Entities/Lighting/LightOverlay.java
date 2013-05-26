package me.igwb.Excavor.Entities.Lighting;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class LightOverlay {

	private static MultiplicationCache cache = new MultiplicationCache();
	
	public static BufferedImage multiply(BufferedImage DST, BufferedImage SRC) {
		
		if(DST.getWidth() != SRC.getWidth() || DST.getHeight() != SRC.getHeight() )
			return DST;
		
		/*int[] dst = DST.getRGB(0, 0, DST.getWidth(), DST.getHeight(), new int[0], 0, 0);
		int[] src = SRC.getRGB(0, 0, SRC.getWidth(), SRC.getHeight(), new int[0], 0, 0);
		
		for(int i = 0; i < dst.length; i++ ) {
			dst[0] = RGBColor.getRGBColor(src[i]).multiply(RGBColor.getRGBColor(dst[i])).getColorCode();
		}
		
		DST.setRGB(0, 0, DST.getWidth(), DST.getHeight(), dst, 0, 0);
		*/
		for(int X = 0; X < DST.getWidth(); X++ )
			for(int Y = 0; Y < DST.getHeight(); Y++ ) {
				
				RGBColor DSTcolor = getPixelData(DST, X, Y );
				RGBColor SRCcolor = getPixelData(SRC, X, Y );
				
				DST.setRGB(X, Y, cache.multiply(DSTcolor, SRCcolor ).getColorCode() );								
			}
		
		return DST;
	}
	
	public static void overlay(BufferedImage DST, BufferedImage SRC) {
		
		
		
	}
	
	public static RGBColor getPixelData(BufferedImage img, int x, int y) {
		int argb = img.getRGB(x, y);
		return RGBColor.getRGBColor(argb);		
	}	
}
