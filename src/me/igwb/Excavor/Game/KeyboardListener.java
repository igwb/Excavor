package me.igwb.Excavor.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import me.igwb.Excavor.Player.Player.Direction;
import me.igwb.Excavor.UI.*;


public class KeyboardListener implements KeyListener{

	@Override
	public void keyPressed(KeyEvent arg0) {
		boolean moving = Programm.getCore().getActivePlayer().isMoving();
		
		DeveloperConsole.keyPressed(arg0);
		if(!DeveloperConsole.allowUpdate())
			return;
			
		ConversationManager.keyPressed(arg0);
		if(!ConversationManager.allowUpdate())
			return;
		
		switch (arg0.getKeyChar()) {
		case 'w': case 'W':
			if(moving && Programm.getCore().getActivePlayer().getDirection() == Direction.Left | Programm.getCore().getActivePlayer().getDirection() == Direction.UpLeft) {
				Programm.getCore().getActivePlayer().setDirection(Direction.UpLeft);
			} else if(moving && Programm.getCore().getActivePlayer().getDirection() == Direction.Right | Programm.getCore().getActivePlayer().getDirection() == Direction.UpRight) {
				Programm.getCore().getActivePlayer().setDirection(Direction.UpRight);
			} else {
				Programm.getCore().getActivePlayer().setDirection(Direction.Up);
				Programm.getCore().getActivePlayer().setMoving(true);
			}
			break;
		case 'a': case 'A':
			if(moving && Programm.getCore().getActivePlayer().getDirection() == Direction.Up | Programm.getCore().getActivePlayer().getDirection() == Direction.UpLeft) {
				Programm.getCore().getActivePlayer().setDirection(Direction.UpLeft);
			} else if(moving && Programm.getCore().getActivePlayer().getDirection() == Direction.Down | Programm.getCore().getActivePlayer().getDirection() == Direction.DownLeft) {
				Programm.getCore().getActivePlayer().setDirection(Direction.DownLeft);
			} else {
				Programm.getCore().getActivePlayer().setDirection(Direction.Left);
				Programm.getCore().getActivePlayer().setMoving(true);
			}
			break;
		case 's': case 'S':
			if(moving && Programm.getCore().getActivePlayer().getDirection() == Direction.Left | Programm.getCore().getActivePlayer().getDirection() == Direction.DownLeft) {
				Programm.getCore().getActivePlayer().setDirection(Direction.DownLeft);
			} else if(moving && Programm.getCore().getActivePlayer().getDirection() == Direction.Right | Programm.getCore().getActivePlayer().getDirection() == Direction.DownRight) {
				Programm.getCore().getActivePlayer().setDirection(Direction.DownRight);
			} else {
				Programm.getCore().getActivePlayer().setDirection(Direction.Down);
				Programm.getCore().getActivePlayer().setMoving(true);
			}
			break;
		case 'd': case 'D':
			if(moving && Programm.getCore().getActivePlayer().getDirection() == Direction.Up | Programm.getCore().getActivePlayer().getDirection() == Direction.UpRight) {
				Programm.getCore().getActivePlayer().setDirection(Direction.UpRight);
			} else if(moving && Programm.getCore().getActivePlayer().getDirection() == Direction.Down | Programm.getCore().getActivePlayer().getDirection() == Direction.DownRight) {
				Programm.getCore().getActivePlayer().setDirection(Direction.DownRight);
			} else {
				Programm.getCore().getActivePlayer().setDirection(Direction.Right);
				Programm.getCore().getActivePlayer().setMoving(true);
			}
			break;
		case 'y' : case 'Y':
			Programm.getCore().getActivePlayer().setHealth(
			Programm.getCore().getActivePlayer().getHealth() - 1);
			break;
		case 'x' : case 'X':
			Programm.getCore().getActivePlayer().setHealth(
			Programm.getCore().getActivePlayer().getHealth() + 1);
			break;			
		case 'c' : case 'C':
			Programm.getCore().getActivePlayer().setArmor(
			Programm.getCore().getActivePlayer().getArmor() - 1);
			break;
		case 'v' : case 'V':
			Programm.getCore().getActivePlayer().setArmor(
			Programm.getCore().getActivePlayer().getArmor() + 1);
			break;
		case 'p' : case 'P':
			PopUpManager.show(new Label("You've activated this test PopUp!"), false);		
			break;
		case KeyEvent.VK_SPACE:
			break;
		case KeyEvent.VK_ESCAPE:
			ConversationManager.startConversation("Menu");
			break;
		case 'o' : case 'O':
			ConversationManager.startConversation("Options");
			break;
		default:
			PopUpManager.show(new Label("You've pressed the uneventfull key '" + arg0.getKeyChar() + "'!"), false);
			break;
		}	
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		switch (arg0.getKeyChar()) {
		case 'w': case 'W':
			if(Programm.getCore().getActivePlayer().getDirection() == Direction.UpLeft) {
				Programm.getCore().getActivePlayer().setDirection(Direction.Left);
			} else if(Programm.getCore().getActivePlayer().getDirection() == Direction.UpRight) {
				Programm.getCore().getActivePlayer().setDirection(Direction.Right);
			} else {
				Programm.getCore().getActivePlayer().setMoving(false);
			}
			break;
		case 'a': case 'A':
			if(Programm.getCore().getActivePlayer().getDirection() == Direction.UpLeft) {
				Programm.getCore().getActivePlayer().setDirection(Direction.Up);
			} else if(Programm.getCore().getActivePlayer().getDirection() == Direction.DownLeft) {
				Programm.getCore().getActivePlayer().setDirection(Direction.Down);
			} else {
				Programm.getCore().getActivePlayer().setMoving(false);
			}
			break;
		case 's': case 'S':
			if(Programm.getCore().getActivePlayer().getDirection() == Direction.DownLeft) {
				Programm.getCore().getActivePlayer().setDirection(Direction.Left);
			} else if(Programm.getCore().getActivePlayer().getDirection() == Direction.DownRight) {
				Programm.getCore().getActivePlayer().setDirection(Direction.Right);
			} else {
				Programm.getCore().getActivePlayer().setMoving(false);
			}
			break;
		case 'd': case 'D':
			if(Programm.getCore().getActivePlayer().getDirection() == Direction.UpRight) {
				Programm.getCore().getActivePlayer().setDirection(Direction.Up);
			} else if(Programm.getCore().getActivePlayer().getDirection() == Direction.DownRight) {
				Programm.getCore().getActivePlayer().setDirection(Direction.Down);
			} else {
				Programm.getCore().getActivePlayer().setMoving(false);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		DeveloperConsole.keyTyped(arg0);		
	}
}
