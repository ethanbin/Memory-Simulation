package com.company;

import java.util.*;

public class EqualSizedFixedPartition extends FixedMemory {
	protected final int DEFAULT_PARTITION_SIZE = 128;
    protected final int MAX_PARTITIONS = 8;
    protected int partitionSize;
    protected int partitionCount = 0;
	//ArrayList<Process> jobList;

	EqualSizedFixedPartition() {
		super();
		init(DEFAULT_MEMORY_SIZE);
	}

	@Override
    protected void init(int size){
	    memoryList = new ArrayList<>();
        int partitionSize = size/ MAX_PARTITIONS;
	    for (int i = 0; i < MAX_PARTITIONS; i++){
            memoryList.add(new MemoryAllocation(partitionSize));
        }
        fragmentations = new ArrayList<>();
    }


	public boolean isProcessDone(Process a) {
		boolean done = false;
		if (currentTime <= a.getArrivalTime() + a.getFinishTime()) { // if the process is done, we will release it
			done = true;
			a = null;
		}
		return done;
	}

	/*
	public void removeProcess(int pos) {
		memoryList.remove(pos);
	}
*/

	public void display() {
		System.out.println("Process\tArrival Time\tProcess Size \tStatus\t\tFinish Time");
		//addProcess();
		System.out.println("Allocation fails: " + allocationFailures);
		//System.out.println("Average Internal Fragmentation: " + internalFragmentation / jobList.size() + "MB");
		//System.out.println("Average External Freagmentation: " + externalFragmentation / jobList.size() + "MB");
	}

	public static void main(String[] args) {
		ArrayList<Process> jobList = new ArrayList<>();

		jobList.add(new Process("A", 0, 131, 4));
		jobList.add(new Process("B", 2, 120, 4));
		jobList.add(new Process("C", 5, 158, 7));
		jobList.add(new Process("D", 9, 107, 5));
		jobList.add(new Process("E", 13, 82, 3));
		jobList.add(new Process("F", 17, 127, 1));
		jobList.add(new Process("G", 17, 43, 8));
		jobList.add(new Process("H", 20, 77, 6));
		jobList.add(new Process("I", 24, 109, 2));
		jobList.add(new Process("J", 26, 90, 3));
		jobList.add(new Process("K", 29, 190, 7));
		jobList.add(new Process("L", 31, 24, 2));

		EqualSizedFixedPartition a = new EqualSizedFixedPartition();
		for (Process p : jobList)
    		a.addProcess(p);
		a.display();

	}
}
