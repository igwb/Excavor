package me.igwb.Excavor.Entities.Lighting;

import java.util.HashMap;

public class MultiplicationCache {
	
	private HashMap<CacheEntry, RGBColor> cache = new HashMap<CacheEntry, RGBColor>(); 
	
	public MultiplicationCache() {
		cache.clear();
		//cache.add(new CacheEntry());
	}
	
	public RGBColor multiply(RGBColor dst, RGBColor src) {		
		CacheEntry entry = new CacheEntry();
		
		entry.setDst(dst);
		entry.setSrc(src);
		
		RGBColor color = cache.get(entry);
		
		if(color == null) {
			color = entry.getSrc().multiply(entry.getDst());
			cache.put(entry, color);
		} else {
			System.out.println("FOUND SOMETHING!");
		}
		
		return color;
	}
}
