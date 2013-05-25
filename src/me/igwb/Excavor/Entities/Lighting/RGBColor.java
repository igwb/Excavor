package me.igwb.Excavor.Entities.Lighting;

import java.awt.Color;

public class RGBColor {
	
	private int R, G, B, A;
	
	public RGBColor(int r, int g, int b) {		
		R = r;
		G = g;
		B = b;
		A = 255;
	}
	
	public RGBColor(int r, int g, int b, int a) {
		
		R = r;
		G = g;
		B = b;
		A = a;
		
	}
	
	public Color getColor() {
		return new Color(R, G, B, A);
	}
	
	public int[] getArray() {
		return new int[] { R, G, B, A };
	}
	
	public int getColorCode() {
		return getColor().getRGB();		
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

	public int getA() {
		return A;
	}

	public void setA(int a) {
		A = a;
	}

	public static RGBColor getRGBColor(Color c) {		
		return getRGBColor(c.getRGB());			
	}
	
	public static RGBColor getRGBColor(int argb) {
	
		int rgb[] = new int[] {
			    (argb >> 16) & 0xff, //red
			    (argb >>  8) & 0xff, //green
			     argb	 	 & 0xff, //blue
			    (argb >> 24) & 0xff // alpha
			};
		
		return new RGBColor(rgb[0], rgb[1], rgb[2], rgb[3]);
		
	}
	
	public static RGBColor getRGBColor(int[] argb) {
		
		if(argb.length != 4)
			throw new IllegalArgumentException("array must contain 4 values!");
		
		return new RGBColor(argb[0], argb[1], argb[2], argb[3]);
		
	}
	
	public RGBColor multiply(RGBColor color) {
		R = (R * color.R) / 255;
		G = (G * color.G) / 255;
		B = (B * color.B) / 255;
		A += color.A;
		
		if(R > 255)
			R = 255;
		
		if(G > 255)
			G = 255;
		
		if(B > 255)
			B = 255;
		
		if(A > 255)
			A = 255;
		
		return this;
	}
	
	public static int[] multiply(int[] SRC, int[] DST) {
        return new int[] {
            (SRC[0] * DST[0]) / 255,
            (SRC[1] * DST[1]) / 255,
            (SRC[2] * DST[2]) / 255,
            Math.min(255, SRC[3] + DST[3])
        };
    }
}
