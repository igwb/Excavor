package me.igwb.Excavor.Scripting;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;

import me.igwb.Excavor.UI.Conversation;

public class ScriptLoader {

	public static Conversation loadConversation(URL path) {
		
		String input = "";
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path.getPath()));
			
			while(reader.ready()) {
				input += reader.readLine();
			}
			
			reader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return loadConversation(input);
	}
	
	public static Conversation loadConversation(String input) {
		
		char[] charIn = input.toCharArray();
		
		RawScriptblock superBlock = new RawScriptblock();
		superBlock.name = "";
		
		boolean skipwhite = false;
		
		for(int i = 0; i < charIn.length; i++)			
			if(!Character.isWhitespace(charIn[i]) || skipwhite) {
			
				if(charIn[i] == '"')
					skipwhite = !skipwhite;
				
				superBlock.input += charIn[i];
				
			}
		
		Scriptblock sb = superBlock.getScriptblock().getChild("Conversation");
		
		return new Conversation(sb);
	}
}
