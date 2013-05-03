package me.igwb.Excavor.UI;

import java.io.*;

public class ExceptionRecorder extends OutputStream {

	public ExceptionRecorder() {
		
	}
	
	@Override
	public void write(int arg0) throws IOException {

		DeveloperConsole.write(arg0);

	}

}
