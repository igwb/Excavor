package me.igwb.Excavor.Game;

import java.awt.Canvas;

public class HUDCanvas extends Canvas{
	
	private static final long serialVersionUID = 1L;

	public HUDCanvas() {
		super();

	    setIgnoreRepaint(true);
		setVisible(true);
		setFocusable(false);
	}
}
