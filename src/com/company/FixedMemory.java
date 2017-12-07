package com.company;

public abstract class FixedMemory extends Memory {

    @Override
    public boolean addProcess(Process proc) {
        for (int i = 0; i < memoryList.size(); i++) {
            MemoryAllocation currentAllocation = memoryList.get(i);

            // if current place isn't free space, continue loop to next place
            if (isMemoryAllocationAProcess(currentAllocation))
                continue;

            proc.setMemorySizeUsed(currentAllocation.getMemorySizeUsed()); // set the size we will be taking up

            proc.setStartingPositionInMemory(memoryList.get(i).getStartingPositionInMemory());
            proc.setEndingPositionInMemory(memoryList.get(i).getEndingPositionInMemory());

            proc.setFinishTime(proc.getArrivalTime() + proc.getRunTime());
            currentTime = proc.getArrivalTime();
            calculateInternalFragmentation();
            memoryList.set(i, proc);
            return true;
        }
        return false;
    }
}