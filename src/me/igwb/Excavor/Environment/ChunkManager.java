package me.igwb.Excavor.Environment;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import resources.ResourceLoader;
import me.igwb.Excavor.Game.Programm;

public class ChunkManager {

	private ArrayList<Chunk> chunks = new ArrayList<Chunk>();
	private BufferedImage fieldFinder;
	
	
	public ChunkManager() {
		try {
			fieldFinder = (BufferedImage) ResourceLoader.getImage("fieldFinder.png");
		
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void loadChunk(int chunkX, int chunkY) {
		Chunk chunk = Chunk.load(chunkX, chunkY);
		chunks.add(chunk);
	}
	
	public void unloadChunk(Point position) {
	
		for(Chunk chunk : chunks) {
			if(chunk.getPosition().equals(position))
				chunks.remove(chunk);

		}
		
	}
	
	public Chunk getChunkAt(Point position) {
		
		
		for(Chunk chunk : chunks) {
			if(chunk.getPosition().equals(position))
				return chunk;
		}
		
		return null;
	}
	
	public Field getFieldFromAbsolute(Point posPoint) {
		Point fieldPos;
		
		int x, y, c;
		
		x = (int)(posPoint.x / fieldFinder.getWidth());
		y = (int)(posPoint.y / fieldFinder.getHeight()) * 2;
		
		
		
		c = fieldFinder.getRGB(Math.abs(posPoint.x) - Math.abs(x) * fieldFinder.getWidth(), Math.abs(posPoint.y) - Math.abs(y / 2) * fieldFinder.getHeight());
		
		
		switch (c) {
		case -1: //Center (white)
			//nothing to do here
			break;
		case -65536: //Top left
			x -= 1;
			y -= 1;
			break;
		case -16711936: //Top right
			y -= 1;
			break;
		case -256: //Low right
			y += 1;
			break;
		case -16776961: //Low left
			x -= 1;
			y += 1;
			break;
		default:
			Programm.getCore().log.severe("Invalid color while trying to locate field: " + c);
			break;
		}
		
		fieldPos = new Point(x,y);
		
		return getFieldAt(new Position(fieldPos.x, fieldPos.y, 0));
	}

	/**
	 * Returns a field by it's position.
	 * @param pos
	 * @return
	 */
	public Field getFieldAt(Position pos) {
		int chunkX, chunkY;
		
		//Find the chunk
		chunkX = (int)(pos.getX() / Chunk.SIZE);
		chunkY = (int)(pos.getY() / Chunk.SIZE);
				
		if(pos.getX() < 0) {
			chunkX -= 1;
		}
		  
		
		if(pos.getY() < 0) {
			chunkY -= 1;
		}
		 
		
		Chunk fieldChunk = getChunkAt(new Point(chunkX, chunkY));

		if(fieldChunk == null) {
			
			return getAnErrorField(pos);
		} else {

			Field f = fieldChunk.getFieldAt(new Point(pos.getX(), pos.getY()));
			
			if(pos.getZ() == 0)
				return f;
			
			
			Field[] zFields = f.getZFields();		
			if((pos.getZ() > 0 & zFields == null) || pos.getZ() > zFields.length)
				return getAnErrorField(pos);
			
			return zFields[pos.getZ()];	
		}
	}
	
	private Field getAnErrorField(Position pos) {
		
		Field errorField  = new Field(pos);
		FieldType[] type = new FieldType[1];
		type[0] = FieldType.getType("ERROR");

		errorField.setTypes(type);
		
		return errorField;
	}
}
