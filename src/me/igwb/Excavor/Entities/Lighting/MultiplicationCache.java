package me.igwb.Excavor.Entities.Lighting;

import java.util.ArrayList;
import java.util.HashMap;

public class MultiplicationCache {
	
	private HashMap<CacheEntry, RGBColor> cache = new HashMap<CacheEntry, RGBColor>(); 
	
	public MultiplicationCache() {
		cache.clear();
		//cache.add(new CacheEntry());
	}
	
	public RGBColor multiply(RGBColor dst, RGBColor src) {		
		CacheEntry entry = new CacheEntry();
		
		entry.dst = dst;
		entry.src = src;		

		RGBColor color = cache.get(entry);
		
		if(color == null) {
			color = entry.src.multiply(entry.dst);
			cache.put(entry, color);
		}
		
		return color;
	}	
}
