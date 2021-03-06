package resources;

import java.awt.Image;
import java.io.IOException;

import me.igwb.Excavor.Environment.FieldType;
import me.igwb.Excavor.Environment.ImageSplitter;

public class EnvironmentLoader {

	private static Image[] IMG;
	
	
	public static Image getImage(FieldType type) {
		
		return IMG[type.ordinal()];
	}
	
	public static void initialize() throws IOException {
		
		IMG = ImageSplitter.split(ResourceLoader.class.getResource("Environment.png"), 10, 10);
	}

}
