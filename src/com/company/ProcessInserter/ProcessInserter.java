package com.company.ProcessInserter;

import com.company.Memory;
import com.company.MemoryAllocation;
import com.company.Process;

import java.util.List;

/**
 * Process Inserter is the base abstract class for a strategy pattern
 */
public abstract class ProcessInserter {
    public boolean addProcess(Process proc, List<MemoryAllocation> list){
        boolean insertedSuccessfully = findLocationToAllocateProcess(proc, list);
        return insertedSuccessfully;
    }

    protected abstract boolean findLocationToAllocateProcess(Process proc, List<MemoryAllocation> list);

    // each strategy will need to make this check, so better to write it once here as method than
    // rewrite this multiple times.
    protected boolean isMemoryAllocationAProcessOrTooSmall(MemoryAllocation memAlloc, int sizeNeeded){
        if (Memory.isMemoryAllocationAProcess(memAlloc))
            return true;
        if (memAlloc.getMemorySizeUsed() < sizeNeeded)
            return true;
        return false;
    }

    /**
     * Handles how to insert a job in a specified location depending on the memory allocation's size. This method
     * of actually inserting a process will always be the same, regardless of the strategy being used - the only
     * difference in the strategies will be finding where/which memory allocation to insert the process at.
     * @param proc Process to insert
     * @param indexToAllocateProcessTo Index in memroy list to insert proc into
     * @param list List of simulated memory to insert the process into.
     */
    protected void allocateProcessToIndex(Process proc, int indexToAllocateProcessTo, List<MemoryAllocation> list){
        MemoryAllocation spaceToInsertProcessInto = list.get(indexToAllocateProcessTo);

        if (spaceToInsertProcessInto.getMemorySizeUsed() == proc.getMemorySizeNeeded()){
            // process takes up the entire block of free memory, essentially replacing it.
            proc.setMemorySizeUsed(proc.getMemorySizeNeeded());
            list.set(indexToAllocateProcessTo, proc);
        }
        else if (spaceToInsertProcessInto.getMemorySizeUsed() > proc.getMemorySizeNeeded()){
            // simulate shrinking of the block of free memory
            spaceToInsertProcessInto.setMemorySizeUsed(
                    spaceToInsertProcessInto.getMemorySizeUsed() - proc.getMemorySizeNeeded());
            proc.setMemorySizeUsed(proc.getMemorySizeNeeded());
            list.add(proc);
        }
    }
}
