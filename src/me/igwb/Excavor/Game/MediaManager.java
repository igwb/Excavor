package me.igwb.Excavor.Game;

import java.io.File;
import java.net.URL;
import java.util.*;

import javax.sound.sampled.*;

import resources.ResourceLoader;

public class MediaManager {

	static Map<String, Clip> Clips = new HashMap<String, Clip>();
	
	public static void initialize(URL url) {
		
		try {

		File d = new File(url.getPath());
		
		if(d.exists())
			for(String s : d.list())
				if(s.endsWith(".wav") || s.endsWith(".avi"))
					Clips.put(s, ResourceLoader.getAudio(new URL(url.toString() + "/" + s)));
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Clip playClip(String name, boolean loop){
		Clip c = Clips.get(name);
		
		if(c == null) return null;
		
		if(loop) c.loop(Clip.LOOP_CONTINUOUSLY);
		c.start();
		
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
}
