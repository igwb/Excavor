package resources;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ResourceLoader {

	public static Image getImage(String path) throws IOException {
		Image img;
		
		img = ImageIO.read(ResourceLoader.class.getResource(path));
		
		return img;
	}
	
}
