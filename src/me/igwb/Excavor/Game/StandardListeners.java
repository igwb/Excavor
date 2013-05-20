package me.igwb.Excavor.Game;

import java.util.Map.Entry;

import me.igwb.Excavor.Media.MediaManager;
import me.igwb.Excavor.Media.RegisteredMedia;

public class StandardListeners {

	private ValueListener[] listeners;
	private static StandardListeners SL;
	
	public static void registerListeners() {
		SL = new StandardListeners();		
	}
	
	public static StandardListeners getListeners() {
		return SL;
	}
	
	private StandardListeners() {
		listeners = new ValueListener[] 
				{ 
					new VolumeListener() 
				};
		
		for(ValueListener listener : listeners)
			ValueManager.registerListener(listener);
	}
	
	class VolumeListener implements ValueListener {

		@Override
		public void valueSet(Entry<String, String> value) {

			if(value.getKey().equalsIgnoreCase("Master")) {
				
				MediaManager.setMusicVolume(Float.parseFloat(ValueManager.getValue("Music")) * (Float.parseFloat(value.getValue()) / 100));
				
			}
			
			else if(value.getKey().equalsIgnoreCase("Music")) {
			
				MediaManager.setMusicVolume(Float.parseFloat(value.getValue()) * (Float.parseFloat(ValueManager.getValue("Master")) / 100));
								
			}
			
			else if(value.getKey().equalsIgnoreCase("MuteMaster")) {
				
				if(value.getValue().equals("Muted"))
					MediaManager.muteAll();
				else {
					MediaManager.unMute();
					if(ValueManager.getValue("MuteMusic").equalsIgnoreCase("UnMuted"))
							MediaManager.playClip(RegisteredMedia.ExcavorTheme.getMediaName(), true);
				}
			}
			
			else if(value.getKey().equalsIgnoreCase("MuteMusic")) {
				
				if(value.getValue().equals("Muted"))
					MediaManager.stopClip(RegisteredMedia.ExcavorTheme.getMediaName());
				else
					MediaManager.playClip(RegisteredMedia.ExcavorTheme.getMediaName(), true);
			}
			
		}
		
	}
	
}
