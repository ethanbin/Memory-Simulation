package com.company;

import com.company.Dynamic.DynamicMemory;
import com.company.Fixed.EqualFixedMemory;
import com.company.Fixed.UnequalFixedMemory;
import com.company.ProcessInserter.BestFitProcessInserter;
import com.company.ProcessInserter.FirstFitProcessInserter;
import com.company.ProcessInserter.WorstFitProcessInserter;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 12/16/2017.
 */

public class Main {

    private static String runEachMemoryAndPrintResults(List<Process> jobList, boolean detailedMode, boolean verboseMode) {
        Memory memory;
        String outp = "";

        System.out.println("Fixed Partition - Equal Size");
        memory = new EqualFixedMemory();
        memory.setDetailedMode(detailedMode);
        memory.setVerboseMode(verboseMode);
        memory.start(jobList);
        System.out.println(memory.getDataResults());
        outp += String.format("Fixed Partition - Equal Size%n%s%n", memory.getDataResults());

        System.out.println("Fixed Partition - Unequal Size");
        memory = new UnequalFixedMemory();
        memory.setDetailedMode(detailedMode);
        memory.setVerboseMode(verboseMode);
        memory.start(jobList);
        System.out.println(memory.getDataResults());
        outp += String.format("Fixed Partition - Unequal Size%n%s%n", memory.getDataResults());

        System.out.println("Dynamic Partition - First Fit");
        memory = new DynamicMemory(new FirstFitProcessInserter());
        memory.setDetailedMode(detailedMode);
        memory.setVerboseMode(verboseMode);
        memory.start(jobList);
        System.out.println(memory.getDataResults());
        outp += String.format("Dynamic Partition - First Fit%n%s%n", memory.getDataResults());

        System.out.println("Dynamic Partition - Best Fit");
        memory = new DynamicMemory(new BestFitProcessInserter());
        memory.setDetailedMode(detailedMode);
        memory.setVerboseMode(verboseMode);
        memory.start(jobList);
        System.out.println(memory.getDataResults());
        outp += String.format("Dynamic Partition - Best Fit%n%s%n", memory.getDataResults());

        System.out.println("Dynamic Partition - Worst Fit");
        memory = new DynamicMemory(new WorstFitProcessInserter());
        memory.setDetailedMode(detailedMode);
        memory.setVerboseMode(verboseMode);
        memory.start(jobList);
        System.out.println(memory.getDataResults());
        outp += String.format("Dynamic Partition - Worst Fit%n%s%n", memory.getDataResults());

        return outp;
    }

    private static List<Process> parseProcessesFromFile(String filePath){
        List<Process> jobList = new ArrayList<>();
        ReadFile rf = new ReadFile(filePath);
        try {
            String[] inData = rf.OpenFile();
            for (String str : inData) {
                String[] processData = str.split("\t");

                int procNumber = Integer.parseInt(processData[0]);
                int procArrivalTime = Integer.parseInt(processData[1]);
                int procSize = Integer.parseInt(processData[2]);
                int procFinishTime = Integer.parseInt(processData[3]);

                jobList.add(new Process(procNumber, procArrivalTime, procSize, procFinishTime));
            }
        } catch (IOException e) {
            System.err.println("Input File Not Found");
            return null;
        } catch (NumberFormatException e) {
            System.err.println("Input File Data Invalid.");
            return null;
        }
        return jobList;
    }

    public static void main(String[] args) {

        Options options = new Options();

        // set input to required
        Option input = new Option("i", "input", true, "required input file path");
        input.setRequired(true);
        options.addOption(input);

        Option output = new Option("o", "output", true, "output file path");
        output.setRequired(false);
        options.addOption(output);

        Option allocationMethod = new Option("a", "allocation method", true, "method of allocating data");
        allocationMethod.setRequired(false);
        options.addOption(allocationMethod);

        Option detailed = new Option("d", "detailed", false, "show detailed data");
        detailed.setRequired(false);
        options.addOption(detailed);

        Option verbose = new Option("v", "verbose", false, "verbose mode");
        verbose.setRequired(false);
        options.addOption(verbose);

        Option help = new Option("h", "help", false, "show help menu");
        help.setRequired(false);
        options.addOption(help);

        // try getting and parsing command line arguments
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println("Missing or Invalid Argument(s). Use -h for help.");
            return;
        }

        if (cmd.hasOption(help.getOpt())) {
            HelpFormatter hf = new HelpFormatter();
            hf.printHelp("Memory Allocation Simulator arguments", options);
        }

        // try to parse input file into a List of Processes
        String inputPath = cmd.getOptionValue(input.getOpt());
        List<Process> jobList = parseProcessesFromFile(inputPath);
        if (jobList == null)
            return;

        Memory memory;
        String outp = "";
        // handling optional arguments
        boolean verboseMode = cmd.hasOption(verbose.getOpt());
        boolean detailedMode = cmd.hasOption(detailed.getOpt());

        String allocationMethodChosen = cmd.getOptionValue("a");
        if (cmd.getOptionValue(allocationMethod.getOpt()) == null) {
            outp += runEachMemoryAndPrintResults(jobList, detailedMode, verboseMode);
        }
        else {
            switch (allocationMethodChosen.toUpperCase()) {
                case "FE":
                    System.out.println("Fixed Partition - Equal Size");
                    memory = new EqualFixedMemory();
                    memory.start(jobList);
                    System.out.println(memory.getDataResults());
                    outp += String.format("Fixed Partition - Equal Size%n%s%n", memory.getDataResults());
                    break;
                case "FU":
                    System.out.println("Fixed Partition - Unequal Size");
                    memory = new UnequalFixedMemory();
                    memory.start(jobList);
                    System.out.println(memory.getDataResults());
                    outp += String.format("Fixed Partition - Unequal Size%n%s%n", memory.getDataResults());
                    break;
                case "DFF":
                    System.out.println("Dynamic Partition - First Fit");
                    memory = new DynamicMemory(new FirstFitProcessInserter());
                    memory.start(jobList);
                    System.out.println(memory.getDataResults());
                    outp += String.format("Dynamic Partition - First Fit%n%s%n", memory.getDataResults());
                    break;
                case "DBF":
                    System.out.println("Dynamic Partition - Best Fit");
                    memory = new DynamicMemory(new BestFitProcessInserter());
                    memory.start(jobList);
                    System.out.println(memory.getDataResults());
                    outp += String.format("Dynamic Partition - Best Fit%n%s%n", memory.getDataResults());
                    break;
                case "DWF":
                    System.out.println("Dynamic Partition - Worst Fit");
                    memory = new DynamicMemory(new WorstFitProcessInserter());
                    memory.start(jobList);
                    System.out.println(memory.getDataResults());
                    outp += String.format("Dynamic Partition - Worst Fit%n%s%n", memory.getDataResults());
                    break;
                default:
                    System.err.println("Invalid Allocation Method");
                    return;
            }
        }
        String path = cmd.getOptionValue(output.getOpt());
        if (path != null) {
            WriteFile wf = new WriteFile(path);
            try {
                wf.write(outp);
            } catch (IOException e) {
                System.err.println("Error Writing to File");
            }
        }
    }
}