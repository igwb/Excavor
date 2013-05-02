package me.igwb.Excavor.Game;

import java.awt.Point;

import me.igwb.Excavor.UI.DeveloperConsole;

public class CommandHandler {
	
	public static void handle(String[] cmd) {
		
		if(cmd.length <= 0)
			return;
		
		try {
		
		switch(cmd[0].toLowerCase()) {
		
		case "exit":
			System.exit(0);
			
		case "sethealth":
			Programm.getCore().getActivePlayer().setHealth(Integer.parseInt(cmd[1]));
			DeveloperConsole.message("Changed Player health to " + cmd[1]);
			break;
			
		case "setarmor":
			Programm.getCore().getActivePlayer().setArmor(Integer.parseInt(cmd[1]));
			DeveloperConsole.message("Changed Player armor strength to " + cmd[1]);
			break;

		case "setposition" : case "teleport":
			Programm.getCore().getActivePlayer().setPosition(new Point(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[2])));
			DeveloperConsole.message("Teleported player to " + cmd[1] + ", " + cmd[2]);
			break;
			
		case "getplayerstats" : case "getstats" : case "playerstats":
			DeveloperConsole.message("---");
			for(String line : Programm.getCore().getActivePlayer().getStats())
				DeveloperConsole.message(line);
			break;
			
		default:
			//DeveloperConsole.message("Could not handle \"" + cmd[0] + "\"");
			break;
		}
		
		} catch (Exception e) {
			e.printStackTrace();
			DeveloperConsole.printException(e);
		}
	}
	
}
