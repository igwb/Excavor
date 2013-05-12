package me.igwb.Excavor.Environment;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;



public class Field {
	
	private Position location;
	
	private FieldType[] Types;
	private Field[] zFields;
	private int[] events;
	
	
	public static final Dimension SIZE = new Dimension(104,97);
	public static final int HEIGHT_OFFSET = 70;
	public static final int SIDE_HEIGHT = 44;
	
	public Field(Position loc) throws IllegalArgumentException {
		
		setLocation(loc);
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

	
	/**
	 * Returns fields on the z-axis at this fields position, not including the lowest one.
	 * Returns null if the field is not the lowest field.
	 * @return
	 */
	public Field[] getZFields() {
		if(this.location.getZ() == 0) {
			return zFields;
		} else {
			return null;
		}
	}
	
	public void setZFields(Field[] zFields) {
		this.zFields = zFields;
	}
	
	public void setTypes(FieldType[] types) {
		
		Types = types;
	}

	public void setEvents(int[] events) {
		
		this.events = events;
	}
	
	/**
	 * Returns the corner position of a field.
	 * 
	 * @return Point - the position
	 */
	public Position getLocation() {
		return location;
	}

	public void setLocation(Position loc) throws IllegalArgumentException {
		
		location = loc;
	}
	
	
	public Point getRenderLocation() {
		Point renderLocation;
		
		Point renderPos = new Point(location.getX() * SIZE.width, location.getY() * SIZE.height - (int)(HEIGHT_OFFSET * location.getY()));
		
		
		//Side offset
		if(location.getY() % 2 != 0) {
			renderPos.x = renderPos.x + (int)(Field.SIZE.width / 2);
		}
					
		if(location.getZ() > 0) {
			renderPos.y = renderPos.y - location.getZ() * SIDE_HEIGHT;
		}
		
		return renderPos;
	}
	
	public boolean equals(Object obj) {
		
		if(obj instanceof Field) {
			if(location.equals(((Field) obj).getLocation())) {
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