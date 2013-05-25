package me.igwb.Excavor.Entities;

import java.awt.Graphics2D;

import me.igwb.Excavor.Environment.Position;

public interface Entity {

	public void Render(Graphics2D g2D);
	
	public void PrimaryAction(String[] information);
	
	public void SecondaryAction(String[] information);
	
	public Position getPosition();
	
	public void setPosition(Position p);
	
	public EntityType getEntityType();
	
}
