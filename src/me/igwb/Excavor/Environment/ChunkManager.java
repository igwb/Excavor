package me.igwb.Excavor.Environment;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import resources.ResourceLoader;
import sun.awt.image.PixelConverter.Argb;

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
		
		Programm.getCore().log.info("X: " + x + " Y: " + y);
		Programm.getCore().log.info("C: " + c);
		
		
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
		
		return getFieldAt(fieldPos);
	}
	
	public Field getFieldAt(Point position) {
		int X, Y;
		
		X = (int)(position.x / Chunk.SIZE);
		Y = (int)(position.y / Chunk.SIZE);
				
		if(position.x < 0) {
			X -= 1;
		}
		  
		
		if(position.y < 0) {
			Y -= 1;
		}
		 

		//Programm.getCore().log.log(Level.INFO, position.toString() + "X: " + X + " Y: " + Y);
		
		Chunk fieldChunk = getChunkAt(new Point(X, Y));

		if(fieldChunk == null) {
			Field errorField  = new Field(new Position(position.x, position.y, 0));
			FieldType[] type = new FieldType[1];
			type[0] = FieldType.getType("ERROR");

			errorField.setTypes(type);
			return errorField;

		} else {

			return fieldChunk.getFieldAt(position);
		}
	}
	
}
