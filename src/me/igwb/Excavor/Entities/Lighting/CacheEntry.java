package me.igwb.Excavor.Entities.Lighting;

public class CacheEntry {
	
	public RGBColor dst = new RGBColor(), src = new RGBColor();
	
	@Override
	public boolean equals(Object obj) {			
		if(obj instanceof CacheEntry) {
			CacheEntry entry = (CacheEntry) obj;
			if(dst.equals(entry.dst) && src.equals(entry.src))
				return true;			
		}			
		return false;
	}
	
	public boolean equals(CacheEntry entry) {
		if(dst.equals(entry.dst) && src.equals(entry.src))
			return true;
		else
			return false;
	}
}