package resources;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class ResourceLoader {

	public static Image getImage(String path) throws IOException {
		Image img;
		
		img = ImageIO.read(ResourceLoader.class.getResource(path));
		
		return img;
	}
	
	public static URL getImageURL(String path) throws IOException {		
		
		return ResourceLoader.class.getResource(path);
		
	}
	
}
