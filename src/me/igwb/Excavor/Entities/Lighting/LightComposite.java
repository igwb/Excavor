package me.igwb.Excavor.Entities.Lighting;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class LightComposite implements Composite {

	public static final LightComposite applyLight = new LightComposite();
	public static final AlphaComposite normal = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
	
	@Override
	public CompositeContext createContext(ColorModel srcColorModel, ColorModel dstColorModel, RenderingHints hints) {
		return new MultiplicationContext();
	}
	
	private class MultiplicationContext implements CompositeContext {

		@Override
		public void compose(Raster dst, Raster src, WritableRaster out) {

			if(dst.getSampleModel().getDataType() != DataBuffer.TYPE_INT ||
					dst.getSampleModel().getDataType() != DataBuffer.TYPE_INT ||
					out.getSampleModel().getDataType() != DataBuffer.TYPE_INT)
			{
				out.setRect(dst);
				return;
			}
			
			int width = Math.min(src.getWidth(), dst.getWidth() );
			int height = Math.min(src.getHeight(), dst.getHeight() );
			
			int[] inputDst = new int[width * height];
			int[] inputSrc = new int[width * height];
			int[] output = new int[width * height];
			
			dst.getDataElements(0, 0, width, height, inputDst );
			src.getDataElements(0, 0, width, height, inputSrc );
			
			int[] cache = new int[] { 0xffffff, 0xffffff, 0xffffff };
			RGBColor srcColor, dstColor;

			for(int index = 0; 
					index < inputDst.length && 
					index < inputSrc.length;
					index++ )
			{
				if(cache[0] == inputDst[index] && cache[1] == inputSrc[index] ) {
					output[index] = cache[2];
					continue;
				}
				
				cache[0] = inputDst[index];
				cache[1] = inputSrc[index];
				
				srcColor = RGBColor.getRGBColor(cache[1] );
				dstColor = RGBColor.getRGBColor(cache[0] );
				
				/**WARNING!**/ 
				/*if statements without brackets*/
				
				if(srcColor.equals(RGBColor.WHITE ))
					output[index] = dstColor.getRGB();
								
				else if(dstColor.equals(RGBColor.WHITE))
					output[index] = srcColor.getRGB();
				
				else if(srcColor.equals(RGBColor.BLACK) || dstColor.equals(RGBColor.BLACK))
					output[index] = 0x000000;
				
				else if(srcColor.equals(dstColor))
					output[index] = srcColor.getRGB();
				
				else
					output[index] = srcColor.multiply(dstColor).getRGB();
				
				cache[2] = output[index];
			}
			
			out.setDataElements(0, 0, width, height, output);
		}

		@Override
		public void dispose() {		
		}		
	}

}
