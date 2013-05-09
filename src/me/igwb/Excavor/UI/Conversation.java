package me.igwb.Excavor.UI;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import resources.ResourceLoader;

import me.igwb.Excavor.Game.CommandHandler;
import me.igwb.Excavor.Game.ValuesManager;
import me.igwb.Excavor.Logic.Delay;
import me.igwb.Excavor.Scripting.*;

public class Conversation {

		private Scriptblock conversationBlock;
		private boolean end = false;
		
		private Image Background = new BufferedImage(1, 1, 3), Selection = new BufferedImage(1, 1, 3);
		private Label[] Text = new Label[1];
		private Rectangle Position = new Rectangle(1, 1, 1, 1);
		private ButtonLayout Layout = ButtonLayout.getStandard(Position, Color.gray);
	
		private int StartIndex = 0;
		
		private String step, lastStep, name;
		
		private Conversation otherConversation = null;
		
		public Conversation(Scriptblock script) {
			
			conversationBlock = script;	
			
			Command entry = script.getCommand("Entrypoint");
			step = entry.getParameter().regions[0].value[0];
			lastStep = step;
			
			changeBackground(script.getCommand("Background"));
			changeSelection(script.getCommand("Selection"));
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
				label.FontSize = Float.parseFloat(region.value[5]);
				
				Rectangle rect = new Rectangle(Integer.parseInt(region.value[1]), Integer.parseInt(region.value[2]), (int) label.getWidth(), (int) label.getHeight());
				
				label.position = new Point(0, (int) (label.getHeight() * 0.75));
				
				int width = Integer.parseInt(region.value[3]);
				int height = Integer.parseInt(region.value[4]);
				
				if(width > 0 && width > label.getWidth()) {
					label.position.x = (int)( ((double) width / 2d) - (label.getWidth() / 2d) );
					rect.width = width;
				}
				
				if(height > 0 && height > label.getHeight()) {
					label.position.y = (int)( ((double) height / 2d) + (label.getHeight() / 2d) );
					rect.height = height;
				}
				
				Button button = new Button(null, label, rect);
				button.nextStep = region.value[6];
				
				buttons.add(button);
			}
			
			Layout = new ButtonLayout(buttons.toArray(new Button[buttons.size()]), Selection);
		}
		
		private void changeSelection(Command cmd) {
			try {
				Selection = ImageIO.read(ResourceLoader.getURL(cmd.getParameter().getAllValues()[0], true));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		private void changeText(Command cmd) {
			
			if(cmd == null)
				return;
			
			ArrayList<Label> labels = new ArrayList<Label>();
			
			for(ParameterRegion region : cmd.getParameter().regions) {
				
				Label label = new Label(region.value[0]);
				label.position = new Point(Integer.parseInt(region.value[1]), Integer.parseInt(region.value[2]));
				
				label.FontSize = Float.parseFloat(region.value[5]);
				
				labels.add(label);
				
				int width = Integer.parseInt(region.value[3]);
				int height = Integer.parseInt(region.value[4]);
				
				if(width > 0 && width > label.getWidth()) {
					label.position.x += (int)( ((double) width / 2d) - (label.getWidth() / 2d) );
				}
				
				if(height > 0 && height > label.getHeight()) {
					label.position.y += (int)( ((double) height / 2d) + (label.getHeight() / 2d) );
				}
			}
			
			Text = new Label[1];
			Text = labels.toArray(Text);
		}

		private void showValue(Command cmd) {
			
			ArrayList<Label> labels = new ArrayList<Label>();
			
			for(Label label : Text) {
				labels.add(label);
			}
			
			for(ParameterRegion region : cmd.getParameter().regions) {
				
				Label label = new Label(ValuesManager.getValue(region.value[0]));
				label.position = new Point(Integer.parseInt(region.value[1]), Integer.parseInt(region.value[2]));
				
				label.FontSize = Float.parseFloat(region.value[5]);
				
				int width = Integer.parseInt(region.value[3]);
				int height = Integer.parseInt(region.value[4]);
				
				if(width > 0 && width > label.getWidth()) {
					label.position.x += (int)( ((double) width / 2d) - (label.getWidth() / 2d) );
				}
				
				if(height > 0 && height > label.getHeight()) {
					label.position.y += (int)( ((double) height / 2d) + (label.getHeight() / 2d) );
				}
				
				labels.add(label);
			}
			
			Text = new Label[1];
			Text = labels.toArray(Text);
		}
		
		private void setValue(Command cmd) {
			
			for(ParameterRegion region : cmd.getParameter().regions) {
				
				ValuesManager.setValue(region.value[0], region.value[1]);
				
			}
			
		}

		private void showConversation(Command cmd) {
			
			otherConversation = ConversationManager.getConversation(cmd.getParameter().getAllValues()[0]).Begin();
			step = cmd.getParameter().getAllValues()[1];
			StartIndex = Integer.parseInt(cmd.getParameter().getAllValues().length > 2 ? cmd.getParameter().getAllValues()[2] : "0");
			
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
						
						if(cmd.getParameter().getAllValues().length > 1)						
							StartIndex = Integer.parseInt(cmd.getParameter().getAllValues()[1]);
						else
							StartIndex = 0;
						
					} else if(cmd.name.equalsIgnoreCase("showValue")) {
						
						showValue(cmd);
						
					} else if(cmd.name.equalsIgnoreCase("setValue")) {
						
						setValue(cmd);
						
					} else if(cmd.name.equalsIgnoreCase("increaseValue")) {
						
						increaseValue(cmd);
						
					} else if(cmd.name.equalsIgnoreCase("decreaseValue")) {
						
						decreaseValue(cmd);
						
					} else if(cmd.name.equalsIgnoreCase("showConversation")) {
						
						showConversation(cmd);
						
					}
					break;
					
					
				case Variable:
					
					if(cmd.name.equalsIgnoreCase("Output"))
						changeText(cmd);
					
					else if(cmd.name.equalsIgnoreCase("Input"))
						changeLayout(cmd);
					
					else if(cmd.name.equalsIgnoreCase("Background"))
						changeBackground(cmd);
					
					else if(cmd.name.equalsIgnoreCase("Selection"))
						changeSelection(cmd);
					
					break;
					
					
				default:
					break;					
				
				}
				
			}
			Layout.setIndex(StartIndex);
			//StartIndex = 0;
		}
		
		private void decreaseValue(Command cmd) {
			for(ParameterRegion region : cmd.getParameter().regions) {
				
				double value;
				
				try {
					
				value = Double.parseDouble(ValuesManager.getValue(region.value[0]));
				
				} catch (Exception e) {
					
					e.printStackTrace();
					return;
					
				}
				
				value -= Double.parseDouble(region.value[1]);
				
				if(region.value.length > 2)
					if(value < Double.parseDouble(region.value[2]))
						value += Double.parseDouble(region.value[1]);
				
				String s = Double.toString(value);
				
				if(s.endsWith(".0"))
					s = s.substring(0, s.length() - 2);
				
				ValuesManager.setValue(region.value[0], s);
				
			}
		}
	
		private void increaseValue(Command cmd) {
			for(ParameterRegion region : cmd.getParameter().regions) {
				
				double value;
				
				try {
					
				value = Double.parseDouble(ValuesManager.getValue(region.value[0]));
				
				} catch (Exception e) {
					
					e.printStackTrace();
					return;
					
				}
				
				value += Double.parseDouble(region.value[1]);
				
				if(region.value.length > 2)
					if(value > Double.parseDouble(region.value[2]))
						value -= Double.parseDouble(region.value[1]);
				
				String s = Double.toString(value);
				
				if(s.endsWith(".0"))
					s = s.substring(0, s.length() - 2);
				
				ValuesManager.setValue(region.value[0], s);
				
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
			
			if(otherConversation != null) {
				
				otherConversation.loop();
				
				if(otherConversation.getConversationEnd())
					otherConversation = null;
				
				return;
			}
			
			Layout.Update();
			
			if(step != lastStep)
				handleChange();
			
			if(Layout.wasButtonPressed()) {
				
				StartIndex = 0;
				step = Layout.getButton().nextStep;
				Layout.setButtonPressed(false);
				
			}
		}
		
		public void render(Graphics g) {
			
			if(g == null)
				return;
			
			if(otherConversation != null) {				
				
				otherConversation.render(g);
				
				return;
			}
			
			g.drawImage(Background, Position.x, Position.y, null);
			for(Label l : Text)
				l.Render(g);
			
			Layout.Render(g);
		}
		
		public boolean keyPressed(KeyEvent key) {
			
			if(otherConversation != null)
				return otherConversation.keyPressed(key);
			
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
		
		