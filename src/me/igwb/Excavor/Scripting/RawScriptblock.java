package me.igwb.Excavor.Scripting;

import java.util.ArrayList;

public class RawScriptblock {

	public RawScriptblock() {
		
	}
	
	public String name = "", input = "";
	
	public boolean complete = false;
	
	public Scriptblock getScriptblock() {
		
		Scriptblock superBlock = new Scriptblock();
		superBlock.setName(name);
		
		ArrayList<Scriptblock> childs = new ArrayList<Scriptblock>();
		ArrayList<Command> commands = new ArrayList<Command>();
		
		char[] charIn = input.toCharArray();
		
		for(int i = 0; i < charIn.length; i++)		
			if(!Character.isWhitespace(charIn[i])) {
				
				if(charIn[i] == '{') {

					int deep = 0;
					
					RawScriptblock raw = new RawScriptblock();
					
					for(int j = i; charIn[i] != '}' && deep >= 0; i++) {
						
						if(charIn[i] == '{')
							deep++;
						
						else if(charIn[i] == '}')
							deep--;
						
						else
							raw.input += charIn[i];
					}
				}				
			}
		
		return null;
	}
	
}
