package me.igwb.Excavor.Entities;

import me.igwb.Excavor.Environment.Position;

public interface Entity {

	public void Render();
	
	public void PrimaryAction();
	
	public void SecondaryAction();
	
	public Position getPosition();
	
	public Position setPosition();
	
}
