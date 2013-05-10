package me.igwb.Excavor.Scripting;

import java.util.ArrayList;

public class RawScriptblock {

	public RawScriptblock() {
		
	}
	
	public String name = "", input = "";
	
	public Scriptblock parent;
	
	public Scriptblock getScriptblock() {
		
		Scriptblock superBlock = new Scriptblock();
		superBlock.setName(name);
		
		ArrayList<Scriptblock> childs = new ArrayList<Scriptblock>();
		ArrayList<Command> commands = new ArrayList<Command>();
		
		char[] charIn = input.toCharArray();
		
		String Input = "";
		
		for(int i = 0; i < charIn.length; i++) {
				
				if(charIn[i] == '{') {

					int deep = 0;
					
					i++;
					
					RawScriptblock raw = new RawScriptblock();
					raw.name = Input.split(";")[Input.split(";").length - 1];
					Input = Input.substring(0, Input.length() - raw.name.length());
					raw.parent = superBlock;
					
					for(; charIn[i] != '}' || deep > 0; i++) {
						
						if(charIn[i] == '{')
							deep++;
						
						else if(charIn[i] == '}')
							deep--;

						raw.input += charIn[i];
					}
					
					childs.add(raw.getScriptblock());
					
				} else
					Input += charIn[i];
		}
		
		for(String s : Input.split(";"))
			commands.add(new RawCommand(s).getCommand());
		
		superBlock.setChilds(childs);
		superBlock.setCommands(commands);
		
		return superBlock;
	}
	
}
