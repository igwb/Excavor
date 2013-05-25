package me.igwb.Excavor.Entities.Lighting;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import me.igwb.Excavor.Entities.Entity;
import me.igwb.Excavor.Entities.EntityType;
import me.igwb.Excavor.Environment.Position;

public class SphereLight implements Entity {

	private LightSourceType SourceType = LightSourceType.Sphere;
	private Image preRenderedImage;
	
	private int strength;
	private Position position;
	private Color color;
	
	private boolean valueChanged;
	
	public SphereLight(Position position, Color color, int strength) {
		
		this.position = position == null ? new Position() : position;
		this.color = color == null ? Color.WHITE : color;
		this.strength = strength;
		
		preRenderedImage = SourceType.getLight(strength, color);
		valueChanged = false;
	}
	
	@Override
	public void Render(Graphics2D g2D) {
		Image image = valueChanged ? SourceType.getLight(strength, color) : preRenderedImage;
		
		int x = position.getX() - image.getWidth(null) / 2,// - position.getZ(),
			y = position.getY() - position.getZ() - image.getHeight(null) / 2;
		
		g2D.drawImage(image, x, y, null);
		valueChanged = false;
	}

	@Override
	public void PrimaryAction(String[] information) {
		
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		if(this.strength == strength)
			return;
		
		this.strength = strength;
		valueChanged = true;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		if(this.color.equals(color))
			return;
		
		this.color = color;
		valueChanged = true;
	}

	@Override
	public void SecondaryAction(String[] information) {
		
	}

	@Override
	public Position getPosition() {
		return position;
	}

	@Override
	public void setPosition(Position p) {
		if(p.equals(position))
			return;
		
		position = p;
		valueChanged = true;
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.LightSource;
	}
}
