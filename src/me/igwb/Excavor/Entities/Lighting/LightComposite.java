package me.igwb.Excavor.Entities.Lighting;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class LightComposite implements Composite{

	public static LightComposite newInstance = new LightComposite();
	public static AlphaComposite normal = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);

	private LightComposite() {
		
	}
	
	@Override
	public CompositeContext createContext(ColorModel arg0, ColorModel arg1,	RenderingHints arg2) {	
		return new LightContext();
	}
	
	private static final class LightContext implements  CompositeContext {

		@Override
		public void compose(Raster SRC, Raster DSToriginal, WritableRaster DSTnew) {

			int width = Math.min(SRC.getWidth(), DSToriginal.getWidth());
			int height = Math.min(SRC.getHeight(), DSToriginal.getHeight());
			
			int[] SRCcolumn = new int[height];			
			int[] DSTcolumn = new int[height];			
			int[] OUTcolumn = SRCcolumn;
			
			for(int x = 0; x < width; x++) {
				
				SRC.getDataElements(x, 0, 1, height, SRCcolumn);
				DSToriginal.getDataElements(x, 0, 0, height, DSTcolumn);
				
				for(int y = 0; y < height; y++) {					
					
					OUTcolumn[y] = multiply(SRCcolumn[y], DSTcolumn[y]);
					
				}
				
				DSTnew.setDataElements(x, 0, 1, height, OUTcolumn);
			}
		}
		
		private int multiply(int SRC, int DST) {
			
			return new Color(
				    ((SRC >> 16) & 0xff) * ((DST >> 16) & 0xff) / 255, //red 
				    ((SRC >> 8) & 0xff) * ((DST >> 8) & 0xff) / 255, //green
				    ((SRC >> 0) & 0xff) * ((DST >> 0) & 0xff) / 255, //blue
				    Math.min(255, ((SRC >> 24) & 0xff) + ((DST >> 24) & 0xff)) // alpha
				).getRGB();
		}

		@Override
		public void dispose() {
			
		}
		
		/*
		public int[] Multiply(int[] src, int[] dst) {
            return new int[] {
                (src[0] * dst[0]) >> 8,
                (src[1] * dst[1]) >> 8,
                (src[2] * dst[2]) >> 8,
                Math.min(255, src[3] + dst[3])
            };
        }
        */
		
	}
	
}
