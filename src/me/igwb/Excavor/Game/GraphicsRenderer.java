package me.igwb.Excavor.Game;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import me.igwb.Excavor.Environment.Field;
import me.igwb.Excavor.Environment.ImageSplitter;

public class GraphicsRenderer {

	public ArrayList<Field> fields = new ArrayList<Field>();
	
	private Image[] Textures;
	
	public GraphicsRenderer(URL TexturePath) {
		try {
			Textures = ImageSplitter.split(TexturePath, 0, 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void RenderAll(Graphics g) {
		
	}
	
	public void RenderFields(Graphics g) {
		for(Field field : fields) {
			Image texture = Textures[field.Type.Image];			
			g.drawImage(texture, field.Location.x, field.Location.y, null);
		}
	}
}
