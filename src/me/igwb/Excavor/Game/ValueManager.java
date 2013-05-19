package me.igwb.Excavor.Game;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class ValuesManager {

	private static Map<String, String> values = new HashMap<String, String>();
	
	private static ArrayList<ValuesListener> listeners = new ArrayList<ValuesListener>();
	
	public static void registerListener(ValuesListener listener) {
		listeners.add(listener);
		
		Iterator<Entry<String, String>> iterator = values.entrySet().iterator();
		
		while(iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			
			listener.valueSet(entry);
		}
	}
	
	public static void deleteListener(ValuesListener listener) {
		listeners.remove(listener);
	}
	
	public static void setValue(String name, String value) {
		values.put(name, value);
		
		Iterator<Entry<String, String>> iterator = values.entrySet().iterator();
		
		while(iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			
			if(entry.getKey().equals(name)) {				
				for(ValuesListener listener : listeners)				
					listener.valueSet(entry);
				
				break;
			}
		}
	}
	
	public static String getValue(String name) {
		return values.get(name);
	}
	
	public static void loadValuesFrom(String path) {
		
		File file = new File(path);
		
		if(!file.exists())
			return;
		
		BufferedReader reader;
		
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		try {
			while(reader.ready()) {
				
				String line = reader.readLine();
				
				if(line.contains("=")) {				
					String[] input = line.split("=");				
					setValue(input[0], input[1]);				
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void clearValues() {
		values.clear();
	}
	
	public static void saveValuesTo(String path, boolean append) {
		
		File file = new File(path);
		
		if(!append)
			file.delete();
		
		if(!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		BufferedWriter writer;
		
		try {
			writer = new BufferedWriter(new FileWriter(file));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		try {
			for(Entry<String, String> entry : values.entrySet())
					writer.append(entry.getKey() + "=" + entry.getValue());
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
