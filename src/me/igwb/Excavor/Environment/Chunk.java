package me.igwb.Excavor.Environment;

import java.awt.Point;

public class Chunk {

	private Field[] Fields = new Field[256];
	
	private Point Position;

	public Chunk(Point position, Field[] fields) {
		
		Fields = fields;
		Position = position;
		
	}
	
	public Point getPosition() {
		return Position;
	}

	private void setPosition(Point position) {
		Position = position;
	}
	
}
