package me.igwb.Excavor.Scripting;

import me.igwb.Excavor.Scripting.Command.CommandType;

public class RawCommand {

		public RawCommand(String input) {
			this.input = input;
		}
		
		private String input = "";
	
		public Command getCommand() {

			Command command = new Command();
			
			if(input.contains("(") && input.contains(")") && !input.contains("\\(") && !input.contains("\\)")) {
				
				command.type = CommandType.Method;
				
				char[] charIn = input.toCharArray();
				
				int i = 0;
				
				for(; charIn[i] != '('; i++)
					command.name += charIn[i];
						
				
				i++;
				
				for(; charIn[i] != ')'; i++)
					if(charIn[i] != '\\')
						command.parameter += charIn[i];
					else
						command.parameter += charIn[i++];
				
			} else if(input.contains("=")) {
				
				command.type = CommandType.Variable;
				
				char[] charIn = input.toCharArray();
				
				int i = 0;
				
				for(; charIn[i] != '='; i++)
					command.name += charIn[i];
				
				i++;
				
				for(; i < charIn.length; i++)
					if(charIn[i] != '\\')
						command.parameter += charIn[i];
					else
						command.parameter += charIn[i += 1];			
			} else
				return null;
			
			return command;
		}
}
