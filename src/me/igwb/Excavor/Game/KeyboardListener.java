package me.igwb.Excavor.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import me.igwb.Excavor.Player.Player.Direction;


public class KeyboardListener implements KeyListener{

	@Override
	public void keyPressed(KeyEvent arg0) {
		
		switch (arg0.getKeyChar()) {
		case 'w': case 'W':
			Programm.getCore().getActivePlayer().setDirection(Direction.Up);
			Programm.getCore().getActivePlayer().setMoving(true);
			break;
		case 'a': case 'A':
			Programm.getCore().getActivePlayer().setDirection(Direction.Left);
			Programm.getCore().getActivePlayer().setMoving(true);
			break;
		case 's': case 'S':
			Programm.getCore().getActivePlayer().setDirection(Direction.Down);
			Programm.getCore().getActivePlayer().setMoving(true);
			break;
		case 'd': case 'D':
			Programm.getCore().getActivePlayer().setDirection(Direction.Right);
			Programm.getCore().getActivePlayer().setMoving(true);
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
		default:
			break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		switch (arg0.getKeyChar()) {
		case 'w': case 'W':
			Programm.getCore().getActivePlayer().setMoving(false);
			break;
		case 'a': case 'A':
			Programm.getCore().getActivePlayer().setMoving(false);
			break;
		case 's': case 'S':
			Programm.getCore().getActivePlayer().setMoving(false);
			break;
		case 'd': case 'D':
			Programm.getCore().getActivePlayer().setMoving(false);
			break;
		default:
			break;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
