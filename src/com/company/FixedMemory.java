package com.company;

/**
 * Created by Ethan on 11/29/2017.
 */
public abstract class FixedMemory extends Memory {
    @Override
    public boolean addProcess(Process p) {
        for (int i = 0; i < memoryList.size(); i++){
            MemoryAllocation currentAllocation = memoryList.get(i);
            // if current place isn't free space, continue loop to next place
            if (isMemoryAllocationAProcess(currentAllocation))
                continue;
            if (currentAllocation.getMemorySizeUsed() <= p.getMemorySizeNeeded()) {
                p.setMemorySizeUsed(currentAllocation.getMemorySizeUsed());
                memoryList.set(i, p);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeProcess(String processName) {
        return false;
    }

    @Override
    public boolean removeProcess(int startingPositionInMemory) {
        return false;
    }
}
