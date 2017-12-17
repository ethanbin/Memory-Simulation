package com.company;

import com.company.Dynamic.DynamicMemory;
import com.company.Fixed.EqualFixedMemory;
import com.company.Fixed.UnequalFixedMemory;
import com.company.ProcessComparators.ProcessArrivalComparator;
import com.company.ProcessComparators.ProcessFinishComparator;
import com.company.ProcessComparators.ProcessNumberComparator;
import com.company.ProcessInserter.BestFitProcessInserter;
import com.company.ProcessInserter.FirstFitProcessInserter;
import com.company.ProcessInserter.WorstFitProcessInserter;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Ethan on 11/27/2017.
 */
public abstract class Memory {
    protected final static int DEFAULT_MEMORY_SIZE = 1024;
    protected List<MemoryAllocation> memoryList;
    protected int memorySize;
    protected List<Double> fragmentations;
    protected double peakFragmentation = -1;
    protected List<Double> memoryUtilizations;
    protected double peakMemoryUtilization = -1;
    protected int currentTime = 0;							// keeps track of time
    protected int allocationFailures = 0;
    protected boolean verboseMode = false;
    protected boolean detailedMode = false;

    protected abstract void init(int size);

    /**
     * Attempts to add process into memory and set current time to the process's arrival time.
     * @param p process to add
     * @return true if process successfully added
     */
    protected abstract boolean addProcess(Process p);

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
                return true;
            }
        }
        return false;
    }

    /**
     * Attempts to remove a given process.
     * @param process Process to remove
     * @return true if the given process was found and removed successfully
     */
    private boolean removeProcess(Process process) {
        int indexOfProc = memoryList.indexOf(process);
        if (indexOfProc == -1)
            return false;
        memoryList.set(indexOfProc, new MemoryAllocation(process.getMemorySizeUsed(),
                process.getStartingPositionInMemory(),
                process.getEndingPositionInMemory()));
        return true;
    }

    /**
     * Remove all finished processes and update fragmentation and memory utilization.
     * @return true if any process were finished and removed
     */
    private boolean removeFinishedProcesses(){
        List<Process> processesToRemove = new ArrayList<>();
        for (MemoryAllocation memAlloc : memoryList){
            if (!isMemoryAllocationAProcess(memAlloc))
                continue;
            Process proc = (Process) memAlloc;
            if (proc.getFinishTime() <= currentTime)
                processesToRemove.add(proc);
        }

        Collections.sort(processesToRemove, new ProcessFinishComparator());
        if (processesToRemove.size() > 0) {
            int timeOfLastRemoval = processesToRemove.get(0).getFinishTime();
            for (Process proc : processesToRemove) {
                // if the time of the process being removed is different than what was last removed,
                // make data calculations on memory now before moving on to next time for processes to be removed.
                if (proc.getFinishTime() > timeOfLastRemoval) {
                    timeOfLastRemoval = proc.getFinishTime();
                    calculateMemoryUtilizationPercentage();
                    calculateFragmentationPercentage();
                }
                removeProcess(proc);
            }
            // calculate data on memory here because it is only calculate above -before- the latest process is removed,
            // so the last process(es) do not get a check and start data calculations.
            calculateMemoryUtilizationPercentage();
            calculateFragmentationPercentage();
            return true;
        }
        return false;
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

    public double getPeakFragmentation(){
        return peakFragmentation;
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
        double totalMemoryUtilization = 0;
        for (Double d : memoryUtilizations)
            totalMemoryUtilization += d;
        return totalMemoryUtilization/memoryUtilizations.size();
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
    public static boolean isMemoryAllocationAProcess(MemoryAllocation memAlloc){
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
     * Start simulating memory, adding and removing jobs until all process have been run through. Simulation and counted
     * time will stop at the arrival of the final process.
     * @param processes Processes to allocate
     */
    public final void start(List<Process> processes){
        processes.sort(new ProcessArrivalComparator());
        if (verboseMode)
            System.out.println(getVerboseData(-1));
        for (Process p : processes){
            // update time
            if (currentTime < p.getArrivalTime())
                currentTime = p.getArrivalTime();
            // remove all completed jobs
            removeFinishedProcesses();
            // add process
            boolean processAddedSuccessfully = addProcess(p);
            if(processAddedSuccessfully) {
                // calculate fragmentation
                calculateFragmentationPercentage();
                // calculate memory after adding new process
                calculateMemoryUtilizationPercentage();
            }
            if (verboseMode)
                System.out.println(getVerboseData(p.getProcessNumber()));
        }
        if (detailedMode)
            System.out.println(getDetailedData(processes));
    }

    public boolean isVerboseMode() {
        return verboseMode;
    }

    public void setVerboseMode(boolean verboseMode) {
        this.verboseMode = verboseMode;
    }

    public boolean isDetailedMode() {
        return detailedMode;
    }

    public void setDetailedMode(boolean detailedMode) {
        this.detailedMode = detailedMode;
    }

    @Override
    public String toString() {
        return "Memory{" +
                "memoryList=" + memoryList +
                ", memorySize=" + memorySize +
                ", fragmentations=" + fragmentations +
                ", peakFragmentation=" + peakFragmentation +
                ", memoryUtilizations=" + memoryUtilizations +
                ", peakMemoryUtilization=" + peakMemoryUtilization +
                ", currentTime=" + currentTime +
                ", allocationFailures=" + allocationFailures +
                '}';
    }

    public String getDataResults() {
        StringBuilder outp = new StringBuilder();
        outp.append(String.format("%f%%%n",
                getAverageFragmentationPercentage() * 100));
        outp.append(String.format("%f%%%n",
                getPeakFragmentation() * 100));
        outp.append(String.format("%d%n",
                allocationFailures));
        outp.append(String.format("%f%%%n",
                getAverageMemoryUtilizationPercentage() * 100));
        outp.append(String.format("%f%%%n",
                getPeakMemoryUtilization() * 100));

        return outp.toString();
    }

    public String getDetailedData(List<Process> processes){
        StringBuilder outp = new StringBuilder();
        processes.sort(new ProcessNumberComparator());
        for (Process p : processes) {
            outp.append(String.format("Process %d%n", p.getProcessNumber()));
            outp.append(String.format("%-40s %d %n",
                    "Arrival Time:",
                    p.getArrivalTime()));
            outp.append(String.format("%-40s %s %n",
                    "Allocated Space:",
                    p.getMemorySizeUsed() >= 0 ?
                            String.valueOf(p.getMemorySizeUsed()) : "0 (fail)"));

            if (p.getMemorySizeUsed() > 0)
            outp.append(String.format("%-40s %d %n",
                    "Internal Fragmentation Produced:",
                    p.getMemorySizeUsed() - p.getMemorySizeNeeded()));

            outp.append(String.format("%-40s %d %n",
                    "Finish Time:",
                    p.getFinishTime()));
            outp.append(String.format("%n"));
        }
        return outp.toString();
    }

    public String getVerboseData(int processNumber){
        StringBuilder outp = new StringBuilder();
        if (currentTime == 0)
            outp.append(String.format("At time 0: the initial state, the memory allocation state:%n"));
        else
            outp.append(String.format("At time %d: Process {%d} comes, the memory changes the allocation state: %n",
                    currentTime, processNumber));

        for (MemoryAllocation memAlloc : memoryList){
            if (isMemoryAllocationAProcess(memAlloc)){
                Process proc = (Process) memAlloc;
                if (proc.getProcessNumber() == -1)
                    outp.append(String.format("%-4d - %-4d: used by OS%n",
                            proc.getStartingPositionInMemory(),proc.getEndingPositionInMemory()));
                else
                    outp.append(String.format("%-4d - %-4d: used by process {%d}%n",
                            proc.getStartingPositionInMemory(),proc.getEndingPositionInMemory(), proc.getProcessNumber()));
            }
            else {
                outp.append(String.format("%-4d - %-4d: hole%n",
                        memAlloc.getStartingPositionInMemory(),
                        memAlloc.getEndingPositionInMemory()));
            }
        }
        return outp.toString();
    }
}