package com.company;

import java.util.*;

public class EqualSizedFixedPartition extends FixedMemory {
	private final static int PARTITIONSIZE = 128;
	private final static int MAXPARTITIONS = 8;
	private static int partitionCount = 0;
	// ArrayList<Process> jobList;

	EqualSizedFixedPartition() {
		super();
		init(DEFAULT_MEMORY_SIZE);
	}

	@Override
	protected void init(int size) {
		memoryList = new ArrayList<>();
		int partitionSize = size / MAXPARTITIONS;
		for (int i = 0; i < MAXPARTITIONS; i++) {
			memoryList.add(new MemoryAllocation(partitionSize));
		}
	}

	public void calculateInternalFragmentation(Process p) {
		internalFragmentation += PARTITIONSIZE - p.getMemorySizeNeeded();
	}

	public void calculateExternalFragmentation(Process P) {
		// fixed sized partitions don't have external fragmentation
	}

	public void display() {
		System.out.println("Process\tArrival Time\tProcess Size \tStatus\t\tFinish Time");
		System.out.println(memoryList);
		System.out.println("Allocation fails: " + allocationFailures);
		System.out.println("Average Internal Fragmentation: " + internalFragmentation  + "MB");
		System.out.println("Average External Fragmentation: " + externalFragmentation + "MB");
	}

	public static void main(String[] args) {
		ArrayList<Process> jobList = new ArrayList<>();

		jobList.add(new Process("A", 0, 131, 4, false));
		jobList.add(new Process("B", 2, 120, 4, false));
		jobList.add(new Process("C", 5, 158, 7, false));
		jobList.add(new Process("D", 9, 107, 20, false));
		jobList.add(new Process("E", 13, 82, 3, false));
		jobList.add(new Process("F", 17, 127, 1, false));
		jobList.add(new Process("G", 17, 43, 10, false));
		jobList.add(new Process("H", 20, 77, 6, false));
		jobList.add(new Process("I", 24, 109, 5, false));
		jobList.add(new Process("J", 26, 90, 3, false));
		jobList.add(new Process("K", 29, 190, 7, false));
		jobList.add(new Process("L", 31, 24, 2, false));
		jobList.add(new Process("M", 31, 123, 2, false));
		jobList.add(new Process("N", 37, 104, 2, false));
		jobList.add(new Process("O", 39, 53, 32, false));
		jobList.add(new Process("P", 41, 91, 12, false));
		jobList.add(new Process("Q", 51, 116, 1, false));

		EqualSizedFixedPartition a = new EqualSizedFixedPartition();
		//while (a.processCount < jobList.size()) {
			// fill up memory
			for (Process p : jobList) {
				if (a.partitionCount > MAXPARTITIONS) { // is there still room
					a.allocationFailures++;
				} else if (p.getMemorySizeNeeded() > PARTITIONSIZE) { // is it tooBig?
					a.processCount++; // because it will never fit
					a.allocationFailures++;
					p.setHasBeenAllocated(true);
				} else if (!p.isHasBeenAllocated()) { //it was not allocated before
					a.addProcesses(p,a.partitionCount);
					a.internalCount++;
					a.processCount++;
					partitionCount++;
					p.setHasBeenAllocated(true);
				}
			}
			a.display();
			System.out.println("\n" + a.currentTime);
			System.out.println(a.processCount);
			System.out.println(a.internalCount);
			System.out.println(a.partitionCount + "\n");
			
			//now that its full, remove all the processes that should be done
			a.removeProcesses();
			a.display();
		}
	//}
}
