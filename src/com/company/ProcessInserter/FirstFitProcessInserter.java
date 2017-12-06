package com.company.ProcessInserter;

import com.company.MemoryAllocation;
import com.company.Process;
import java.util.List;

/**
 * Created by Ethan on 12/6/2017.
 */
public class FirstFitProcessInserter extends ProcessInserter{
    @Override
    public boolean tryAllocatingProcess(Process proc, List<MemoryAllocation> list){
        for (int i = 0; i < list.size(); i++){
            if (isMemoryAllocationAProcessOrTooSmall(list.get(i), proc.getMemorySizeNeeded()))
                continue;
            allocateProcessToIndex(proc, i, list);
            return true;
        }
        return false;
    }
}
