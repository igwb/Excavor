package me.igwb.Excavor.Environment;

import java.awt.Point;

public class Field {
	
	private Point Position;
	
	private FieldType[] Types;
	
	public static final int SIZE = 50;
	
	public Field() {
		
	}

	public FieldType[] getTypes() {
		return Types;
	}

	public void setTypes(FieldType[] types) {
		Types = types;
	}

	public Point getPosition() {
		return Position;
	}

	public void setPosition(Point position) {
		Position = position;
	}
}