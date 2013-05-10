package me.igwb.Excavor.Media;

public enum RegisteredMedia {

	ExcavorTheme ("/resources/Excavor-MainTheme(WIP).wav", "ExcavorTheme", MediaType.Music);
	
	private String path;
	private String mediaName;
	private MediaType type;
	
	public MediaType getType() {
		return type;
	}

	public void setType(MediaType type) {
		this.type = type;
	}
	
	public static RegisteredMedia getMedia(String mediaName) {
		for(RegisteredMedia m : RegisteredMedia.getAll())
			if(m.getMediaName().equalsIgnoreCase(mediaName))
				return m;
		
		return null;
	}

	RegisteredMedia(String path, String mediaName, MediaType type) {
		this.path = path;
		this.mediaName = mediaName;
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public static RegisteredMedia[] getAll() {
		RegisteredMedia[] rm = new RegisteredMedia[]
				{
				 ExcavorTheme
				};
		
		return rm;
	}

	public String getMediaName() {
		return mediaName;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}	
}
