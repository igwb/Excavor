package me.igwb.Excavor.Lighting;

import java.awt.Color;

public class RGBColor {
	
	private int R, G, B;
	
	public RGBColor(int r, int g, int b) {		
		R = r;
		G = g;
		B = b;		
	}
	
	public Color getColor() {
		return new Color(R, G, B);
	}
	
	public int getR() {
		return R;
	}

	public void setR(int r) {
		R = r;
	}

	public int getG() {
		return G;
	}

	public void setG(int g) {
		G = g;
	}

	public int getB() {
		return B;
	}

	public void setB(int b) {
		B = b;
	}

	public static RGBColor getRGBColor(Color c) {		
		return getRGBColor(c.getRGB());			
	}
	
	public static RGBColor getRGBColor(int argb) {
	
		int rgb[] = new int[] {
			    (argb >> 16) & 0xff, //red
			    (argb >>  8) & 0xff, //green
			    (argb      ) & 0xff  //blue
			};
		
		return new RGBColor(rgb[0], rgb[1], rgb[2]);
		
	}
	
	public RGBColor Multiply(RGBColor color) {
		R = (R * color.R) / 255;
		G = (G * color.G) / 255;
		B = (B * color.B) / 255;
		
		if(R > 255)
			R = 255;
		
		if(G > 255)
			G = 255;
		
		if(B > 255)
			B = 255;
		
		return this;
	}
}
