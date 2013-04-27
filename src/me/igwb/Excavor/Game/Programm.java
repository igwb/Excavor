package me.igwb.Excavor.Game;

public class Programm {

	static Core GC;
	public static void main(String[] args) {

		GC = new Core();
		
		GC.initialize();
	}

	public static Core getCore() {
		return GC;
	}
	
}
