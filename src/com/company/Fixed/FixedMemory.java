package com.company.Fixed;

import com.company.Memory;
import com.company.MemoryAllocation;
import com.company.Process;

public abstract class FixedMemory extends Memory {

    @Override
    public boolean addProcess(Process proc) {
        for (int i = 0; i < memoryList.size(); i++) {
            MemoryAllocation currentAllocation = memoryList.get(i);

            if (proc.getMemorySizeNeeded() > currentAllocation.getMemorySizeUsed())
                continue;

                // if current place isn't free space, continue loop to next place
            if (isMemoryAllocationAProcess(currentAllocation))
                continue;

            proc.setMemorySizeUsed(currentAllocation.getMemorySizeUsed()); // set the size we will be taking up

            proc.setStartingPositionInMemory(memoryList.get(i).getStartingPositionInMemory());
            proc.setEndingPositionInMemory(memoryList.get(i).getEndingPositionInMemory());

            memoryList.set(i, proc);
            return true;
        }
        return false;
    }

    /**
     * Calculates the current internal fragmentation using of each allocated process using
     * [size of memory used for allocation - size of memory process needs]. This value is added onto
     * internalFragmentation each time this method is called. Users should call getAverageInternalFragmentation
     * to find the average internal fragmentation over each time it was calculated.
     */
    @Override
    public void calculateFragmentationPercentage(){
        double fragmentationPercentage = 0;
        int internalFragmentationsCount = 0;
        for (MemoryAllocation memAlloc : memoryList){
            if (!isMemoryAllocationAProcess(memAlloc))
                continue;
            Process proc = (Process) memAlloc;
            double wastedMemory = proc.getMemorySizeUsed() - proc.getMemorySizeNeeded();
            fragmentationPercentage += wastedMemory / proc.getMemorySizeUsed();
            internalFragmentationsCount++;
        }
        double fragmentation = fragmentationPercentage / internalFragmentationsCount;
        if (internalFragmentationsCount == 0)
            fragmentation = 0;
        if (fragmentation > peakFragmentation)
            peakFragmentation = fragmentation;
        fragmentations.add(fragmentation);
    }

}