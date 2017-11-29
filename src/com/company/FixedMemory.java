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
                p.setEndingPositionInMemory(currentAllocation.getEndingPositionInMemory());
                p.setStartingPositionInMemory(currentAllocation.getStartingPositionInMemory());
                memoryList.set(i, p);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeProcess(String processName) {
        for (int i = 0; i < memoryList.size(); i++){
            if (!isMemoryAllocationAProcess(memoryList.get(i)))
                continue;
            Process currentProcess = (Process) memoryList.get(i);
            if (!currentProcess.getName().equals(processName))
                continue;
            MemoryAllocation memAlloc = new MemoryAllocation(currentProcess.getMemorySizeUsed(),
                    currentProcess.getStartingPositionInMemory(),
                    currentProcess.getEndingPositionInMemory());
            memoryList.set(i, memAlloc);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeProcess(int startingPositionInMemory) {
        for (int i = 0; i < memoryList.size(); i++){
            if (!isMemoryAllocationAProcess(memoryList.get(i)))
                continue;
            Process currentProcess = (Process) memoryList.get(i);
            if (currentProcess.getStartingPositionInMemory() != startingPositionInMemory)
                continue;
            MemoryAllocation memAlloc = new MemoryAllocation(currentProcess.getMemorySizeUsed(),
                    currentProcess.getStartingPositionInMemory(),
                    currentProcess.getEndingPositionInMemory());
            memoryList.set(i, memAlloc);
            return true;
        }
        return false;
    }
}
