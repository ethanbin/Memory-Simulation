package com.company;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 11/27/2017.
 */
public class Memory {
    private final int DEFAULT_MEMORY_SIZE = 1024;
    List<MemoryAllocation> memoryList;
    int totalMemorySize;

    public Memory(){
        init(DEFAULT_MEMORY_SIZE);
    }

    public Memory(int size){
        init(size);
    }

    private void init(int size){
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
