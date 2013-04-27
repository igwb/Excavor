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
	
	public ArrayList<Field> ArrayList(Point Center, int ViewDistance) {
		
		ArrayList<Field> List = new ArrayList<Field>();
		Point CenterField;
		Field cur;
		
		
		CenterField = new Point((int)(Size * Math.floor(Center.x / Size)), (int)(Size * Math.floor(Center.x / Size)));
		
		//Add CenterField
		List.add(Programm.getCore().getChunkManager().getFieldAt((CenterField)));
		
		//Upper Right Quadrant
		for (int i = 0; i < ViewDistance; i++) {
			cur = Programm.getCore().getChunkManager().getFieldAt(new Point(CenterField.x + Size * i, CenterField.y));
			
			if(cur.getSeeThru()) {
				List.add(cur);
				
				//Check fields to the right
				
				for (int j = 0; j < ViewDistance; j++) {
					cur = Programm.getCore().getChunkManager().getFieldAt(new Point(CenterField.x + Size * i, CenterField.y + Size * j));
					if(cur.getSeeThru())
						List.add(cur);
				}

					
			} else {
				
				
				break;
			}
			
		}
		
		
		return List;
	}
	
}
