package com.company;

import java.util.*;

public class EqualSizedFixedPartition {

	private ArrayList<Process> p;
	private static final int PARTITIONSIZE = 128;
	private static final Process[] MM = new Process[8]; // a total of 1024MB
	private int currTime = 0; // keeps track of time
	private int partitionCount = 0;
	private int allocationFail = 0;
	private int internalFragmentation = 0; // within each block
	private int externalFragmentation = 0;

	EqualSizedFixedPartition(ArrayList<Process> p) {
		this.p = p;
	}

	public void insertToMM() {
		for (int i = 0; i < p.size(); i++) { // going through every process
			if (canPlace(p.get(i))) {
				MM[partitionCount] = p.get(i);
				System.out.println("  " + p.get(i).getName() + "\t    " + p.get(i).getArrivalTime() + "\t\t"
						+ p.get(i).getSize() + "MB\t\tALLOCATED\t" + p.get(i).getFinishTime());
				partitionCount++;
				internalFragmentation += PARTITIONSIZE - p.get(i).getSize();
			} else {
				System.out.println("  " + p.get(i).getName() + "\t    " + p.get(i).getArrivalTime() + "\t\t"
						+ p.get(i).getSize() + "MB\t\tWAITING  \t" + p.get(i).getFinishTime());
				allocationFail++;
			}
		}
	}

	public boolean canPlace(Process a) {
		return partitionCount < MM.length && a.getSize() <= PARTITIONSIZE; // if there is a partition left AND it fits
																			// into the partition
	}

	public boolean isProcessDone(Process a) {
		boolean done = false;
		if (currTime <= a.getArrivalTime() + a.getFinishTime()) { // if the process is done, we will release it
			done = true;
			a = null;
		}
		return done;
	}

	public void display() {
		System.out.println("Process\tArrival Time\tProcess Size \tStatus\t\tFinish Time");
		insertToMM();
		System.out.println("Allocation fails: " + allocationFail);
		System.out.println("Average Internal Fragmentation: " + internalFragmentation/p.size() + "MB");
		System.out.println("Average External Freagmentation: " + externalFragmentation/p.size() + "MB");
	}

	public static void main(String[] args) {
		ArrayList<Process> testing = new ArrayList<Process>();
		testing.add(new Process("A", 0, 131, 4));
		testing.add(new Process("B", 2, 120, 4));
		testing.add(new Process("C", 5, 158, 7));
		testing.add(new Process("D", 9, 107, 5));
		testing.add(new Process("E", 13, 82, 3));
		testing.add(new Process("F", 17, 127, 1));
		testing.add(new Process("G", 17, 43, 8));
		testing.add(new Process("H", 20, 77, 6));
		testing.add(new Process("I", 24, 109, 2));
		testing.add(new Process("J", 26, 90, 3));
		testing.add(new Process("K", 29, 190, 7));
		testing.add(new Process("L", 31, 24, 2));

		EqualSizedFixedPartition a = new EqualSizedFixedPartition(testing);
		a.display();
	}
}
