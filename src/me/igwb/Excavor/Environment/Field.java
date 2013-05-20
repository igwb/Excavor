package me.igwb.Excavor.Environment;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;

import me.igwb.Excavor.Lighting.Light;



public class Field {
	
	private Position location;
	
	private FieldType[] Types;
	private Field[] zFields;
	private int[] events;
	
	private boolean seeThru = true, walkable = true;
	
	private Light light;
	
	public static final Dimension SIZE = new Dimension(104,97);
	public static final int HEIGHT_OFFSET = 70;
	public static final int SIDE_HEIGHT = 44;
	
	public static final Polygon LEFT = new Polygon(new int[] { 0, 52, 52, 0 }, new int[] { 26, 52, 97, 72 }, 4);
	public static final Polygon TOP = new Polygon(new int[] { 52, 0, 52, 104 }, new int[] { 52, 26, 0, 26 }, 4);
	public static final Polygon RIGHT = new Polygon(new int[] { 104, 52, 52, 104 }, new int[] { 26, 52, 97, 72 }, 4);
	
	public Field(Position loc) throws IllegalArgumentException {
		
		setLocation(loc);
		light = new Light();
		
	}
	
	public boolean getSeeThru() {
		
		return seeThru;
	}

	public FieldType[] getTypes() {
		
		return Types;
	}

	
	public boolean getWalkable() {
		
		return walkable;
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
		
		if(this.zFields != null && walkable) {
			for(Field f : this.zFields) {
				if(!f.getWalkable())
					walkable = false;
			}
		}
	}
	
	public void setTypes(FieldType[] types) {
		
		Types = types;
		
		seeThru = true;
		for(FieldType t : Types) {
			if(!t.Transparent)
				seeThru = false;
		}
		
		walkable = true;
		for(FieldType t : Types) {
			if(!t.Passable)
				walkable = false;
		}
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
		lines.add("position=" + location.getX() + "," + location.getY() + "," + location.getZ());
		
		return lines.toArray(new String[lines.size()]);
		
	}

	public Light getLight() {
		return light;
	}

	public void setLight(Light light) {
		this.light = light;
	}
	
}