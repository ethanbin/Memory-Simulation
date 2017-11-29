package com.company;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 11/27/2017.
 */
public abstract class Memory {
    protected final int DEFAULT_MEMORY_SIZE = 1024;
    protected List<MemoryAllocation> memoryList;
    protected int size;
    protected int internalFragmentation = 0;
    protected int externalFragmentation = 0;
	protected int currTime = 0;							// keeps track of time
	protected int allocationFail = 0;

    protected abstract void init(int size);

    public abstract void addProcess(Process p);
    
    public abstract void removeProcess(int startingPositionInMemory);

    public abstract void removeProcess(String processName);

    public abstract void calculateInternalFragmentation(Process p);
    
    public abstract void calculateExternalFragmentation(Process P);
    
    @Override
    public String toString() {
        return "Memory{" +
                "Size=" + size +
                ", memoryList=" + memoryList +
                '}';
    }
}
