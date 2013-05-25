package me.igwb.Excavor.Lighting;

import java.awt.*;
import java.awt.image.BufferedImage;

import me.igwb.Excavor.Environment.*;
import me.igwb.Excavor.Game.*;

public class LightCalculation {

	public static BufferedImage PointCalculation(Position Center, int Strength, Color color) {
		
		BufferedImage Lighting = new BufferedImage(Programm.getCore().GameCanvasSize.width, Programm.getCore().GameCanvasSize.height, 3);
		
		Graphics2D g2D = Lighting.createGraphics();
		g2D.setColor(color);
		
		int MaxX = Center.getX() + Strength, MinX = Center.getX() - Strength;
		int MaxY = Center.getY() + Strength, MinY = Center.getY() - Strength;
		int MaxZ = Center.getZ() + Strength, MinZ = Center.getZ() - Strength;
		
		Position Max = new Position(MaxX, MaxY, MaxZ);
		Position Min = new Position(MinX, MinY, MinZ);
		
		int MaxDistance = Center.distance(Max).getDistance();
		
		Position[] Positions = new Position[Min.distance(Max).getDistanceX() * 
		                                    Min.distance(Max).getDistanceX() * 
		                                    Min.distance(Max).getDistanceZ()];
		
		for(int X = 0; X <= Max.getX() - Min.getX(); X++)
			for(int Y = 0; Y <= Max.getY() - Min.getY(); Y++)		
				for (int Z = 0; Z <= Max.getZ() - Min.getZ(); Z++)					
					Positions[X+Y+Z] = new Position(X + Min.getX(), Y + Min.getY(), Z + Min.getZ());
		
		for(int i = 0; i < Positions.length; i++) {
			
			Field field = Programm.getCore().getChunkManager().getFieldAt(Positions[i]);			
			
			if(Positions[i] == null || field == null)
				continue;
			
			int distance = Center.distance(Positions[i]).getDistance();
			
			AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)distance / (float)MaxDistance);
			g2D.setComposite(composite);
			
			int X = Positions[i].getX() - Positions[i].getZ()*2;
			int Y = Positions[i].getY() - Positions[i].getZ();
			
			g2D.drawLine(X-1, Y-1, X+1, Y+1);
		}
		
		return Lighting;
	}
	
	
	@SuppressWarnings("unused")
	private static void paintLeft(float alpha, Graphics2D g2D, Position position) {
		/*
		AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
		g2D.setComposite(composite);
		
		g2D.drawImage(EnvironmentLoader.getImage(FieldType.BLeft), , arg2, arg3)
		*/
	}
}
