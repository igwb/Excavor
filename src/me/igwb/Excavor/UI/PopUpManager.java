package me.igwb.Excavor.UI;

import java.awt.*;
import java.awt.image.*;
import java.util.ArrayList;

import me.igwb.Excavor.Logic.Delay;

public class PopUpManager {

	private static double endTime;

	private static double vanishTime;
	
	private static Point StandardPosition = new Point();
	
	private static Rectangle Position;
	
	private static Image Background;
	
	private static ArrayList<Label> Labels;
	
	private static BufferedImage image;
	
	public static void initialize(Image background, double vanishAfter, double endAfter, Point standartPosition, Rectangle position) {
		Position = position;

		endTime = endAfter;
		vanishTime = vanishAfter;
		
		Background = background.getScaledInstance(position.width, position.height, 0);
		image = new BufferedImage(Background.getWidth(null), Background.getHeight(null), BufferedImage.TRANSLUCENT);
		
		Labels = new ArrayList<Label>();		
		StandardPosition = standartPosition;
	}
	
	public static void show(Label label, boolean overrideStandardPosition) {
		
		if(label == null)
			return;
		
		if(!overrideStandardPosition)
			label.position = StandardPosition;
		
		Labels.add(label);		
	}
	
	private static Delay delay;
	private static boolean vanish = false;
	
	public static void Render(Graphics g) {
		
		if(Labels.size() <= 0)
			return;
		
		
		if(delay == null)
			delay = new Delay(vanishTime);
		
		if(delay.checkDelay() && vanish) {
			Labels.remove(0);
			delay = null;
			vanish = false;
			image = new BufferedImage(Background.getWidth(null), Background.getHeight(null), BufferedImage.TRANSLUCENT);
			return;
		}
		
		Graphics2D g2D = (Graphics2D) image.getGraphics(), G2D = (Graphics2D) g;
		
		AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
		
		if(delay.checkDelay() && !vanish) {
			vanish = true;
			delay.expand(endTime - vanishTime);
		}
		
		Float alpha = (float) ((float) delay.getTimeLeft() / (endTime - vanishTime));
		
		if(vanish) {
			composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
			//g.drawString(alpha.toString(), 60, 200);
			//g.drawRect(60, 200, 100, 100);
		}
		
		g2D.drawImage(Background, 0, 0, null);		
		Labels.get(0).Render(g2D);
		
		G2D.setComposite(composite);		
		G2D.drawImage(image, Position.x, Position.y, null);		
		G2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}
}
