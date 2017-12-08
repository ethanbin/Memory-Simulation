package com.company.Fixed;

import com.company.MemoryAllocation;

import java.util.ArrayList;

/**
 * Created by Ethan on 12/8/2017.
 */
public class EqualFixedMemory extends FixedMemory{
    protected final int DEFAULT_PARTITION_SIZE = 128;
    protected final int MAX_PARTITIONS = 8;
    //ArrayList<Process> jobList;

    public EqualFixedMemory() {
        init(DEFAULT_MEMORY_SIZE);
    }

    public EqualFixedMemory(int size) {
        init(size);
    }

    @Override
    protected void init(int size){
        memoryList = new ArrayList<>();
        int partitionSize = size/ MAX_PARTITIONS;
        for (int i = 0; i < MAX_PARTITIONS; i++){
            memoryList.add(new MemoryAllocation(partitionSize));
        }
        fragmentations = new ArrayList<>();
    }

    public void display() {
        System.out.println("Process\tArrival Time\tProcess Size \tStatus\t\tFinish Time");
        //addProcess();
        System.out.println("Allocation fails: " + allocationFailures);
        //System.out.println("Average Internal Fragmentation: " + internalFragmentation / jobList.size() + "MB");
        //System.out.println("Average External Freagmentation: " + externalFragmentation / jobList.size() + "MB");
    }
}
