package com.company;

import java.util.*;

public class Process {

	private String name;
	private int arrivalTime;
	private int size;
	private int finishTime;

	public Process() {
		name = "";
		arrivalTime = 0;
		size = 0;
		finishTime = 0;
	}

	public Process(String name, int arrivalTime, int size, int finishTime) {
		this.name = name;
		this.arrivalTime = arrivalTime;
		this.size = size;
		this.finishTime = finishTime;
	}

	public String getName() {
		return name;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getFinishTime() {
		return finishTime;
	}

	@Override
	public String toString() {
		return "Process [name=" + name + ", arrivalTime=" + arrivalTime + ", size=" + size + ", finishTime="
				+ finishTime + "]";
	}

	public static void main(String[]args) {
		ArrayList<Process> testing = new ArrayList<Process>();
		testing.add(new Process("A", 0, 131, 4));
		testing.add(new Process("B", 2, 120, 4));
		testing.add(new Process("C", 5, 58, 7));
		testing.add(new Process("D", 9, 107, 5));
		testing.add(new Process("E", 13, 82, 3));
		
		System.out.println(testing);
			
		testing.remove(2);							//can remove from anywhere
		System.out.println(testing);		
		
		testing.add(2, new Process("C", 5, 140, 7));		//can specify where to add
		System.out.println(testing);
		
		System.out.println(testing.get(4));			//can specify which particular element you want
		
		System.out.println(testing.size());			//how many elements are in the list
		
	}

}
