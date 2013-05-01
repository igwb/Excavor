package me.igwb.Excavor.UI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class ConversationManager {

	private static Conversation C;
	
	public static void show(Image background, Rectangle position, ButtonLayout layout, Graphics g) {
		
		if(C == null || C.Background != background || C.Position != position || C.Layout != layout)
			C = new Conversation(background, position, layout);
		
		C.Render(g);
	}
	
	public static void Render(Graphics g) {
		
		if(C != null)
			C.Render(g);
		
	}
	
	public static void destroy() {
		
		if(C != null)
			C = null;
		
	}
	
	public static boolean allowUpdate() {
		return C == null;
	}
	
	public static void keyPressed(KeyEvent arg0) {
		if(C == null)
			return;
		
		if(arg0.getKeyCode() == KeyEvent.VK_S)
			C.Layout.moveIndexUp();
		
		else if(arg0.getKeyCode() == KeyEvent.VK_W)
			C.Layout.moveIndexDown();
		
		else if(arg0.getKeyCode() == KeyEvent.VK_SPACE)
			C.Layout.setButtonPressed(true);
	}
	
}
