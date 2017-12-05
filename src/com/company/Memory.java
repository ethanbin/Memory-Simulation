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
    protected int internalCount = 0;
    protected int externalFragmentation = 0;
    protected int externalCount = 0;
	protected int currentTime = 0;			// keeps track of time
	protected int currMemoryPos = 0;		//keeps track of what position we are at in memory
	protected int processCount = 0;			//keeps track of when to stop looping to add and remove processes
	protected int allocationFailures = 0;

    protected abstract void init(int size);

    public abstract boolean addProcesses(Process p, int partition);
    
    public abstract void removeProcesses();

    public abstract void calculateInternalFragmentation(Process p);
    
    public abstract void calculateExternalFragmentation(Process P);

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
