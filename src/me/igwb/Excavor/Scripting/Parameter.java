package me.igwb.Excavor.Scripting;

import java.util.ArrayList;

public class Parameter {
	
	public ParameterRegion[] regions;

	public String[] getAllValues() {
		
		ArrayList<String> list = new ArrayList<String>();
		
		for(ParameterRegion reg : regions)
			for(String value : reg.value)
				list.add(value);
		
		
		return list.toArray(new String[1]);
	}
	
}
