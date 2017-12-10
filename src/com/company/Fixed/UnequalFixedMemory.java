package com.company.Fixed;

import com.company.MemoryAllocation;

import java.util.ArrayList;

/**
 * Created by Ethan on 12/8/2017.
 */
public class UnequalFixedMemory extends FixedMemory{
    protected final int PARTITION_SPLIT_COUNT = 4;
    protected final int SMALLEST_PARTITION_SIZE = 4;
    protected int smallestPartitionSize;
    //ArrayList<Process> jobList;

    public UnequalFixedMemory() {
        memoryList = new ArrayList<>();
        fragmentations = new ArrayList<>();
        memoryUtilizations = new ArrayList<>();
        init(DEFAULT_MEMORY_SIZE);
    }

    public UnequalFixedMemory(int size) {
        memoryList = new ArrayList<>();
        fragmentations = new ArrayList<>();
        memoryUtilizations = new ArrayList<>();
        init(size);
        int total = 0;
        for (MemoryAllocation memoryAllocation : memoryList)
            total += memoryAllocation.getMemorySizeUsed();
    }

    @Override
    protected void init(int size){
        int partitionSize = size / PARTITION_SPLIT_COUNT;
        // the partition is split 4 ways. 1 gets split further, the other 3 are used as-is
        if (partitionSize > SMALLEST_PARTITION_SIZE){
            init(partitionSize);
            for (int i = 0; i < PARTITION_SPLIT_COUNT - 1; i++){
                memoryList.add(new MemoryAllocation(partitionSize));
            }
        }
        else{
            smallestPartitionSize = partitionSize;
            for (int i = 0; i < PARTITION_SPLIT_COUNT; i++){
                memoryList.add(new MemoryAllocation(partitionSize));
            }
        }
    }

    @Override
    public String toString() {
        return "UnequalFixedMemory{" +
                "PARTITION_SPLIT_COUNT=" + PARTITION_SPLIT_COUNT +
                ", SMALLEST_PARTITION_SIZE=" + SMALLEST_PARTITION_SIZE +
                ", smallestPartitionSize=" + smallestPartitionSize +
                ", memorySize=" + memorySize +
                ", currentTime=" + currentTime +
                ", allocationFailures=" + allocationFailures +
                "} " + super.toString();
    }
}
