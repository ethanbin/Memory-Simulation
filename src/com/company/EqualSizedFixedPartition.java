package com.company;

import java.util.*;

public class EqualSizedFixedPartition extends FixedMemory{
	private final int PARTITIONSIZE = 128;
	private final int MAXPARTITIONS = 8;
	private int partitionCount = 0;
	ArrayList<Process> jobList;

	EqualSizedFixedPartition(ArrayList<Process> jobList) {
		this.jobList = jobList;
		init(DEFAULT_MEMORY_SIZE);
	}
	
	public void init(int size) {
		memoryList = new ArrayList<MemoryAllocation>(); 
		for (int i = 0; i < jobList.size(); i++) {		//letting us know if it's too big because default says that it isn't
			isTooBig(jobList.get(i));
		}
	}	
 
	public void fillUpMemory() {
		for (int i = 0; i < jobList.size(); i++) { // going through every process
			if (canPlace(jobList.get(i))) {
				memoryList.add(jobList.get(i));
				//((Process)memoryList.get(i)).setStartingPositionInMemory(10);
				partitionCount++;
				calculateInternalFragmentation(PARTITIONSIZE, jobList.get(i));
			} else {
				allocationFailures++;
			}			
		}
		
		for (int m = 0; m < memoryList.size(); m++) {
			System.out.println(memoryList.get(m));		
		}
	}
	
	public void removeFinishedProcesses() {
		
	}

	public void isTooBig(Process a) {
		if (a.getMemorySizeNeeded() > PARTITIONSIZE) 
			a.setTooBig(true);
	}
	
	public boolean canPlace(Process a) {
		return partitionCount < MAXPARTITIONS && !a.isTooBig(); // if there is a partition left AND it
																				// fits into the partition
	}

	public boolean isProcessDone(Process a) {
		boolean done = false;
		/*if (currentTime <= a.getArrivalTime() + a.getFinishTime()) { // if the process is done, we will release it
			done = true;
			a = null;
		}*/
		return done;
	}

	public void calculateInternalFragmentation(int partitionSize, Process p) {
		if(partitionSize - p.getMemorySizeNeeded() != 0)
			internalFragmentationCount++;
		internalFragmentation += partitionSize - p.getMemorySizeNeeded();
	}

	public void calculateExternalFragmentation(Process P) {
		// fixed sized partitions don't have external fragmentation
	}

	public void display() {
		System.out.println("Process\tArrival Time\tProcess Size \tStatus\t\tFinish Time");
		fillUpMemory();
		System.out.println(jobList);
		System.out.println("Allocation fails: " + allocationFailures);
		System.out.println(internalFragmentationCount);
		System.out.println("Average Internal Fragmentation: " + internalFragmentation  + "MB");
		System.out.println("Average External Fragmentation: " + externalFragmentation / jobList.size() + "MB");
	}

	public static void main(String[] args) {
		ArrayList<Process> jobList = new ArrayList<>();

		jobList.add(new Process("A", 0, 131, 4, false));
		jobList.add(new Process("B", 2, 120, 4, false));
		jobList.add(new Process("C", 5, 158, 7, false));
		jobList.add(new Process("D", 9, 107, 5, false));
		jobList.add(new Process("E", 13, 82, 3, false));
		jobList.add(new Process("F", 17, 127, 1, false));
		jobList.add(new Process("G", 17, 43, 8, false));
		jobList.add(new Process("H", 20, 77, 6, false));
		jobList.add(new Process("I", 24, 109, 2, false));
		jobList.add(new Process("J", 26, 90, 3, false));
		jobList.add(new Process("K", 29, 190, 7, false));
		jobList.add(new Process("L", 31, 24, 2, false));

		EqualSizedFixedPartition a = new EqualSizedFixedPartition(jobList);
		a.display();

	}
}
