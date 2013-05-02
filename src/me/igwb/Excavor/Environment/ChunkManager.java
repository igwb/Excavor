package me.igwb.Excavor.Environment;

import java.awt.Point;
import java.util.ArrayList;

public class ChunkManager {

	private ArrayList<Chunk> chunks = new ArrayList<Chunk>();
	
	public ChunkManager() {
		
	}
	
	public void loadChunk(String chunkPath) {
		Chunk chunk = Chunk.load(chunkPath);
		chunks.add(chunk);
	}
	
	public void unloadChunk(Point position) {
		
	}
	
	public Chunk getChunkAt(Point position) {
		for(Chunk chunk : chunks) {
			if(chunk.getPosition().equals(position))
				return chunk;
		}
		
		return null;
	}
	
	public Field getFieldAt(Point position) {
		
		int X = (int)Math.floor(position.x / 20);
		int Y = (int)Math.floor(position.y / 20);

		Chunk fieldChunk = getChunkAt(new Point(X, Y));

		if(fieldChunk == null) {
			Field errorField  = new Field(position);
			FieldType[] type = new FieldType[1];
			type[0] = FieldType.getType("ERROR");

			errorField.setTypes(type);
			return errorField;

		} else {

			return fieldChunk.getFieldAt(position);
		}
	}
	
}
