package com.company;

import java.util.*;

public class Process extends  MemoryAllocation {

	private String name;
	private int arrivalTime;
	private int finishTime;
	private int runTime;
	protected int memorySizeNeeded;

	public Process(String name, int size, int arrivalTime, int finishTime) {
		init(name, size, arrivalTime, finishTime);
	}

    public void init(String name, int size, int arrivalTime, int finishTime) {
        this.name = name;
        this.memorySizeNeeded = size;
        this.arrivalTime = arrivalTime;
        this.finishTime = finishTime;
    }

	public String getName() {
		return name;
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
                "name='" + name + '\'' +
                ", arrivalTime=" + arrivalTime +
                ", finishTime=" + finishTime +
                ", memorySizeNeeded=" + memorySizeNeeded +
                "} " + super.toString();
    }

    public static void main(String[]args) {
		ArrayList<MemoryAllocation> testing = new ArrayList<MemoryAllocation>();
		testing.add(new Process("A", 0, 131, 4));
		testing.add(new Process("B", 2, 120, 4));
		testing.add(new Process("C", 5, 58, 7));
		testing.add(new Process("D", 9, 107, 5));
		testing.add(new Process("E", 13, 82, 3));
		
		Process k = new Process("K", 12, 4, 2);
		
		
		System.out.println(testing);
			
		testing.remove(2);							//can remove from anywhere
		System.out.println(testing);		
		
		testing.add(2,k);		//can specify where to add
		System.out.println(testing);
		
		System.out.println(testing.get(4));			//can specify which particular element you want
		
		System.out.println(testing.size());			//how many elements are in the list

		List<MemoryAllocation> memory = new ArrayList<>();
		memory.add(new MemoryAllocation());
		memory.add(new Process("E", 13, 82, 3));
		Process p = (Process) memory.get(1);
		System.out.println(p);
	}

}
