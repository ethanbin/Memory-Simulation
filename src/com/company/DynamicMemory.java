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
        memorySize = size;
        memoryList.add(new MemoryAllocation(size, 0, size - 1));
    }

    @Override
    public boolean addProcess(Process proc) {
        if (insertingStrategy.addProcess(proc, memoryList)) {
            return true;
        }
        allocationFailures++;
        return false;
    }

    /**
     * Calculates internal fragmentation. Since dynamic memory allocation cannot have internal fragmentation,
     * this just sets internalFragmentation to 0.
     * @return Average external fragmentation for the entire time this program ran.
     */
    @Override
    public void calculateInternalFragmentation() {
        // Dyanmic memory cannot cause internal fragmentation.
        internalFragmentation = 0;
    }

    /**
     * Returns internal fragmentation. Since dynamic memory allocation cannot have internal fragmentation, return 0.
     * @return internal fragmentation. Since dynamic memory allocation cannot have internal fragmentation, return 0
     */
    @Override
    public double getAverageInternalFragmentation() {
        return 0;
    }

    @Override
    public boolean compact() {
        return false;
    }

    @Override
    public String toString() {
        String outp = "";
        outp += "Total Size of Memory: " + memorySize + "\n";
        outp += "Memory Snapshot:\n";
        int freeSpaceRemaining = 0;
        for (MemoryAllocation memAlloc : memoryList){
            String type = memAlloc.getClass().getSimpleName();
            if (type.equalsIgnoreCase("Process")){
                Process proc = (Process) memAlloc;
                outp += "Process \"" + proc.getName() + "\"\t\t\tSize: " + proc.getMemorySizeUsed() +
                        "\t Starts in address " + proc.getStartingPositionInMemory() + ";\t" +
                        "\t Ends in address " + proc.getEndingPositionInMemory() + "\n";
            }
            else {
                outp += "Allocated Memory.\tSize: " + memAlloc.getMemorySizeUsed() +
                        "\t Starts in address " + memAlloc.getStartingPositionInMemory() + ";\t" +
                        "\t Ends in address " + memAlloc.getEndingPositionInMemory() + "\n";
                freeSpaceRemaining += memAlloc.getMemorySizeUsed();
            }
        }
        outp += "Free space remaining: " + freeSpaceRemaining + "\n";
        outp += "Process Allocations failed: " + allocationFailures + "\n";
        outp += "Average External Fragmentation: " + externalFragmentation + "\n";
        return outp;
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

        for (Process p : jobList){
            firstFit.addProcess(p);
            if (p.getName() == "F")
                firstFit.removeProcess("F");
        }
        System.out.println(firstFit);
    }
}
