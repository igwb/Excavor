package me.igwb.Excavor.Game;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class GameFocusListener implements FocusListener{

	@Override
	public void focusGained(FocusEvent arg0) {
		
		Programm.getCore().getStatusManager().setToBackground(false);
	}
	
	@Override
	public void focusLost(FocusEvent arg0) {
		
		Programm.getCore().getStatusManager().setToBackground(true);
	}

}
