package me.igwb.Excavor.Environment;

import java.awt.Point;
import java.util.ArrayList;


public class Field {
	
	private Point Position;
	
	private FieldType[] Types;
	
	public static final int SIZE = 50;
	
	public Field(Point position) {
		
		setPosition(position);
	}
	
	public boolean getSeeThru() {
		
		for(FieldType types : Types) {
			if(!types.Transparent)
				return false;
		}
		return true;
	}

	public FieldType[] getTypes() {
		
		return Types;
	}

	public void setTypes(FieldType[] types) {
		
		Types = types;
	}

	/**
	 * Returns the absolute position of a field.
	 * 
	 * @return Point - the position
	 */
	public Point getPosition() {
		return Position;
	}

	public void setPosition(Point position) {
		
		Position = new Point((int)Math.floor(position.x / Field.SIZE) * Field.SIZE, (int)Math.floor(position.y / Field.SIZE) * Field.SIZE);
	}
	
	
	public boolean equals(Object obj) {
		
		if(obj instanceof Field) {
			if(Position.equals(((Field) obj).Position)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public String[] getFieldData() {
		
		ArrayList<String> lines = new ArrayList<String>();
		
		String types = "";
		
		for(FieldType type : Types) {
			types += type.Image + ",";
		}
		
		types = types.substring(0, types.length() - 1);
		
		lines.add("#FieldData: " + new java.util.Date().toString());
		lines.add("FieldTypes=" + types);
		lines.add("position=" + Position.x + "," + Position.y);
		
		return lines.toArray(new String[lines.size()]);
		
	}
}