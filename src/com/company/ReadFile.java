package MemoryAllocationProject;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class ReadFile {

	private String path;

	public ReadFile(String path) {
		this.path = path;
	}

	int readLines() throws IOException {
		FileReader a = new FileReader(path);
		BufferedReader b = new BufferedReader(a);
		
		String aLine;
		int numberOfLines = 0;
		
		while ( ( aLine = b.readLine( ) ) != null ) {
			numberOfLines++;
			}
		
		b.close();
		
		return numberOfLines;
	}

	public String[] OpenFile() throws IOException {
		FileReader a = new FileReader(path);
		BufferedReader b = new BufferedReader(a);

		int lineCount = readLines();
		String[] data = new String[lineCount];
		for (int i = 0; i < lineCount; i++) {
			data[i] = b.readLine();
		}

		b.close();
		return data;
	}

}
