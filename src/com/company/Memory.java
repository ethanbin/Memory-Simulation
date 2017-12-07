package com.company;

import java.util.List;

/**
 * Created by Ethan on 11/27/2017.
 */
public abstract class Memory {
    protected final static int DEFAULT_MEMORY_SIZE = 1024;
    protected List<MemoryAllocation> memoryList;
    protected int memorySize;
    protected double internalFragmentation = 0;
    protected int internalFragmentationCalculationCount = 0;
    protected double externalFragmentation = 0;
    protected int externalFragmentationCalculationCount = 0;
    protected int currentTime = 0;							// keeps track of time
	protected int allocationFailures = 0;

    protected abstract void init(int size);

    public abstract boolean addProcess(Process p);

    public boolean removeProcess(String processName) {
        for (int i = 0; i < memoryList.size(); i++){
            if (!Memory.isMemoryAllocationAProcess(memoryList.get(i)))
                continue;

            Process currentProc = (Process) memoryList.get(i);

            if (currentProc.getName().equals(processName)){
                memoryList.set(i, new MemoryAllocation(currentProc.getMemorySizeUsed(),
                        currentProc.getStartingPositionInMemory(),
                        currentProc.getEndingPositionInMemory()));
                calculateExternalFragmentation();
                return true;
            }
        }
        return false;
    }

    public boolean removeProcess(int startingPositionInMemory) {
        for (int i = 0; i < memoryList.size(); i++){
            if (!Memory.isMemoryAllocationAProcess(memoryList.get(i)))
                continue;

            Process currentProc = (Process) memoryList.get(i);

            if (currentProc.getStartingPositionInMemory() == startingPositionInMemory){
                memoryList.set(i, new MemoryAllocation(currentProc.getMemorySizeUsed(),
                        currentProc.getStartingPositionInMemory(),
                        currentProc.getEndingPositionInMemory()));
                calculateExternalFragmentation();
                return true;
            }
        }
        return false;
    }

    public abstract void calculateInternalFragmentation();

    /**
     * Calculates the current external fragmentation using
     * [(all free space - size of biggest memory allocation) / all free space]. This results in a number from 0 to 1.
     * The close the number is to 1, the less fragmented the memory is. This value is added onto externalFragmentation
     * each time this method is called. Users should call getAverageExternalFragmentation to find the average
     * external fragmentation over each time it was calculated.
     */
    public void calculateExternalFragmentation(){
        int freeSpace = 0;
        int sizeOfLargestMemoryAllocation = 0;
        for (MemoryAllocation memAlloc : memoryList){
            if (Memory.isMemoryAllocationAProcess(memAlloc))
                continue;
            freeSpace += memAlloc.getMemorySizeUsed();
            if (memAlloc.getMemorySizeUsed() > sizeOfLargestMemoryAllocation)
                sizeOfLargestMemoryAllocation = memAlloc.getMemorySizeUsed();
        }
        int sum = freeSpace - sizeOfLargestMemoryAllocation;
        int divisor = freeSpace;
        externalFragmentation += (double) sum/divisor;
        externalFragmentationCalculationCount++;
    }

    public double getAverageExternalFragmentation(){
        if (externalFragmentationCalculationCount == 0)
            return -1;
        return externalFragmentation/externalFragmentationCalculationCount;
    }

    static public boolean isMemoryAllocationAProcess(MemoryAllocation memAlloc){
        try {
            // if memAlloc is not a Process, attempting to cast it will throw ClassCastException.
            Process p =(Process) memAlloc;
            return true;
        }
        catch (ClassCastException e){
            return false;
        }
    }

    @Override
    public String toString() {
        return "Memory{" +
                "memoryList=" + memoryList +
                ", memorySize=" + memorySize +
                ", internalFragmentation=" + internalFragmentation +
                ", externalFragmentation=" + externalFragmentation +
                ", currentTime=" + currentTime +
                ", allocationFailures=" + allocationFailures +
                '}';
    }
}
