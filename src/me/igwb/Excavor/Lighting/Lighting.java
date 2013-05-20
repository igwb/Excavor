package me.igwb.Excavor.Lighting;

import java.awt.*;
import java.awt.image.BufferedImage;

import me.igwb.Excavor.Environment.Position;

public class Lighting {
	
	private BufferedImage BasicLight;
	
	private Color color;
	
	private int strength, softness;

	private Image BasicImage;
	
	public Lighting (Color color, int strength, int softness) {
		
		this.color = color;
		this.strength = strength;
		this.softness = softness;

		
		//drawBasicLight();
	}
	
	
	@SuppressWarnings("unused")
	private void drawBasicLight() {
		
		BasicLight = new BufferedImage(strength, strength, 3);		
		Graphics2D g2D = BasicLight.createGraphics();
		
		float radius = (float) Math.pow(strength*strength * 2, 0.5);
		
		float[] dist = new float[] { (float)strength * 0.0793f - (float)softness * 0.0793f, (float)softness * 0.793f };
		
		Color[] colors = new Color[] { color, Color.BLACK };
		
		RadialGradientPaint p = new RadialGradientPaint(new Point(BasicLight.getWidth() / 2, BasicLight.getHeight() / 2), radius, dist, colors);
		
		//g2D.setColor(Color.black);
		//g2D.fillRect(0, 0, width, height);
		g2D.setPaint(p);
		g2D.fillRect(0, 0, strength, strength);
		
		g2D.dispose();
		
		BasicImage = BasicLight.getScaledInstance(strength * 2, strength, Image.SCALE_SMOOTH);
	}
	
	public void Render(Position p, Graphics g) {

		Point pos = new Point((p.getX() - p.getZ() / 2) - BasicLight.getWidth(), 
								(p.getY()) - BasicLight.getHeight() / 2);
		
		Graphics2D g2D = (Graphics2D) g;
				
		
		g2D.drawImage(BasicImage, pos.x, pos.y, null);
		
		/*p.setX(p.getX() - BasicLight.getWidth() / 2);
		p.setY(p.getY() - BasicLight.getHeight() / 2);
		p.setZ(p.getZ() - Math.round((float)(strength + softness) / 50f));
		
		Position other = new Position(p.getX() + BasicLight.getWidth(), p.getY() + BasicLight.getHeight(), p.getZ() + Math.round((float)(strength + softness) / 50f));
		
		Distance d = p.distance(other);
		
		int maxDist = d.getDistance();
		
		int dX = d.getDistanceX();
		int dY = d.getDistanceY();
		int dZ = d.getDistanceZ();
		
		Field[] fields = new Field[dX * dY * dZ];
		
		for(int i = 0; i < fields.length; i++) {
			fields[i] = Programm.getCore().getChunkManager().getFieldAt(position, fieldSize)
		}
		*/
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
		//drawBasicLight();
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
		//drawBasicLight();
	}

	public int getSoftness() {
		return softness;
	}

	public void setSoftness(int softness) {
		this.softness = softness;
		//drawBasicLight();
	}
}
