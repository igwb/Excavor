package me.igwb.Excavor.Environment;

import java.awt.Point;

public class Chunk {

	private Field[] fields = new Field[400];
	
	private Point position;

	public Chunk(Point position, Field[] fields) {
		
		this.fields = fields;
		this.position = position;
		
	}
	
	public Field getFieldAt(Point position) {
		for(Field field : fields) {
			if(field.Position.equals(position))
				return field;
		}
		
		return null;
	}
	
	public Point getPosition() {
		return position;
	}

	private void setPosition(Point position) {
		this.position = position;
	}
	
}
