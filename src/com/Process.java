package com.company;

import java.util.*;

public class Process {

	private String name;
	private int size;

	public Process() {
		name = "";
		size = 0;
	}

	public Process(String name, int size) {
		this.name = name;
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public int getSize() {
		return size;
	}
	
	
	@Override
	public String toString() {
		return "Process [name=" + name + ", size=" + size + "]";
	}

	public static void main(String[]args) {
		ArrayList<Process> testing = new ArrayList<Process>();
		testing.add(new Process("A", 100));
		testing.add(new Process("B", 120));
		testing.add(new Process("C", 140));
		testing.add(new Process("D", 160));
		testing.add(new Process("E", 180));
		
		System.out.println(testing);
			
		testing.remove(2);							//can remove from anywhere
		System.out.println(testing);		
		
		testing.add(2, new Process("C", 140));		//can specify where to add
		System.out.println(testing);
		
		System.out.println(testing.get(4));			//can specify which particular element you want
		
		System.out.println(testing.size());			//how many elements are in the list
		
	}
}
