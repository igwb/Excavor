package me.igwb.Excavor.Environment;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class ImageSplitter {
	
	public static Image[] split(URL image, int rows, int columns) throws IOException {
		BufferedImage buffImage = ImageIO.read(image);	

		int chunkWidth = buffImage.getWidth() / columns;
	    int chunkHeight = buffImage.getHeight() / rows;
		
		Image imgs[] = new Image[rows * columns];
		int count = 0;
		
		for (int x = 0; x < rows; x++) {  
            for (int y = 0; y < columns; y++) {
                imgs[count] = new BufferedImage(chunkWidth, chunkHeight, buffImage.getType());   
                
                Graphics gr = imgs[count++].getGraphics();  
                gr.drawImage(buffImage, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);  
                gr.dispose();
            }  
        }
		
		return imgs;
	}

}
