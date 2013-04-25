package me.igwb.Excavor.Environment;

import java.awt.Point;

public class Chunk {

	private Field[] Fields = new Field[256];
	
	private Point Position;

	public Chunk(Point position, Field[] fields) {
		
		Fields = fields;
		Position = position;
		
	}
	
	public Field getFieldAt(Point position) {
		for(Field field : Fields) {
			if(field.Position.equals(position))
				return field;
		}
		
		return null;
	}
	
	public Point getPosition() {
		return Position;
	}

	private void setPosition(Point position) {
		Position = position;
	}
	
}
