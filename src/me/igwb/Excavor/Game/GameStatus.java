package me.igwb.Excavor.Game;

public enum GameStatus {

	RUNNING (0),
	IN_BACKGROUND (1),
	PAUSED (2),
	CONVERSATION (3),
	INVENTORY (4),
	WORLD_MAP (5);
	
	public int id;
	public boolean updateGame;
	
	private GameStatus(int id) {
		this.id = id;
	}
	
	
}
