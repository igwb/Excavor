package me.igwb.Excavor.Media;

import java.util.*;
import javax.sound.sampled.*;
import resources.ResourceLoader;

public class MediaManager {

	static Map<String, Clip> Clips = new HashMap<String, Clip>();
	
	public static void initialize() {
		
		try {

		for(RegisteredMedia m : RegisteredMedia.getAll()) {
			String path = m.getPath();
			
			if(path.endsWith(".wav") || path.endsWith(".avi"))
				Clips.put(m.getMediaName(), ResourceLoader.getAudio(path));
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Clip playClip(String name, boolean loop){
		Clip c = Clips.get(name);
		
		if(c == null) return null;
		
		c.loop(loop ? Clip.LOOP_CONTINUOUSLY : 0);
		
		return c;
	}
	
	public static Clip stopClip(String name) {
		Clip c = Clips.get(name);
		c.stop();
		
		return c;
	}
	
	public static void setVolume(String name, float decibel) {
		Clip c = Clips.get(name);
		
		if(c == null) return;
		
		FloatControl volume = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);

		decibel -= 50;
		
		volume.setValue((decibel / 100) * 12);
	}
	
	public static void setVolume(float decibel) {

		for(Clip c : Clips.values()) {
			FloatControl volume = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);

			decibel -= 50;
		
			volume.setValue((decibel / 100) * 12);
		}
	}
	
	public static Clip getClip(String name) {
		Clip c = Clips.get(name);
		return c;
	}
	
	private static ArrayList<Clip> Paused = new ArrayList<Clip>();
	public static void pauseAll() {
		for(Clip c : Clips.values()) {			
			if(c.isRunning() && c != null) {
				c.stop();
				Paused.add(c);
			}
		}
	}
	
	public static void resumeAll() {
		for(Clip c : Paused) {			
			c.start();
		}
		Paused.clear();
	}
}
