package com.company;

import com.company.ProcessInserter.BestFitProcessInserter;
import com.company.ProcessInserter.FirstFitProcessInserter;
import com.company.ProcessInserter.ProcessInserter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ethan on 11/29/2017.
 */
public class DynamicMemory extends Memory {
    protected ProcessInserter insertingStrategy;
    protected int timesCompacted;

    DynamicMemory(ProcessInserter strat) {
        insertingStrategy = strat;
        init(DEFAULT_MEMORY_SIZE);
    }

    DynamicMemory(int size, ProcessInserter strat){
        insertingStrategy = strat;
        init(size);
    }

    @Override
    protected void init(int size) {
        memoryList = new ArrayList<>();
        memorySize = size;
        memoryList.add(new MemoryAllocation(size, 0, size - 1));
        fragmentations = new ArrayList<>();
    }

    @Override
    public boolean addProcess(Process proc) {
        if (currentTime < proc.getArrivalTime())
            currentTime = proc.getArrivalTime();
        proc.setFinishTime(currentTime + proc.getRunTime());
        if (insertingStrategy.addProcess(proc, memoryList)) {
            return true;
        }
        allocationFailures++;
        return false;
    }

    /**
     * Calculates the current external fragmentation using
     * [(all free space - size of biggest memory allocation) / all free space]. This results in a number from 0 to 1.
     * The close the number is to 1, the less fragmented the memory is. This value is added to fragmentations
     * each time this method is called. Users should call getAverageFragmentationPercentage to find the average
     * external fragmentation over each time it was calculated.
     */
    @Override
    public void calculateFragmentationPercentage() {
        int freeSpace = 0;
        int sizeOfLargestMemoryAllocation = 0;
        for (MemoryAllocation memAlloc : memoryList){
            if (Memory.isMemoryAllocationAProcess(memAlloc))
                continue;
            freeSpace += memAlloc.getMemorySizeUsed();
            if (memAlloc.getMemorySizeUsed() > sizeOfLargestMemoryAllocation)
                sizeOfLargestMemoryAllocation = memAlloc.getMemorySizeUsed();
        }
        int sum = freeSpace - sizeOfLargestMemoryAllocation;
        int divisor = freeSpace;
        fragmentations.add((double) sum/divisor);
    }

    public boolean compact() {
        return false;
    }

    @Override
    public String toString() {
        String outp = "";
        outp += "Total Size of Memory: " + memorySize + "\n";
        outp += "Memory Snapshot:\n";
        int freeSpaceRemaining = 0;
        for (MemoryAllocation memAlloc : memoryList){
            String type = memAlloc.getClass().getSimpleName();
            if (type.equalsIgnoreCase("Process")){
                Process proc = (Process) memAlloc;
                outp += "Process \"" + proc.getName() + "\"\t\t\tSize: " + proc.getMemorySizeUsed() +
                        "\t Starts in address " + proc.getStartingPositionInMemory() + ";\t" +
                        "\t Ends in address " + proc.getEndingPositionInMemory() + "\n";
            }
            else {
                outp += "Allocated Memory.\tSize: " + memAlloc.getMemorySizeUsed() +
                        "\t Starts in address " + memAlloc.getStartingPositionInMemory() + ";\t" +
                        "\t Ends in address " + memAlloc.getEndingPositionInMemory() + "\n";
                freeSpaceRemaining += memAlloc.getMemorySizeUsed();
            }
        }
        outp += "Free space remaining: " + freeSpaceRemaining + "\n";
        outp += "Process Allocations failed: " + allocationFailures + "\n";
        outp += "Average External Fragmentation: " + getAverageFragmentationPercentage() + "\n";
        return outp;
    }
}
