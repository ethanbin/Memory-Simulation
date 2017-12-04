package com.company;

import java.util.List;

/**
 * Created by Ethan on 11/27/2017.
 */
public abstract class Memory {
    protected final static int DEFAULT_MEMORY_SIZE = 1024;
    protected List<MemoryAllocation> memoryList;
    protected int memorySize;
    protected int internalFragmentation = 0;
    protected int externalFragmentation = 0;
    protected int internalFragmentationCount = 0;	//we need these counts to find the average in the end
    protected int externalFragmentationCount = 0;
	protected int currentTime = 0;					// keeps track of time
	protected int allocationFailures = 0;
	protected int lastProcessArrivalTime;
	
    protected abstract void init(int size);
    
    public abstract boolean canPlace(Process a);

    //public abstract boolean addProcess(Process p);
    
    public abstract boolean isProcessDone(Process a);
    
    //public abstract boolean removeProcess(int startingPositionInMemory);

    //public abstract boolean removeProcess(String processName);
    
    public abstract void removeFinishedProcesses(int currTime, int totalPartitions);

    public abstract void calculateInternalFragmentation(int partitionSize, Process p);
    
    public abstract void calculateExternalFragmentation(Process p);
    
    public abstract void display();

    protected boolean isMemoryAllocationAProcess(MemoryAllocation memAlloc){
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
