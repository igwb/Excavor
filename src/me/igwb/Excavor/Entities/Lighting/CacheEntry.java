package me.igwb.Excavor.Entities.Lighting;

public class CacheEntry {
	
	private RGBColor dst = new RGBColor(0, 0, 0, 0), src = new RGBColor(0, 0, 0, 0);
	
	public RGBColor getDst() {
		return dst;
	}

	public void setDst(RGBColor dst) {
		this.dst = dst;
	}

	public RGBColor getSrc() {
		return src;
	}

	public void setSrc(RGBColor src) {
		this.src = src;
	}

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