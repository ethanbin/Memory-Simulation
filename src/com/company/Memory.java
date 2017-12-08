package com.company;

import com.company.Dynamic.DynamicMemory;
import com.company.ProcessInserter.FirstFitProcessInserter;

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
    protected List<Double> memoryUtilizations;
    protected double peakMemoryUtilization = -1;
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
     * Remove all finished processes and update fragmentation and memory utilization.
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
            // calculate memory utilization
        }
        calculateMemoryUtilizationPercentage();
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
     * Calculate memory utilization as a percentage and add it to the memoryUtilizations list. This also updates what
     * the peak memory allocation is.
     */
    protected void calculateMemoryUtilizationPercentage(){
        double memoryUsed = 0;
        for (MemoryAllocation memAlloc : memoryList){
            if (!isMemoryAllocationAProcess(memAlloc))
                continue;
            Process proc = (Process) memAlloc;
            memoryUsed += proc.getMemorySizeNeeded();
        }
        double memoryUsedPercentage = memoryUsed / memorySize;
        if (memoryUsedPercentage > peakMemoryUtilization)
            peakMemoryUtilization = memoryUsedPercentage;
        memoryUtilizations.add(memoryUsedPercentage);
    }

    /**
     * This method gets the average memory utilization over each time it was calculated.
     * @return average memory utilization, -1 if memory utilization was never calculated.
     */
    public double getAverageMemoryUtilizationPercentage(){
        if (memoryUtilizations.size() == 0)
            return -1;
        double totalFragmentation = 0;
        for (Double d : memoryUtilizations)
            totalFragmentation += d;
        return totalFragmentation/fragmentations.size();
    }

    /**
     * Return peak memory allocation as a percentage.
     * @return peak memory allocation as a percentage
     */
    public double getPeakMemoryUtilization(){
        return peakMemoryUtilization;
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
            // update time
            if (currentTime < p.getArrivalTime())
                currentTime = p.getArrivalTime();
            // remove all completed jobs
            removeFinishedProcesses();
            p.setFinishTime(currentTime + p.getRunTime());
            // add process
            addProcess(p);
            // calculate fragmentation
            calculateFragmentationPercentage();
            // calculate memory after adding new process
            calculateMemoryUtilizationPercentage();
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

    public String getDataResults() {
        StringBuilder outp = new StringBuilder();
        outp.append(String.format("%-40s %f %n",
                "Average Fragmentation Percentage:",
                getAverageFragmentationPercentage()));
        outp.append(String.format("%-40s %f %n",
                "Peak Fragmentation Percentage:",
                getPeakFragemntation()));
        outp.append(String.format("%-40s %d %n",
                "Number of Allocation Failures:",
                allocationFailures));
        outp.append(String.format("%-40s %f %n",
                "Average Memory Utilization Percentage:",
                getAverageMemoryUtilizationPercentage()));
        outp.append(String.format("%-40s %f %n",
                "Peak Memory Utilization Percentage:",
                getPeakMemoryUtilization()));

        return outp.toString();
    }


    public static void main(String[] args) {
        Memory memory = new DynamicMemory(new FirstFitProcessInserter());
        List<Process> jobList = new ArrayList<>();
        //for (int i = 0; i < 800; i++)
        //    jobList.add(new Process(String.valueOf(i), 1, 0, 2));

        jobList.add(new Process("A", 600, 3, 4));
        jobList.add(new Process("B", 120, 4, 4));
        jobList.add(new Process("C", 660, 5, 7));
        jobList.add(new Process("D", 120, 9, 5));
        jobList.add(new Process("E",  82, 13,3));
        jobList.add(new Process("F",  127,17, 1));
        jobList.add(new Process("G",  430, 17,8));
        jobList.add(new Process("H",  77, 20,6));
        jobList.add(new Process("I",  109,24, 2));
        jobList.add(new Process("J",  90, 26,3));
        jobList.add(new Process("K",  190,29, 7));
        jobList.add(new Process("L",  240, 31,2));

        memory.start(jobList);

        System.out.println(memory);

        System.out.println(memory.getDataResults());
    }
}