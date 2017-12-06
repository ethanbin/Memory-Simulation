package com.company;

import com.company.ProcessInserter.BestFitProcessInserter;
import com.company.ProcessInserter.FirstFitProcessInserter;
import com.company.ProcessInserter.ProcessInserter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 11/29/2017.
 */
public class DynamicMemory extends Memory implements Compactable{
    private ProcessInserter insertingStrategy;

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
        memoryList = new ArrayList<>();
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

    public static void main(String[] args) {
        DynamicMemory firstFit = new DynamicMemory(new FirstFitProcessInserter());
        List<Process> jobList = new ArrayList<>();
        jobList.add(new Process("A", 0, 131, 4));
        jobList.add(new Process("B", 2, 120, 4));
        jobList.add(new Process("C", 5, 158, 7));
        jobList.add(new Process("D", 9, 107, 5));
        jobList.add(new Process("E", 13, 82, 3));
        jobList.add(new Process("F", 17, 127, 1));
        jobList.add(new Process("G", 17, 43, 8));
        jobList.add(new Process("H", 20, 77, 6));
        jobList.add(new Process("I", 24, 109, 2));
        jobList.add(new Process("J", 26, 90, 3));
        jobList.add(new Process("K", 29, 190, 7));
        jobList.add(new Process("L", 31, 24, 2));

        for (Process p : jobList)
            firstFit.addProcess(p);
        System.out.println(firstFit);
    }
}
