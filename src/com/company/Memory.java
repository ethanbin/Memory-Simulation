package com.company;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 11/27/2017.
 */
public class Memory {
    protected final int DEFAULT_MEMORY_SIZE = 1024;
    protected List<MemoryAllocation> memoryList;
    int totalMemorySize;

    public Memory(){
        init(DEFAULT_MEMORY_SIZE);
    }

    public Memory(int size){
        init(size);
    }

    protected void init(int size){
        memoryList = new ArrayList<>();
        memoryList.add(new MemoryAllocation(size, 0, DEFAULT_MEMORY_SIZE));
    }

    @Override
    public String toString() {
        return "Memory{" +
                "DEFAULT_MEMORY_SIZE=" + DEFAULT_MEMORY_SIZE +
                ", memoryList=" + memoryList +
                ", totalMemorySize=" + totalMemorySize +
                '}';
    }
}
