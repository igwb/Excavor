package resources;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.sun.org.apache.xerces.internal.util.URI;

public class ResourceLoader {

	public static Image getImage(String path) throws IOException {
		Image img;
		
		img = ImageIO.read(ResourceLoader.class.getResource(path));
		
		return img;
	}
	
	public static URL getURL(String path) throws IOException {		
		
		return ResourceLoader.class.getResource(path);
		
	}
	
	public static URL getURL(String path, boolean searchExt) throws IOException {
		
		if(searchExt) {
			
			URL url = null;
			try {
				 url = new URL("file:/" + System.getProperty("user.dir") + "/" + path);
				 //System.out.println(url.getFile() + "\n" + url.getPath());
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
			if(new File(url.getFile()).exists())
				return url;
			else
				return getURL(path);
			
		} else
			return getURL(path);
		
	}
	
}
