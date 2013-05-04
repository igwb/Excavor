package me.igwb.Excavor.Scripting;

import me.igwb.Excavor.UI.Conversation;

public class ScriptLoader {

	public static Conversation loadConversation(String input) {
		
		char[] charIn = input.toCharArray();
		
		RawScriptblock superBlock = new RawScriptblock();
		
		for(int i = 0; i < charIn.length; i++)			
			if(!Character.isWhitespace(charIn[i])) {
				
				if(charIn[i] == '{') {				
					
					if(!superBlock.name.equalsIgnoreCase("Conversation"))
						return null;
					
					boolean canFinish = true;
					boolean checkwhite = true;
					
					//loop 2 'yay'
					for(int j = i; i < charIn.length; i++) {						
						if(!Character.isWhitespace(charIn[i]) || !checkwhite) {
							
							if(charIn[i] ==  '"')
								checkwhite = !checkwhite;
							
							if(charIn[i] == '{')				
								canFinish = false;
							
							else if(charIn[i] == '}')
								if(canFinish)
									break;
								else
									canFinish = true;
							
							superBlock.input += charIn[i];
							
						}
						
					}
					superBlock.complete = true;
					break;
				}
				
				if(!superBlock.complete)
					superBlock.name += charIn[i];
				
			}
		
		return null;
	}
	
}
