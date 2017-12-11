package com.company.Fixed;

import com.company.MemoryAllocation;
import com.company.Process;

import java.util.ArrayList;

/**
 * Created by Ethan on 12/8/2017.
 */
public class EqualFixedMemory extends FixedMemory{
    protected final int DEFAULT_MAX_PARTITIONS = 8;
    //ArrayList<Process> jobList;

    public EqualFixedMemory() {
        init(DEFAULT_MEMORY_SIZE);
    }

    public EqualFixedMemory(int size) {
        init(size);
    }

    @Override
    protected void init(int size){
        memorySize = size;
        memoryList = new ArrayList<>();
        int partitionSize = size/ DEFAULT_MAX_PARTITIONS;
        int currentStartingPosition = 0;
        int currentEndingPosition = partitionSize - 1;
        for (int i = 0; i < DEFAULT_MAX_PARTITIONS; i++){
            memoryList.add(new MemoryAllocation(partitionSize, currentStartingPosition, currentEndingPosition));
            currentStartingPosition += partitionSize;
            currentEndingPosition += partitionSize;
        }
        fragmentations = new ArrayList<>();
        memoryUtilizations = new ArrayList<>();
        addProcess(new Process(-1, 0,32, 99999));
    }
}
