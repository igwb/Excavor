package me.igwb.Excavor.Lighting;

import java.util.ArrayList;

import me.igwb.Excavor.Environment.Position;

public class XShape {

	private ArrayList<Position> Points;
	private Position Center;
	
	public XShape(Position center, Position StartingPosition) {
		Points = new ArrayList<Position>();
		Center = center;
		
		Points.add(StartingPosition);
	}
	
	public ArrayList<Position> getPoints() {
		return Points;
	}
	
	public void addPoint(Position P) {
		Points.add(P);
	}

	public Position getMaxX() {
		Position curr = Points.get(0);
		
		for(Position p : Points)
			curr = p.getX() > curr.getX() ? p : curr;
			
		return curr;
	}
	
	public Position getMinX() {
		Position curr = Points.get(0);
		
		for(Position p : Points)
			curr = p.getX() < curr.getX() ? p : curr;
			
		return curr;
	}
	
	public Position getMaxY() {
		Position curr = Points.get(0);
		
		for(Position p : Points)
			curr = p.getY() > curr.getY() ? p : curr;
			
		return curr;
	}
	
	public Position getMinY() {
		Position curr = Points.get(0);
		
		for(Position p : Points)
			curr = p.getY() < curr.getY() ? p : curr;
			
		return curr;
	}
	
	public Position getMaxZ() {
		Position curr = Points.get(0);
		
		for(Position p : Points)
			curr = p.getZ() > curr.getZ() ? p : curr;
			
		return curr;
	}
	
	public Position getMinZ() {
		Position curr = Points.get(0);
		
		for(Position p : Points)
			curr = p.getZ() < curr.getZ() ? p : curr;
			
		return curr;
	}

	
	public Position getCenter() {
		return Center;
	}	
}
