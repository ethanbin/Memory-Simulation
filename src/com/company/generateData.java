package com.company;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class generateData {

	public static void main(String[] args) throws IOException{
		String file = "testing.txt";
		
		try {
			ReadFile a = new ReadFile(file);
			String [] lines = a.OpenFile();
			
			
			for(int i = 0; i < lines.length; i++) {
				System.out.println(lines[i]);
			}
			
			WriteFile b = new WriteFile(file, false);
			b.write("This is a test.....");
			
			ReadFile c = new ReadFile(file);
			String [] line = a.OpenFile();
			
			for(int i = 0; i < line.length; i++) {
				System.out.println(line[i]);
			}
		}
		catch (IOException e) {
			System.out.print(e.getMessage());
		}
	}
}