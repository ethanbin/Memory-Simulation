package com.company;

import java.util.*;

public class EqualSizedFixedPartition extends Memory {
	private final int PARTITIONSIZE = 128;
	private final int MAXPARTITIONS = 8;
	private int partitionCount = 0;
	ArrayList<Process> jobList;

	EqualSizedFixedPartition(ArrayList<Process> jobList) {
		super();
		this.jobList = jobList;
	}

	public void addProcesses() {
		for (int i = 0; i < jobList.size(); i++) { // going through every process
			//System.out.print("  " + jobList.get(i).getName() + "\t    " + jobList.get(i).getArrivalTime() + "\t\t"
					//+ jobList.get(i).getSize());
			if (canPlace(jobList.get(i))) {
				memoryList.add(jobList.get(i));
				memoryList.get(memoryList.size()).setMemorySize(jobList.get(i).getSize());
				//System.out.print("MB\t\tALLOCATED\t");
				partitionCount++;
				calculateInternalFragmentation(jobList.get(i));
			} else {
				//System.out.print("MB\t\tWAITING  \t");
				allocationFail++;
			}
			//System.out.print(jobList.get(i).getFinishTime() + "\n");
			
		}
		
		for (int m = 0; m < memoryList.size(); m++) {
			System.out.println(memoryList.get(m));
		}
		
	}

	public boolean canPlace(Process a) {
		return partitionCount < MAXPARTITIONS && a.getSize() <= PARTITIONSIZE; // if there is a partition left AND it
																				// fits into the partition
	}

	public boolean isProcessDone(Process a) {
		boolean done = false;
		if (currTime <= a.getArrivalTime() + a.getFinishTime()) { // if the process is done, we will release it
			done = true;
			a = null;
		}
		return done;
	}

	public void removeProcess(int pos) {
		memoryList.remove(pos);
	}

	public void calculateInternalFragmentation(Process p) {
		internalFragmentation += PARTITIONSIZE - p.getSize();
	}

	public void calculateExternalFragmentation(Process P) {
		// fixed sized partitions don't have external fragmentation
	}

	public void display() {
		System.out.println("Process\tArrival Time\tProcess Size \tStatus\t\tFinish Time");
		addProcesses();
		System.out.println("Allocation fails: " + allocationFail);
		System.out.println("Average Internal Fragmentation: " + internalFragmentation / jobList.size() + "MB");
		System.out.println("Average External Freagmentation: " + externalFragmentation / jobList.size() + "MB");
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

		EqualSizedFixedPartition a = new EqualSizedFixedPartition(jobList);
		a.display();

	}
}
