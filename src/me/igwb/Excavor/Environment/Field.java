package me.igwb.Excavor.Environment;



public class Field {
	
	private Position location;
	
	private FieldType[] Types;
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