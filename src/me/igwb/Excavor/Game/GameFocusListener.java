package me.igwb.Excavor.Game;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class GameFocusListener implements FocusListener{

	@Override
	public void focusGained(FocusEvent arg0) {
		if(!wasPaused)
			Programm.getCore().setPaused(false);
		
	}

	private boolean wasPaused = false;
	
	@Override
	public void focusLost(FocusEvent arg0) {
		if(Programm.getCore().getPaused())
			wasPaused = true;
		else
			wasPaused = false;
		
		Programm.getCore().setPaused(true);
		
	}

}
