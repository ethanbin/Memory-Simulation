package com.company;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 11/27/2017.
 */
public abstract class Memory {
    protected final int MEMORY_SIZE = 1024;
    protected static List<MemoryAllocation> memoryList;
    private int totalMemorySize = 0;					//space that has been allocated
    
    protected int internalFragmenation = 0;
    protected int externalFragmentation = 0;
    protected int currTime = 0;							// keeps track of time
    protected int partitionCount = 0;
    protected int allocationFail = 0;

    public Memory(){
        init(MEMORY_SIZE);
    }

    public Memory(int size){
        init(size);
    }

    protected void init(int size){
        memoryList = new ArrayList<>();
        memoryList.add(new MemoryAllocation(size, 0, MEMORY_SIZE));
    }

    public abstract void addProcess(Process p, int pos);
    
    public abstract void removeProcess(int pos);
    
    public abstract void calculateInternalFragmentation(Process p);
    
    public abstract void calculateExternalFragmentation(Process P);
    
    @Override
    public String toString() {
        return "Memory{" +
                "DEFAULT_MEMORY_SIZE=" + MEMORY_SIZE +
                ", memoryList=" + memoryList +
                ", totalMemorySize=" + totalMemorySize +
                '}';
    }
}
