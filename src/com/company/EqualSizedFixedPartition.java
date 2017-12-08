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
}
