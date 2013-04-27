package me.igwb.Excavor.Environment;

import java.awt.Point;
import java.io.*;

public class Chunk {

	private Field[] fields = new Field[400];
	
	private Point position;

	public Chunk(Point position, Field[] fields) {
		
		this.fields = fields;
		this.position = position;
		
	}
	
	public Field getFieldAt(Point position) {
		for(Field field : fields) {
			if(field.getPosition().equals(position))
				return field;
		}
		
		return null;
	}
	
	public Point getPosition() {
		return position;
	}

	public static Chunk load(String Path) {
		
		String input = "";
		
		int x = Integer.parseInt(Path.split(";")[1].split(",")[0]);
		int y = Integer.parseInt(Path.split(";")[1].split(",")[1]);
		
		Point position = new Point(x, y);
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(Path));
			
			while(reader.ready()) {
				input += reader.readLine();
			}
			
			reader.close();
			
		} catch (IOException e) {
			return null;
		}
		
		Field[] fields = new Field[400];
		
		for (int i = 0; i < fields.length; i++) {
			
			fields[i] = new Field(new Point());
			
			//get field position
			
			y = (i / 20) + (position.y * 20);
			x = (i - (y * 20)) + (position.x * 20);
			
			Point p = new Point(x, y);
			
			//get types from file
			
			String[] types = input.split(";")[i].split(",");
			int[] typeIDs = new int[types.length];
			
			for(int j = 0; j < types.length; j++)				
				typeIDs[j] = Integer.parseInt(types[j]);
			
			FieldType[] fieldTypes = new FieldType[typeIDs.length];
			
			for(int j = 0; j < typeIDs.length; j++)				
				fieldTypes[j] = FieldType.getType(typeIDs[j]);
			
			//set variables
			
			fields[i].setPosition(p);
			fields[i].setTypes(fieldTypes);
			
		}
		
		Chunk chunk = new Chunk(position, fields);
		
		return chunk;
	}
}