package resources;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;

public class ResourceLoader {

	public static Image getImage(String path) throws IOException {
		Image img = null;
		
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

	public static Clip getAudio(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        URL url = ResourceLoader.class.getResource(path);
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
			
		Clip clip = AudioSystem.getClip();
		clip.open(audioIn);
		
		return clip;
	}

	public static Clip getAudio(URL url) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
		
		Clip clip = AudioSystem.getClip();
		clip.open(audioIn);
		
		return clip;
	}
	
}
