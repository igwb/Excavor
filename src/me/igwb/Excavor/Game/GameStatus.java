package me.igwb.Excavor.Game;

public enum GameStatus {

	RUNNING (0),
	IN_BACKGROUND (1),
	PAUSED (2),
	MAIN_MENU (3),
	CONVERSATION (4),
	INVENTORY (5),
	WORLD_MAP (6);
	
	public int id;
	public boolean updateGame;
	
	private GameStatus(int id) {
		this.id = id;
	}
	
	
}
