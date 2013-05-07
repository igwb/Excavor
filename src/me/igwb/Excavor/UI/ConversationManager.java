package me.igwb.Excavor.UI;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import resources.ResourceLoader;

import me.igwb.Excavor.Scripting.ScriptLoader;

public class ConversationManager {

	private static ArrayList<Conversation> conversations;
	
	private static Conversation activeConversation;
	
	public static void initialize(String loadFrom) throws Exception {
		
		try {
		
		conversations = new ArrayList<Conversation>();
		
		File d = new File(System.getProperty("user.dir") + "/" + loadFrom);
		
		if(d.exists())
			for(String s : d.list())
				if(s.endsWith(".script"))
					conversations.add(ScriptLoader.loadConversation(ResourceLoader.getURL(loadFrom + "/" + s, true)));
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void update() {

		if(activeConversation == null)
			return;
		
		activeConversation.loop();
		
		if(activeConversation.getConversationEnd())
			activeConversation = null;
	}
	
	public static void render(Graphics g) {
		if(activeConversation == null)
			return;
		
		activeConversation.render(g);		
	}
	
	public static void startConversation(String name) {
		
		for(int i = 0; i < conversations.size(); i++) {
			if(conversations.get(i).getName().equalsIgnoreCase(name)) {
				activeConversation = null;
				activeConversation = conversations.get(i).Begin();
				break;
			}
		}
	}
	
	public static boolean allowUpdate() {
		return activeConversation == null;
	}

	public static boolean keyPressed(KeyEvent arg0) {
		
		if(activeConversation != null)
			activeConversation.keyPressed(arg0);
		
		return allowUpdate();
	}
}
