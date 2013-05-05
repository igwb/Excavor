package me.igwb.Excavor.Scripting;

import java.util.ArrayList;

public class Command {

	public String name = "";
	
	public String parameter = "";
	
	public CommandType type;
	
	public enum CommandType { Method, Variable };
	
	public Parameter getParameter() {
		
		ArrayList<ParameterRegion> regions = new ArrayList<ParameterRegion>();
		
		for(String s : parameter.split("&")) {
			ParameterRegion pr = new ParameterRegion();
			
			if(s.startsWith("[") && s.endsWith("]"))
				s = s.substring(1, s.length() - 1);
			
			pr.value = s.split(",");
			
			for(int i = 0; i < pr.value.length; i++) {
				if(pr.value[i].startsWith("\"") && pr.value[i].endsWith("\""))
					pr.value[i] = pr.value[i].substring(1, pr.value[i].length() - 1);
			}
			
			regions.add(pr);
		}
		
		Parameter param = new Parameter();
		param.regions = regions.toArray(new ParameterRegion[regions.size()]);
		
		return param;
	}
}
