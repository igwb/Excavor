package me.igwb.Excavor.Game;

import java.util.Map.Entry;

import me.igwb.Excavor.Media.MediaManager;

public class StandardListeners {

	private ValuesListener[] listeners;
	private static StandardListeners SL;
	
	public static void registerListeners() {
		SL = new StandardListeners();		
	}
	
	public static StandardListeners getListeners() {
		return SL;
	}
	
	private StandardListeners() {
		listeners = new ValuesListener[] 
				{ 
					new VolumeListener() 
				};
		
		for(ValuesListener listener : listeners)
			ValuesManager.registerListener(listener);
	}
	
	class VolumeListener implements ValuesListener {

		@Override
		public void valueSet(Entry<String, String> value) {

			if(value.getKey().equalsIgnoreCase("Master")) {
				
				
				
			}
			
			else if(value.getKey().equalsIgnoreCase("Music")) {
			
				MediaManager.setVolume(Float.parseFloat(value.getValue()) * (Float.parseFloat(ValuesManager.getValue("Master")) / 100));
			
			}
			
			else if(value.getKey().equalsIgnoreCase("MuteSound")) {
				
				if(value.getValue() == "true")
					MediaManager.muteAll();
				
			}
			
		}
		
	}
	
}
