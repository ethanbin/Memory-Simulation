package com.company.ProcessInserter;

import com.company.MemoryAllocation;
import com.company.Process;
import java.util.List;

/**
 * Created by Ethan on 12/6/2017.
 */
public class WorstFitProcessInserter extends ProcessInserter{
    @Override
    public boolean tryAllocatingProcess(Process proc, List<MemoryAllocation> list){
        int largestSizedIndex = -1;
        int largestMemorySize = -1;
        for (int i = 0; i < list.size(); i++){
            if (isMemoryAllocationAProcessOrTooSmall(list.get(i), proc.getMemorySizeNeeded()))
                continue;
            if (list.get(i).getMemorySizeUsed() > largestMemorySize) {
                largestSizedIndex = i;
                largestMemorySize = list.get(i).getMemorySizeUsed();
            }
        }
        if (largestSizedIndex == -1)
            return false;
        allocateProcessToIndex(proc, largestSizedIndex, list);
        return true;
    }
}
