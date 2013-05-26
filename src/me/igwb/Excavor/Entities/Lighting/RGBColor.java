package me.igwb.Excavor.Entities.Lighting;

import java.awt.Color;

public class RGBColor extends Color{

	public RGBColor(int r, int g, int b, int a) {
		super(r, g, b, a);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 7435477073249657011L;
	
	/*private int R, G, B, A;
	
	public RGBColor() {
		R = G = B = A = 255;
	}
	
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
		
	}*/
	
	public Color toColor() {
		return this;//return new Color(R, G, B, A);
	}
	
	public int[] getArray() {
		return new int[] { getRed(), getGreen(), getBlue(), getAlpha() };
	}
	
	public int getColorCode() {
		return getRGB();		
	}

	public static RGBColor toRGBColor(Color c) {		
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
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof RGBColor)
			obj = ((RGBColor) obj).toColor();
		
		return super.equals(obj);
	}
	
	public RGBColor multiply(RGBColor color) {
		int r = (getRed() * color.getRed()) / 255;
		int g = (getGreen() * color.getGreen()) / 255;
		int b = (getBlue() * color.getBlue()) / 255;
		int a = Math.min(255, getAlpha() + color.getAlpha());
		
		return new RGBColor(r, g, b, a);
	}
	
	public static int multiply(int SRC, int DST) {
		return getRGBColor(SRC).multiply(getRGBColor(DST)).getRGB();
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
