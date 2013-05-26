package me.igwb.Excavor.Game;

import me.igwb.Excavor.Media.MediaManager;

public class StatusManager {

	private GameStatus status, previousStatus;
	private boolean wasPaused;
	
	public StatusManager() {
		
	}
	
	public GameStatus getStatus() {
		
		return status;
	}
	
	
	public void setPaused(boolean setPaused) {
		
		if(setPaused) {
			if(status != GameStatus.PAUSED && status != GameStatus.IN_BACKGROUND) {
				previousStatus = status;
				status = GameStatus.PAUSED;
			}
		}  else {
			status = previousStatus;
		}
	}
	
	public void setToBackground(boolean setBackground) {
		
		if(setBackground) {
			if(status != GameStatus.PAUSED) {
				previousStatus = status;
			} else {
				wasPaused = true;
			}
			status = GameStatus.IN_BACKGROUND;
			MediaManager.pauseAll();
		} else {
			if(wasPaused) {
				status = GameStatus.PAUSED;
			} else {
				status = previousStatus;
			}
			MediaManager.resumeAll();
		}
	}
	
	
	public void setGameStatus(GameStatus newStatus) {
		
		switch (newStatus) {
		case IN_BACKGROUND:
				setToBackground(true);
			break;
		case PAUSED:
				setPaused(true);
			break;
		default:
			status = newStatus;
			break;
		}
	}
	
	public GameStatus getCurrentStatus() {
		
		return status;
	}
}
