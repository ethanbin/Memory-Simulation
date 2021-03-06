package com.company.ProcessInserter;

import com.company.Memory;
import com.company.MemoryAllocation;
import com.company.Process;

import java.util.List;

/**
 * Process Inserter is the base abstract class for a strategy pattern
 */
public abstract class ProcessInserter {
    final public boolean addProcess(Process proc, List<MemoryAllocation> list){
        boolean insertedSuccessfully = tryAllocatingProcess(proc, list);
        return insertedSuccessfully;
    }

    /**
     * This method should determine which memory allocation to insert a process into, depending on the strategy being
     * used. The method then calls allocateProcessToIndex and passes the index of the memory allocation to insert
     * the job into.
     * @param proc Process to allocate
     * @param list Memory list to allocate process into
     * @return true if Process allocated successfully, false if not
     */
    protected abstract boolean tryAllocatingProcess(Process proc, List<MemoryAllocation> list);

    /**
     * Checks if the given memory allocation is a job or too small for the required size indicated in a parameter.
     * Each strategy will need to make this check, so better to write it once here as method than
     * rewrite this multiple times.
     * @param memAlloc Memory Allocation to check if it is a Process
     * @param sizeNeeded Size needed for a Process that is considering this allocation
     * @return true if this memory allocation is a Process or if the allocation is too small for the needed size.
     */
    final protected boolean isMemoryAllocationAProcessOrTooSmall(MemoryAllocation memAlloc, int sizeNeeded){
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
    final protected void allocateProcessToIndex(Process proc, int indexToAllocateProcessTo, List<MemoryAllocation> list){
        MemoryAllocation spaceToInsertProcessInto = list.get(indexToAllocateProcessTo);

        if (spaceToInsertProcessInto.getMemorySizeUsed() == proc.getMemorySizeNeeded()){
            // process takes up the entire block of free memory, essentially replacing it.
            proc.setMemorySizeUsed(proc.getMemorySizeNeeded());
            proc.setStartingPositionInMemory(spaceToInsertProcessInto.getStartingPositionInMemory());
            proc.setEndingPositionInMemory(spaceToInsertProcessInto.getEndingPositionInMemory());
            list.set(indexToAllocateProcessTo, proc);
        }
        else if (spaceToInsertProcessInto.getMemorySizeUsed() > proc.getMemorySizeNeeded()){
            // simulate shrinking of the block of free memory
            spaceToInsertProcessInto.setMemorySizeUsed(
                    spaceToInsertProcessInto.getMemorySizeUsed() - proc.getMemorySizeNeeded());
            proc.setMemorySizeUsed(proc.getMemorySizeNeeded());

            proc.setStartingPositionInMemory(spaceToInsertProcessInto.getStartingPositionInMemory());
            spaceToInsertProcessInto.setStartingPositionInMemory(proc.getStartingPositionInMemory() + proc.getMemorySizeUsed());

            proc.setEndingPositionInMemory(proc.getStartingPositionInMemory() + proc.getMemorySizeUsed() - 1);
            spaceToInsertProcessInto.setStartingPositionInMemory(proc.getStartingPositionInMemory() + proc.getMemorySizeUsed());

            list.add(indexToAllocateProcessTo, proc);
        }
    }
}
