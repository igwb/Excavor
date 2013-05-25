package me.igwb.Excavor.Entities.Lighting;

import java.awt.*;

import me.igwb.Excavor.Game.Programm;
import me.igwb.Excavor.Player.Player;

public class LightRendering {
	
	private static Image img;

	public static void Render(Graphics g) {
		
		
		
		Player p = Programm.getCore().getActivePlayer();		
		g.drawImage(img, 0, 0, null);
		
	}
	
}
