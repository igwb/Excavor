package me.igwb.Excavor.Environment;

import java.awt.Point;
import java.io.*;
import java.util.logging.Level;

import me.igwb.Excavor.Game.Programm;

public class Chunk {

	private Field[] fields = new Field[400];
	
	public static int SIZE = 20;
	public static String MAP_FOLDER = System.getProperty("user.dir") + "/map/";
	
	private Point position;

	public Chunk(Point position, Field[] fields) {
		
		this.fields = fields;
		this.position = position;
		
	}
	
	public Field getFieldAt(Point position) {
		for(Field field : fields) {
			
			//Programm.getCore().log.log(Level.INFO,field.getLocation().getX() + "==" + position.x + "is: " + (field.getLocation().getX().equals(position.x)) + " | " + field.getLocation().getY() + "==" + position.y + "is: " +  (field.getLocation().getY().equals(position.y)));
			if(field.getLocation().getX().equals(position.x) && field.getLocation().getY().equals(position.y)) {
				return field;
			}
		}
		
		return null;
	}
	
	public Point getPosition() {
		return position;
	}

	public static Chunk load(int chunkX, int chunkY) {
		
		String input[], curLine[], curFields[], curData[], curSubData[];
		
		Field[] fields = new Field[(int)Math.pow(SIZE, 2)];
		Field[] zFields;
		Field usedField;
		
		
		FieldType types[];
		int events[], x, y;
		
		input = new String[SIZE];
		curLine = new String[SIZE];
		BufferedReader reader = null;

		//Reading the chunk file
		try {
			reader = new BufferedReader(new FileReader(MAP_FOLDER + "C" + chunkX + "_" + chunkY));

			for (int i = 0; i < SIZE; i++) {

				input[i] = reader.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;	
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {

			if(reader != null)
				try {reader.close(); } catch (IOException e) { e.printStackTrace(); }
		}
		
		
		//Loop for the read lines.
		for (int i = 0; i < input.length; i++) {
			
			curLine = input[i].split(";");
			
			//Loop for the fields in a line
			for (int j = 0; j < curLine.length; j++) {
				
				if(!curLine[j].equals(null) && !curLine[j].equals(";") && !curLine[j].equals("") && curLine[j].length() > 0) {
				
				curFields = curLine[j].split("/");
				} else {
					continue;
				}
		
				if(curFields.length > 1) {
					zFields = new Field[curFields.length - 1];
				} else {
					zFields = null;
				}
				
				//Loop for the layers
				for (int z = 0; z < curFields.length; z++) {
					curData = curFields[z].split("-");

					x = ((int)(j - 1 + (chunkX * SIZE) - Math.floor(((j - 1) / SIZE) * SIZE)));
					y = ((int)(i + (chunkY * SIZE)));

					
					if(z >= 1) {
						usedField = new Field(new Position(x, y, z));
					} else {
						usedField = new Field(new Position(x, y, z));
					}
					


					curSubData = curData[0].split(",");

					//Reading the events if present
					if(curData.length > 1 && z == 0) {


						events = new int[curSubData.length];


						for (int s = 0; s < curSubData.length; s++) {
							events[s] = Integer.parseInt(curSubData[s]);
						}

						usedField.setEvents(events);

						curSubData = curData[1].split(",");
					}

					types = new FieldType[curSubData.length];
					
					for (int s = 0; s < curSubData.length; s++) {
						types[s] = FieldType.getType(Integer.parseInt(curSubData[s]));
					}

					usedField.setTypes(types);
					
					
					if(z >= 1) {
						zFields[z - 1] = usedField;
					} else {
						fields[i * SIZE + j - 1] = usedField;
					}
				}
				fields[i * SIZE + j - 1].setZFields(zFields);
			}
		}
		
		Chunk chunk = new Chunk(new Point(chunkX, chunkY), fields);
		
		return chunk;
	}
}
