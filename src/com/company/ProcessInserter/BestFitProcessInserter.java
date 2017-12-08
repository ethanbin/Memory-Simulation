package com.company.ProcessInserter;

import com.company.MemoryAllocation;
import com.company.Process;
import java.util.List;

/**
 * Created by Ethan on 12/6/2017.
 */
public class BestFitProcessInserter extends ProcessInserter{
    @Override
    public boolean tryAllocatingProcess(Process proc, List<MemoryAllocation> list){
        int largestFittingSizedIndex = -1;
        int smallestMemorySizeDifference = -1;
        for (int i = 0; i < list.size(); i++){
            if (isMemoryAllocationAProcessOrTooSmall(list.get(i), proc.getMemorySizeNeeded()))
                continue;
            if (list.get(i).getMemorySizeUsed() - proc.getMemorySizeNeeded() < smallestMemorySizeDifference) {
                largestFittingSizedIndex = i;
                smallestMemorySizeDifference = list.get(i).getMemorySizeUsed() - proc.getMemorySizeNeeded();
            }
        }
        if (largestFittingSizedIndex == -1)
            return false;
        allocateProcessToIndex(proc, largestFittingSizedIndex, list);
        return true;
    }
}
