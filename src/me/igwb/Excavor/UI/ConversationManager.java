package me.igwb.Excavor.UI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InGameWindow implements KeyListener {

	private static Conversation C;
	
	public static void show(Image background, Rectangle position, ButtonLayout layout, Graphics g) {
		
		if(C == null || C.Background != background || C.Position != position || C.Layout != layout)
			C = new Conversation(background, position, layout);
		
		C.Render(g);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if(C == null)
			return;
		
		if(arg0.getKeyCode() == KeyEvent.VK_W)
			C.Layout.moveIndexUp();
		
		else if(arg0.getKeyCode() == KeyEvent.VK_S)
			C.Layout.moveIndexDown();
		
		else if(arg0.getKeyCode() == KeyEvent.VK_SPACE)
			C.Layout.setButtonPressed(true);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
