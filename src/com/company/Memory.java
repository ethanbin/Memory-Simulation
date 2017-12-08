package com.company;

import com.company.Dynamic.DynamicMemory;
import com.company.Fixed.UnequalFixedMemory;
import com.company.ProcessInserter.FirstFitProcessInserter;
import com.company.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ethan on 11/27/2017.
 */
public abstract class Memory {
    protected final static int DEFAULT_MEMORY_SIZE = 1024;
    protected List<MemoryAllocation> memoryList;
    protected int memorySize;
    protected List<Double> fragmentations;
    protected double peakFragemntation = -1;
    protected int currentTime = 0;							// keeps track of time
	protected int allocationFailures = 0;

    protected abstract void init(int size);

    /**
     * Attempts to add process into memory and set current time to the process's arrival time.
     * @param p process to add
     * @return true if process successfully added
     */
    protected abstract boolean addProcess(Process p);

    /**
     * Attempts to remove a process of the given name.
     * @param processName Name of process to remove
     * @return true if process removed successfully
     */
    private boolean removeProcess(String processName) {
        for (int i = 0; i < memoryList.size(); i++){
            if (!Memory.isMemoryAllocationAProcess(memoryList.get(i)))
                continue;

            Process currentProc = (Process) memoryList.get(i);

            if (currentProc.getName().equals(processName)){
                memoryList.set(i, new MemoryAllocation(currentProc.getMemorySizeUsed(),
                        currentProc.getStartingPositionInMemory(),
                        currentProc.getEndingPositionInMemory()));
                return true;
            }
        }
        return false;
    }

    /**
     * Attempts to remove a process with the given starting position in memory.
     * @param startingPositionInMemory Starting position of process to remove
     * @return true if process removed successfully
     */
    private boolean removeProcess(int startingPositionInMemory) {
        for (int i = 0; i < memoryList.size(); i++){
            if (!Memory.isMemoryAllocationAProcess(memoryList.get(i)))
                continue;

            Process currentProc = (Process) memoryList.get(i);

            if (currentProc.getStartingPositionInMemory() == startingPositionInMemory){
                memoryList.set(i, new MemoryAllocation(currentProc.getMemorySizeUsed(),
                        currentProc.getStartingPositionInMemory(),
                        currentProc.getEndingPositionInMemory()));
                calculateFragmentationPercentage();
                return true;
            }
        }
        return false;
    }

    /**
     * Remove all finished processes.
     * @return true if all finished process were removed
     */
    private boolean removeFinishedProcesses(){
        List<Integer> startingPositionsOfProcessesToRemove = new ArrayList<>();
        for (MemoryAllocation memAlloc : memoryList){
            if (!isMemoryAllocationAProcess(memAlloc))
                continue;
            Process proc = (Process) memAlloc;
            if (proc.getFinishTime() <= currentTime)
                startingPositionsOfProcessesToRemove.add(proc.getStartingPositionInMemory());
        }

        boolean allFinishedProcessesRemoved = true;
        for (int i : startingPositionsOfProcessesToRemove){
            // if removeProcess fails once, the boolean will become false, and the && will keep it false
            allFinishedProcessesRemoved = removeProcess(i) && allFinishedProcessesRemoved;
        }
        return allFinishedProcessesRemoved;
    }

    /**
     * Calculate how fragmented memory is at the time when the method is called as a percentage and add
     * the result onto the fragmentations list. The smaller the result, the less fragmentation exists.
     */
    protected abstract void calculateFragmentationPercentage();

    /**
     * This method gets the average fragmentation over each time fragmentation was calculated.
     * @return average internal fragmentation, -1 if fragmentation was never calculated.
     */
    public double getAverageFragmentationPercentage(){
        if (fragmentations.size() == 0)
            return -1;
        double totalFragmentation = 0;
        for (Double d : fragmentations)
            totalFragmentation += d;
        return totalFragmentation/fragmentations.size();
    }

    public double getPeakFragemntation(){
        return peakFragemntation;
    }

    /**
     * Receives a memory allocation and returns true if is a Process.
     * @param memAlloc Memory allocation to test
     * @return
     */
    static public boolean isMemoryAllocationAProcess(MemoryAllocation memAlloc){
        try {
            // if memAlloc is not a Process, attempting to cast it will throw ClassCastException.
            Process p =(Process) memAlloc;
            return true;
        }
        catch (ClassCastException e){
            return false;
        }
    }

    /**
     * Simulate memory adding and removing jobs until all process have been run through. Simulation and counted
     * time will stop at the arrival of the final process.
     * @param processes
     */
    public final void simulateMemory(List<Process> processes){
        Collections.sort(processes);
        for (Process p : processes){
            // remove all completed jobs
            removeFinishedProcesses();
            // update time
            if (currentTime < p.getArrivalTime())
                currentTime = p.getArrivalTime();
            p.setFinishTime(currentTime + p.getRunTime());
            // add process
            addProcess(p);
            // calculate fragmentation
            calculateFragmentationPercentage();
        }
    }

    /**
     * Begin simulating memory. This method does nothing other than call the simulateMemory method.
     * @param processes
     */
    public final void start(List<Process> processes){
        simulateMemory(processes);
    }

    @Override
    public String toString() {
        return "Memory{" +
                //"memoryList=" + memoryList +
                ", memorySize=" + memorySize +
                ", averaage fragmentation percentage =" + getAverageFragmentationPercentage() +
                ", currentTime=" + currentTime +
                ", allocationFailures=" + allocationFailures +
                '}';
    }

    public String dataResults() {
        String outp = "";
        outp += "Average Fragmentation Percentage: " + getAverageFragmentationPercentage();
        outp += "Peak Fragmentation Percentage: " + getPeakFragemntation();
        return outp;
    }


    public static void main(String[] args) {
        Memory memory = new DynamicMemory(new FirstFitProcessInserter());
        List<Process> jobList = new ArrayList<>();
        for (int i = 0; i < 800; i++)
            jobList.add(new Process(String.valueOf(i), 1, 0, 2));

//        jobList.add(new Process("A", 60, 3, 4));
//        jobList.add(new Process("B", 12, 4, 4));
//        jobList.add(new Process("C", 66, 5, 7));
//        jobList.add(new Process("D", 12, 9, 5));
//        jobList.add(new Process("E",  82, 13,3));
//        jobList.add(new Process("F",  127,17, 1));
//        jobList.add(new Process("G",  43, 17,8));
//        jobList.add(new Process("H",  77, 20,6));
//        jobList.add(new Process("I",  109,24, 2));
//        jobList.add(new Process("J",  90, 26,3));
//        jobList.add(new Process("K",  190,29, 7));
//        jobList.add(new Process("L",  24, 31,2));

        Collections.sort(jobList);

        memory.start(jobList);

        System.out.println(memory);
    }
}
