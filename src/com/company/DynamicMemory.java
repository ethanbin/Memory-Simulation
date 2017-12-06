package com.company;

import com.company.ProcessInserter.BestFitProcessInserter;
import com.company.ProcessInserter.FirstFitProcessInserter;
import com.company.ProcessInserter.ProcessInserter;

/**
 * Created by Ethan on 11/29/2017.
 */
public class DynamicMemory extends Memory implements Compactable{
    ProcessInserter insertingStrategy;

    DynamicMemory(ProcessInserter strat) {
        insertingStrategy = strat;
        init(DEFAULT_MEMORY_SIZE);
    }

    DynamicMemory(int size, ProcessInserter strat){
        insertingStrategy = strat;
        init(size);
    }

    @Override
    protected void init(int size) {
        memoryList.add(new MemoryAllocation(size, 0, size - 1));
    }

    @Override
    public boolean addProcess(Process proc) {
        return insertingStrategy.addProcess(proc, memoryList);
    }

    @Override
    public boolean removeProcess(int startingPositionInMemory) {
        for (int i = 0; i < memoryList.size(); i++){
            if (!Memory.isMemoryAllocationAProcess(memoryList.get(i)))
                continue;
            Process currentProc = (Process) memoryList.get(i);
            if (currentProc.getStartingPositionInMemory() == startingPositionInMemory){
                memoryList.set(i, new MemoryAllocation());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeProcess(String processName) {
        for (int i = 0; i < memoryList.size(); i++){
            if (!Memory.isMemoryAllocationAProcess(memoryList.get(i)))
                continue;
            Process currentProc = (Process) memoryList.get(i);
            if (currentProc.getName().equals(processName)){
                memoryList.set(i, new MemoryAllocation());
                return true;
            }
        }
        return false;
    }

    @Override
    public void calculateInternalFragmentation(Process p) {

    }

    @Override
    public void calculateExternalFragmentation(Process P) {

    }

    @Override
    public boolean compact() {
        return false;
    }
}
