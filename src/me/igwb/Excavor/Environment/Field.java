package me.igwb.Excavor.Environment;



public class Field {
	
	private Position location;
	
	private FieldType[] Types;
	private Field[] zFields;
	private int[] events;
	
	
	public static final int SIZE = 50;
	
	public Field(Position loc) {
		
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
	 * Returns the position of a field.
	 * 
	 * @return Point - the position
	 */
	public Position getLocation() {
		return location;
	}

	public void setLocation(Position loc) {
		
		location = loc;
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
}