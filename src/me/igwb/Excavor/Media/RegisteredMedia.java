package me.igwb.Excavor.Media;

public enum RegisteredMedia {

	ExcavorTheme ("/resources/Excavor-MainTheme(WIP).wav", "ExcavorTheme");
	
	private String path;
	private String mediaName;
	
	RegisteredMedia(String path, String mediaName) {
		this.path = path;
		this.mediaName = mediaName;
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
