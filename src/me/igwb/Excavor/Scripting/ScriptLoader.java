package me.igwb.Excavor.Scripting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;

import me.igwb.Excavor.UI.Conversation;

public class ScriptLoader {

	public static Conversation loadConversation(URL url) {
		
		String input = "";
		
		try {
			File file = new File(url.getPath());
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			while(reader.ready()) {
				input += reader.readLine();
			}
			
			reader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return loadConversation(input.toCharArray());
	}
	
	public static Conversation loadConversation(char[] input) {
		
		RawScriptblock superBlock = new RawScriptblock();
		superBlock.name = "";
		
		boolean skipwhite = false;
		
		for(int i = 0; i < input.length; i++)			
			if(!Character.isWhitespace(input[i]) || skipwhite) {
			
				if(input[i] == '"')
					skipwhite = !skipwhite;
				
				superBlock.input += input[i];
				
			}
		
		Scriptblock sb = superBlock.getScriptblock().getChild("Conversation");
		
		return new Conversation(sb);
	}
}
