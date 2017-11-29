package com.company;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 11/27/2017.
 */
public abstract class Memory {
    protected final int DEFAULT_MEMORY_SIZE = 1024;
    protected List<MemoryAllocation> memoryList;
    protected int totalMemorySize = 0;					//space that has been allocated
    
    protected int size;
    protected int internalFragmentation = 0;
    protected int externalFragmentation = 0;
	protected int currTime = 0;							// keeps track of time
	protected int allocationFail = 0;

    public Memory(){
    	size = DEFAULT_MEMORY_SIZE;
        init(size);
    }

    public Memory(int size){
    	this.size = size;
        init(size);
    }

    protected void init(int size){
        memoryList = new ArrayList<>();
       // memoryList.add(new MemoryAllocation(size, 0, DEFAULT_MEMORY_SIZE));
    }

    public abstract void addProcess();//Process p, int pos);
    
    public abstract void removeProcess(int pos);
    
    public abstract void calculateInternalFragmentation(Process p);
    
    public abstract void calculateExternalFragmentation(Process P);
    
    @Override
    public String toString() {
        return "Memory{" +
                "Size=" + size +
                ", memoryList=" + memoryList +
                ", totalMemorySize=" + totalMemorySize +
                '}';
    }
}
