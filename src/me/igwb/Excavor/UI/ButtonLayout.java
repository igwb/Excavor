package me.igwb.Excavor.UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;


public class ButtonLayout {

	private Button[] Buttons;
	
	private Image Selection;
	private Rectangle SelectionPosition;
	
	private int Index;
	
	private boolean buttonPressed = false, updated = false;
	
	public ButtonLayout(Button[] buttons, Image selection) {
		Buttons = buttons;
		Selection = selection;
		Index = 0;
	}

	public Button getButton() {
		return Buttons[Index];
	}
	
	public Button[] getButtons() {
		return Buttons;
	}

	public void setButtons(Button[] buttons) {
		Buttons = buttons;
	}

	public int getIndex() {
		return Index;
	}
	
	public void setIndex(int index) {
		Index = index;
	}
	
	public void Update() {
		
		if(SelectionPosition == Buttons[Index].Position)
			return;
		
		SelectionPosition = Buttons[Index].Position;
		Selection = Selection.getScaledInstance(SelectionPosition.width, SelectionPosition.height, 0);
		
		updated = true;
	}
	
	public void Render(Graphics g) {
		
		if(!updated)
			Update();
		
		for(Button button : Buttons) {
			button.Render(g);
		}
		
		g.drawImage(Selection, SelectionPosition.x, SelectionPosition.y, null);
		
		updated = false;
	}
	
	public void moveIndexUp() {
		Index++;
		
		if(Index >= Buttons.length)
			Index = 0;
	}
	
	public void moveIndexDown() {
		Index--;
		
		if(Index < 0)
			Index = Buttons.length - 1;
	}

	public boolean wasButtonPressed() {
		return buttonPressed;
	}

	public void setButtonPressed(boolean buttonPressed) {
		this.buttonPressed = buttonPressed;
	}

	public static ButtonLayout getStandard(Rectangle Size, Color color) {
		
		BufferedImage image = new BufferedImage(Size.width, Size.height, IndexColorModel.TRANSLUCENT);
		Graphics g = image.getGraphics();
		g.setColor(Color.RED);
		g.drawRect(0, 0, Size.width - 1, Size.height - 1);
		
		return new ButtonLayout(new Button[] { Button.Ok(Size.x, Size.y, Size.width, Size.height, color) }, image);
	}

	public boolean getKey(int keyCode) {

		for(int i = 0; i < Buttons.length; i++) {
			if(Buttons[i].ShortcutKey == (char) keyCode) {
				Index = i;
				return true;
			}
		}
		return false;
	}
	
}
