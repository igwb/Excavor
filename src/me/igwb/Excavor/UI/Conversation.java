package me.igwb.Excavor.UI;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import resources.ResourceLoader;

import me.igwb.Excavor.Game.CommandHandler;
import me.igwb.Excavor.Logic.Delay;
import me.igwb.Excavor.Scripting.*;

public class Conversation {

		private Scriptblock conversationBlock;
		private boolean end = false;
		
		private Image Background = new BufferedImage(1, 1, 3);
		private Label[] Text = new Label[1];
		private Rectangle Position = new Rectangle(1, 1, 1, 1);
		private ButtonLayout Layout = ButtonLayout.getStandard(Position, Color.gray);
		
		private String step, lastStep, name;
		
		public Conversation(Scriptblock script) {
			
			conversationBlock = script;	
			
			Command entry = script.getCommand("Entrypoint");
			step = entry.getParameter().regions[0].value[0];
			lastStep = step;
			
			changeBackground(script.getCommand("Background"));
			//changeLayout(script.getChild(step).getCommand("Input"));
			//changeText(script.getChild(step).getCommand("Output"));
			
			name = script.getCommand("Name").getParameter().regions[0].value[0];
		}
		
		public Conversation Begin() {
			Conversation c = new Conversation(conversationBlock);
			c.handleChange();
			return c;
		}
		
		private void changeBackground(Command cmd) {
			
			if(cmd == null)
				return;
			
			ParameterRegion region = cmd.getParameter().regions[0];
			
			String path = region.value[0];
			
			int X, Y, Width, Height;
			
			try {
				X = Integer.parseInt(region.value[1]);
				Y = Integer.parseInt(region.value[2]);
				Width = Integer.parseInt(region.value[3]);
				Height = Integer.parseInt(region.value[4]);
				
				Position = new Rectangle(X, Y, Width, Height);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			
			try {
				Background = ImageIO.read(ResourceLoader.getURL(path, true)).getScaledInstance(Width, Height, 0);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			
		}
		
		private void changeLayout(Command cmd) {
			
			if(cmd == null)
				return;
			
			if(cmd.getParameter().getAllValues()[0].equalsIgnoreCase("Null"))
				Layout = null;
			
			ArrayList<Button> buttons = new ArrayList<Button>();
			
			for(ParameterRegion region : cmd.getParameter().regions) {
				
				Label label = new Label(region.value[0]);
				label.FontSize = Float.parseFloat(region.value[3]);
				
				Rectangle rect = new Rectangle(Integer.parseInt(region.value[1]), Integer.parseInt(region.value[2]), (int) label.getWidth(), (int) label.getHeight());
				
				label.position = new Point(0, (int) (label.getHeight() * 0.75));
				
				buttons.add(new Button(null, label, rect));			
			}
			
			Image selection = new BufferedImage(1, 1, 3);
			try {
				selection = ImageIO.read(ResourceLoader.getURL(conversationBlock.getCommand("Selection").getParameter().regions[0].value[0], true));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Layout = new ButtonLayout(buttons.toArray(new Button[buttons.size()]), selection);
		}
		
		private void changeText(Command cmd) {
			
			if(cmd == null)
				return;
			
			ArrayList<Label> labels = new ArrayList<Label>();
			
			for(ParameterRegion region : cmd.getParameter().regions) {
				
				Label label = new Label(region.value[0]);
				label.position = new Point(Integer.parseInt(region.value[1]), Integer.parseInt(region.value[2]));
				
				label.FontSize = Float.parseFloat(region.value[3]);
				
				labels.add(label);
			}
			
			Text = new Label[1];
			Text = labels.toArray(Text);
		}

		private void handleChange() {
			
			lastStep = step;
			
			for(Command cmd : conversationBlock.getChild(step).getCommands()) {
				
				switch(cmd.type) {
				
				
				case Method:
					
					if(cmd.name.equalsIgnoreCase("EndConversation")) {
						
						ParameterRegion region = cmd.getParameter().regions[0];						
						int i = region.value.length == 0 ? 1 : Integer.parseInt(region.value[0]);	
						
						endIn = new Delay(i);
					} else if(cmd.name.equalsIgnoreCase("DispatchCommand")) {
						
						CommandHandler.handle(cmd.getParameter().getAllValues());
						
					} else if(cmd.name.equalsIgnoreCase("GoTo")) {
						
						step = cmd.getParameter().getAllValues()[0];
						
					}
					
					break;
					
					
				case Variable:
					
					if(cmd.name.equalsIgnoreCase("Output"))
						changeText(cmd);
					
					else if(cmd.name.equalsIgnoreCase("Input"))
						changeLayout(cmd);
					
					else if(cmd.name.equalsIgnoreCase("Background"))
						changeBackground(cmd);
					
					break;
					
					
				default:
					break;
				
					
				
				}
				
			}
			
		}
		
		private Delay endIn;
		
		public boolean getConversationEnd() {
			return end;
		}
		
		public void loop() {
			
			if(endIn != null)
				if(endIn.checkDelay())
					end = true;
			
			Layout.Update();
			
			if(step != lastStep)
				handleChange();
			
			if(Layout.wasButtonPressed()) {
				
				step = conversationBlock.getChild(step).getCommand("Input").getParameter().regions[Layout.getIndex()].value[4];
				Layout.setButtonPressed(false);
				
			}
		}
		
		public void render(Graphics g) {
			
			g.drawImage(Background, Position.x, Position.y, null);
			for(Label l : Text)
				l.Render(g);
			
			Layout.Render(g);
		}
		
		public boolean keyPressed(KeyEvent key) {
			
			if(key.getKeyCode() == KeyEvent.VK_S)
				Layout.moveIndexUp();
			
			else if(key.getKeyCode() == KeyEvent.VK_W)
				Layout.moveIndexDown();
			
			else if(key.getKeyCode() == KeyEvent.VK_SPACE)
				Layout.setButtonPressed(true);
			
			else if(key.getKeyCode() >= KeyEvent.VK_NUMPAD1 && key.getKeyCode() <= KeyEvent.VK_NUMPAD9) {
				Layout.setIndex(key.getKeyCode() - KeyEvent.VK_NUMPAD0 - 1);
				Layout.setButtonPressed(true);
			}
			else
				return false;
			
			return true;
		}

		public String getName() {
			return name;
		}
}
		
		