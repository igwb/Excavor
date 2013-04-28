package me.igwb.Excavor.Game;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import me.igwb.Excavor.Environment.Field;

public class RenderLogic {

	private int Size;
	
	public RenderLogic(Dimension FieldSize){
			Size = FieldSize.height;
	}
	
	public ArrayList<Field> getRenderFields(Point Center, int ViewDistance) {
		
		ArrayList<Field> List = new ArrayList<Field>();
		Point CenterField, max, min;
		
		
		CenterField = new Point((int)(Size * Math.floor(Center.x / Size)), (int)(Size * Math.floor(Center.x / Size)));
		
		max = new Point(CenterField.x + ViewDistance, CenterField.y + ViewDistance);
		min = new Point(CenterField.x - ViewDistance, CenterField.y - ViewDistance);
		
		//Add CenterField
		List.add(Programm.getCore().getChunkManager().getFieldAt((CenterField)));
		
		for (int i = 0; i < List.size(); i++) {
			List.addAll(getAdjicentRender(List.get(i), max, min));
		}
		
		
		return List;
	}
	
	private ArrayList<Field> getAdjicentRender(Field RootField, Point max, Point min) {
		
		ArrayList<Field> List = new ArrayList<Field>();
		Point curPoint;
		Field curField;
		
		//return if field is not transparent
		if(!RootField.getSeeThru()) {
			return List;
		}
		
		
		curPoint = new Point(RootField.getPosition().x, RootField.getPosition().y + 1);
		curField = Programm.getCore().getChunkManager().getFieldAt(curPoint);
		if(curField.getSeeThru() && curPoint.x <= max.x && curPoint.y <= max.y && curPoint.x >= min.x && curPoint.y >= min.y) {
			List.add(curField);
		}
		
		curPoint = new Point(RootField.getPosition().x + 1, RootField.getPosition().y + 1);
		curField = Programm.getCore().getChunkManager().getFieldAt(curPoint);
		if(curField.getSeeThru() && curPoint.x <= max.x && curPoint.y <= max.y && curPoint.x >= min.x && curPoint.y >= min.y) {
			List.add(curField);
		}
		
		curPoint = new Point(RootField.getPosition().x + 1, RootField.getPosition().y);
		curField = Programm.getCore().getChunkManager().getFieldAt(curPoint);
		if(curField.getSeeThru() && curPoint.x <= max.x && curPoint.y <= max.y && curPoint.x >= min.x && curPoint.y >= min.y) {
			List.add(curField);
		}
		
		curPoint = new Point(RootField.getPosition().x + 1 , RootField.getPosition().y - 1);
		curField = Programm.getCore().getChunkManager().getFieldAt(curPoint);
		if(curField.getSeeThru() && curPoint.x <= max.x && curPoint.y <= max.y && curPoint.x >= min.x && curPoint.y >= min.y) {
			List.add(curField);
		}
		
		curPoint = new Point(RootField.getPosition().x, RootField.getPosition().y - 1);
		curField = Programm.getCore().getChunkManager().getFieldAt(curPoint);
		if(curField.getSeeThru() && curPoint.x <= max.x && curPoint.y <= max.y && curPoint.x >= min.x && curPoint.y >= min.y) {
			List.add(curField);
		}
		
		curPoint = new Point(RootField.getPosition().x - 1, RootField.getPosition().y - 1);
		curField = Programm.getCore().getChunkManager().getFieldAt(curPoint);
		if(curField.getSeeThru() && curPoint.x <= max.x && curPoint.y <= max.y && curPoint.x >= min.x && curPoint.y >= min.y) {
			List.add(curField);
		}
		
		curPoint = new Point(RootField.getPosition().x - 1, RootField.getPosition().y);
		curField = Programm.getCore().getChunkManager().getFieldAt(curPoint);
		if(curField.getSeeThru() && curPoint.x <= max.x && curPoint.y <= max.y && curPoint.x >= min.x && curPoint.y >= min.y) {
			List.add(curField);
		}
		
		curPoint = new Point(RootField.getPosition().x - 1, RootField.getPosition().y + 1);
		curField = Programm.getCore().getChunkManager().getFieldAt(curPoint);
		if(curField.getSeeThru() && curPoint.x <= max.x && curPoint.y <= max.y && curPoint.x >= min.x && curPoint.y >= min.y) {
			List.add(curField);
		}
		
		return List;
	}
}
