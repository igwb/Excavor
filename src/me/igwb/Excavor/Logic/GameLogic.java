package me.igwb.Excavor.Logic;

import java.awt.Point;

import me.igwb.Excavor.Environment.Field;
import me.igwb.Excavor.Game.Programm;
import me.igwb.Excavor.Player.Player;
import me.igwb.Excavor.UI.ConversationManager;
import me.igwb.Excavor.UI.DeveloperConsole;

public class GameLogic {

	public GameLogic() {
		
	}
	
	public void update(double delta) {
		
		if(!DeveloperConsole.allowUpdate())
			return;
		
		ConversationManager.update();
		
		if(!ConversationManager.allowUpdate())
			return;
		
		movePlayer(Programm.getCore().getActivePlayer(), delta, true);
		
	}
	
	public void movePlayer(Player p, double delta) throws IllegalArgumentException {
		movePlayer(p, delta, true);
	}
		
	public void movePlayer(Player p, double delta, boolean canCollide) throws IllegalArgumentException {
		
		Point playerPos = p.getPosition(), newPos;
		Field newField;
		
		int moveDistance = (int)Math.ceil(1 * delta);
		

		
		if(p.isMoving()) {
			switch (p.getDirection()) {
			case Up:
				newPos = new Point(playerPos.x, playerPos.y - moveDistance);
				break;
			case UpLeft:
				newPos = new Point(playerPos.x - moveDistance, playerPos.y - moveDistance);
				break;
			case UpRight:
				newPos = new Point(playerPos.x + moveDistance, playerPos.y - moveDistance);
				break;
			case Right:
				newPos = new Point(playerPos.x + moveDistance, playerPos.y);
				break;
			case Down:
				newPos = new Point(playerPos.x, playerPos.y + moveDistance);
				break;
			case DownLeft:
				newPos = new Point(playerPos.x - moveDistance, playerPos.y + moveDistance);
				break;
			case DownRight:
				newPos = new Point(playerPos.x + moveDistance, playerPos.y + moveDistance);
				break;
			case Left:
				newPos = new Point(playerPos.x - moveDistance, playerPos.y);
				break;
			default:
				throw new IllegalArgumentException("Unknow direction!");
			}
			
			newField = Programm.getCore().getChunkManager().getFieldFromAbsolute(newPos);
			
			if(newField.getWalkable()) {
				p.setPosition(newPos);
			} else {
				p.setPosition(p.getPosition());
			}
		}
	}
	

	
}
