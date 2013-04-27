package me.igwb.Excavor.Environment;

import java.awt.Point;
import java.net.URL;
import java.util.ArrayList;

public class ChunkManager {

	private ArrayList<Chunk> chunks = new ArrayList<Chunk>();
	
	public ChunkManager() {
		
	}
	
	public void loadChunk(URL chunkPath) {
		
	}
	
	public void unloadChunk(Point position) {
		
	}
	
	public Chunk getChunkAt(Point position) {
		for(Chunk chunk : chunks) {
			if(chunk.getPosition() == position)
				return chunk;
		}
		
		return null;
	}
	
	public Field getFieldAt(Point position) {
		int X = position.x / 20;
		int Y = position.y / 20;
		
		return getChunkAt(new Point(X, Y)).getFieldAt(position);
	}
	
}
