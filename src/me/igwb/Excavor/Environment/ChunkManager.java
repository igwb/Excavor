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
	
		for(Chunk chunk : chunks) {
			if(chunk.getPosition().equals(position))
				chunks.remove(chunk);

		}
		
	}
	
	public Chunk getChunkAt(Point position) {
		Point chunkPos = new Point();
		
		chunkPos.x = (int)Math.floor(Math.floor(position.x / Field.SIZE) / Chunk.SIZE);
		chunkPos.y = (int)Math.floor(Math.floor(position.y / Field.SIZE) / Chunk.SIZE);
		
		for(Chunk chunk : chunks) {
			if(chunk.getPosition().equals(chunkPos))
				return chunk;
		}
		
		return null;
	}
	
	public Field getFieldAt(Point position) {
		
		int X = (int)Math.floor(position.x / Field.SIZE) * Field.SIZE;
		int Y = (int)Math.floor(position.y / Field.SIZE) * Field.SIZE;

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
