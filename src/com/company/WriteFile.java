package MemoryAllocationProject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteFile {
	private String path;		//location of the file
	private boolean appendToFile = false; // this is by default; means you don't want to append but want to erase
											// everything in the file

	public WriteFile(String path) {
		this.path = path;
	}
	
	public WriteFile(String path,boolean appendToFile) {
		this.path = path;
		this.appendToFile = appendToFile;
	}
	
	public void write(String text) throws IOException{
		FileWriter a = new FileWriter(path, appendToFile);	//makes sure that we are opening the right file
		PrintWriter b = new PrintWriter(a);		//translates the FileWriter (in bytes) to plain text
		
		b.println(text);	//to add text to the file
		b.close();
	}
	
}
