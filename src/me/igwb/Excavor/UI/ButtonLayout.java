package me.igwb.Excavor.UI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;


public class ButtonLayout {

	private Button[] Buttons;
	
	private Image Selection;
	private Rectangle SelectionPosition;
	
	private int Index;
	
	private boolean buttonPressed, updated = false;
	
	public ButtonLayout(Button[] buttons, Image selection) {
		Buttons = buttons;
		Selection = selection;
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
		Index++;
		
		if(Index <= 0)
			Index = Buttons.length - 1;
	}

	public boolean wasButtonPressed() {
		return buttonPressed;
	}

	public void setButtonPressed(boolean buttonPressed) {
		this.buttonPressed = buttonPressed;
	}
	
}
