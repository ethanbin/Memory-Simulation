package com.company;

import java.util.*;

public class Process extends  MemoryAllocation{

	private String name;
	private int arrivalTime;
	private int finishTime;
	protected int memorySizeNeeded;
    
	public Process(String name, int arrivalTime, int size, int finishTime) {
		init(name, arrivalTime, size, finishTime);
	}

    public void init(String name, int arrivalTime, int size, int finishTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.memorySizeNeeded = size;
        this.finishTime = finishTime;
    }

	public String getName() {
		return name;
	}

    public void setName(String name) {
        this.name = name;
    }

    public int getArrivalTime() {
		return arrivalTime;
	}

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public int getFinishTime() {
		return finishTime;
	}

    public int getMemorySizeNeeded() {
        return memorySizeNeeded;
    }

    public void setMemorySizeNeeded(int memorySizeNeeded) {
        this.memorySizeNeeded = memorySizeNeeded;
    }

    @Override
    public String toString() {
        return "Process{" +
                "name='" + name + '\'' +
                ", arrivalTime=" + arrivalTime +
                ", finishTime=" + finishTime +
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
