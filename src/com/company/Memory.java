package com.company;

import com.company.Dynamic.DynamicMemory;
import com.company.ProcessInserter.FirstFitProcessInserter;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.text.NumberFormat;
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
        }
    }

    @Override
    public String toString() {
        return "Memory{" +
                "memoryList=" + memoryList +
                ", memorySize=" + memorySize +
                ", fragmentations=" + fragmentations +
                ", peakFragemntation=" + peakFragemntation +
                ", memoryUtilizations=" + memoryUtilizations +
                ", peakMemoryUtilization=" + peakMemoryUtilization +
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
                getPeakFragmentation()));
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
        Options options = new Options();

        // set input to required
        Option input = new Option("i", "input", true, "input file path");
        input.setRequired(true);
        options.addOption(input);

        Option output = new Option("o", "output", true, "output file path");
        output.setRequired(false);
        options.addOption(output);

        Option detailed = new Option("d", "detailed", false, "show detailed data");
        detailed.setRequired(false);
        options.addOption(detailed);

        Option verbose = new Option("v", "verbose", false, "verbose mode");
        verbose.setRequired(false);
        options.addOption(verbose);

        // try getting and parsing command line arguments
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
        }
        catch (ParseException e){
            System.err.println("Missing or Invalid Argument(s)");
            return;
        }

        // try to parse input file into a List of Processes
        List<Process> jobList = new ArrayList<>();
        ReadFile rf = new ReadFile(cmd.getOptionValue("i"));
        try {
            String [] inData = rf.OpenFile();
            for (String str : inData) {
                String [] processData = str.split("\t");

                int procNumber = Integer.parseInt(processData[0]);
                int procArrivalTime = Integer.parseInt(processData[1]);
                int procSize = Integer.parseInt(processData[2]);
                int procFinishTime = Integer.parseInt(processData[3]);

                jobList.add(new Process(procNumber, procArrivalTime, procSize, procFinishTime));
            }
        }
        catch (IOException e){
            System.err.println("Input File Not Found");
            return;
        }
        catch (NumberFormatException e){
            System.err.println("Input File Data Invalid.");
            return;
        }
        // print all process that were read in
        for (Process proc : jobList)
            System.out.println(proc);
        // run memory and print results
        Memory mem = new DynamicMemory(new FirstFitProcessInserter());
        mem.start(jobList);
        System.out.println(mem);
        System.out.println(mem.getDataResults());
    }
}