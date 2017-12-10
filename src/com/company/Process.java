package com.company;

import java.util.*;

public class Process extends  MemoryAllocation {

	private int processNumber;
	private int arrivalTime;
	private int finishTime;
	private int runTime;
	protected int memorySizeNeeded;

	public Process(int  processNumber,int arrivalTime, int size, int finishTime) {
		init( processNumber, arrivalTime, size, finishTime);
	}

    public void init(int  processNumber, int arrivalTime, int size, int finishTime) {
        this. processNumber =  processNumber;
        this.arrivalTime = arrivalTime;
        this.memorySizeNeeded = size;
        this.finishTime = finishTime;
    }

	public int getProcessNumber() {
		return  processNumber;
	}

    public int getArrivalTime() {
		return arrivalTime;
	}

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getFinishTime() {
		return finishTime;
	}

	public int getMemorySizeNeeded() {
        return memorySizeNeeded;
    }

    @Override
    public String toString() {
        return "Process{" +
                " processNumber=" +  processNumber +
                ", arrivalTime=" + arrivalTime +
                ", finishTime=" + finishTime +
                ", memorySizeNeeded=" + memorySizeNeeded +
                "} " + super.toString();
    }

    public static void main(String[]args) {
		ArrayList<MemoryAllocation> testing = new ArrayList<MemoryAllocation>();
		testing.add(new Process(0, 0, 131, 4));
		testing.add(new Process(1, 2, 120, 6));
		testing.add(new Process(2, 5, 58, 12));
		testing.add(new Process(3, 9, 107, 14));
		testing.add(new Process(4, 13, 82, 16));
		
		Process k = new Process(5, 12, 4, 14);
		
		
		System.out.println(testing);
			
		testing.remove(2);							//can remove from anywhere
		System.out.println(testing);		
		
		testing.add(2,k);		//can specify where to add
		System.out.println(testing);
		
		System.out.println(testing.get(4));			//can specify which particular element you want
		
		System.out.println(testing.size());			//how many elements are in the list

		List<MemoryAllocation> memory = new ArrayList<>();
		memory.add(new MemoryAllocation());
		memory.add(new Process(6, 13, 82, 16));
		Process p = (Process) memory.get(1);
		System.out.println(p);
	}
}
