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
        return true;
    }
}
