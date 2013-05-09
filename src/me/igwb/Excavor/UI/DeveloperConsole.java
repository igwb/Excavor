package me.igwb.Excavor.UI;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import me.igwb.Excavor.Game.CommandHandler;

import resources.ResourceLoader;

public class DeveloperConsole {

	private static BufferedImage back;
	
	private static ArrayList<String> lines;
	private static ArrayList<String> input;
	
	private static String Line = "";
	
	private static Font font;
	
	private static int BeginAt = 0, Index = 0;
	
	private static boolean show = false;
	
	private static ExceptionRecorder er = new ExceptionRecorder();
	
	public static void initialize(int width, int height) {
		
		lines = new ArrayList<String>();
		input = new ArrayList<String>();
		lines.add("");
		input.add("");
		
		try {
		font = Font.createFont(Font.PLAIN, ResourceLoader.getURL("/resources/Sans Bold.ttf").openStream());
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		font = font.deriveFont((float) 10).deriveFont(Font.PLAIN);
		
		back = new BufferedImage(width, height, 3);
		Graphics2D g = back.createGraphics();
		
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		g.setColor(Color.GREEN);
		g.drawRect(0, 0, width - 1, height - 1);
						
		System.setErr(new PrintStream(er));
		System.setOut(new PrintStream(er));
	}
	
	public static void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode() == KeyEvent.VK_PAGE_UP && show) {
			BeginAt -= 1;
			
			if(BeginAt < 0)
				BeginAt = 0;
		}
		else if(arg0.getKeyCode() == KeyEvent.VK_PAGE_DOWN && show) {
			BeginAt += 1;
			
			if(BeginAt >= lines.size() - 15)
				if(lines.size() > 15)
					BeginAt = lines.size() - 15;
				else
					BeginAt = 0;
		} 
		else if(arg0.getKeyCode() == KeyEvent.VK_ENTER && show) {
			lines.add("> " + Line);
			input.add(Line);
			Index = input.size();
			
			String[] Lines = Line.split(" ");
			CommandHandler.handle(Lines);
			
			if(lines.size() > 15)
				BeginAt = lines.size() - 15;
			
			Line = "";
		}
		else if(arg0.getKeyCode() == KeyEvent.VK_F6) 
			show = !show
			;
		else if(arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE && show) {
			if(Line.length() < 1)
				return;
			
			Line = Line.substring(0, Line.length() - 1);
			//if(!Line.endsWith(" "))
				//Line.trim();
		} else if(arg0.getKeyCode() == KeyEvent.VK_UP && show) {
			Index--;
			if(Index < 0)
				Index = input.size() - 1;
			
			Line = input.get(Index);
			
		} else if(arg0.getKeyCode() == KeyEvent.VK_DOWN && show) {
			Index++;
			if(input.size() - 1 < Index)
				Index = 0;
			
			Line = input.get(Index);
		} else if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE && show)
			Line = "";
	}

	public static void keyTyped(KeyEvent arg0) {
		
		if(show && !arg0.isActionKey())
		switch(arg0.getKeyChar()) {
			
		case KeyEvent.VK_BACK_SPACE : case KeyEvent.VK_ENTER : case KeyEvent.VK_CONTROL : case KeyEvent.VK_ESCAPE: 
			return;
			
		default:
			Line += arg0.getKeyChar();
			if(arg0.getKeyCode() != KeyEvent.VK_SPACE)
				Line.trim();
			return;
		
		}
	}
	
	public static void render(Graphics g) {
		
		if(!show)
			return;	
		
		g.setFont(font);
		
		BufferedImage image = new BufferedImage(back.getWidth(), back.getHeight(), 3);		
		Graphics2D g2D = image.createGraphics();
		g2D.setFont(font);
		
		g2D.drawImage(back, 0, 0, null);		
		
		g2D.setColor(Color.WHITE);
		
		if(exception != "") {
		
			message("[System " + new java.util.Date().toString() + "]");
			
			message(exception);
			
			exception = "";
		
		}
		
		for(int i = BeginAt; i < lines.size(); i++)
			g2D.drawString(lines.get(i), 15, 13 * (i - BeginAt) - 2);
		
		g2D.setColor(Color.BLACK);
		g2D.fillRect(1, back.getHeight() - 17, back.getWidth() - 2, 16);
		g2D.setColor(Color.WHITE);
		g2D.drawString(">", 2, back.getHeight() - 5);
		g2D.drawString(Line, 15, back.getHeight() - 5);
		
		g.drawImage(image, 0, 0, null);
	}
	
	public static void message(String message) {	
		
		for(String s : message.split("\n")) {
			
		String line = lines.get(lines.size() - 1);		
		lines.set(lines.size() - 1, s);
		lines.add(line);
		
		}
	}
	
	private static String exception = "";
	
	public static void write(int ascii) throws IOException {
		
		exception += (char) ascii;
	}
	
	public static boolean allowUpdate() {
		return !show;
	}
	
	@Deprecated
	public static void printException(Exception e) {
		
		if(e == null)
			return;
		
		String message = e.toString() == null || e.toString() == "" ? "Unknown cause!" : e.toString();
		
		message(message);
		
		for(StackTraceElement ste : e.getStackTrace())
			message(ste.toString());
	}
}
