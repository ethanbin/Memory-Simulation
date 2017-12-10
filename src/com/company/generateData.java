package com.company;

import java.util.*;
import java.io.IOException;

public class generateData {

	private final static int DEFAULT_NUMBER_OF_PROCESSES = 20;
	private final static int DEFAULT_MAX_SIZE_OF_SPACE = 1024;
	private final static int DEFAULT_MAX_ARRIVAL_TIME = 50;
	private final static int DEFAULT_MAX_FINISH_TIME = 100;
	private final static int DEFAULT_INCREMENT_SIZE = 0;

	private int numberOfProcesses; // based on the user input
	private int maxSizeOfSpace;
	private int maxArrivalTime;
	private int maxFinishTime;
	private int incrementSize;

	private Random generate;
	private int processNumber;
	private int sizeOfSpace;
	private int arrivalTime;
	private int finishTime;
	private int[][] processes;
	private String output;

	public generateData() {
		numberOfProcesses = DEFAULT_NUMBER_OF_PROCESSES;
		maxSizeOfSpace = DEFAULT_MAX_SIZE_OF_SPACE;
		maxArrivalTime = DEFAULT_MAX_ARRIVAL_TIME;
		maxFinishTime = DEFAULT_MAX_FINISH_TIME;
		incrementSize = DEFAULT_INCREMENT_SIZE;
		init();
	}

	public generateData(int numberOfProcesses, int maxSizeOfSpace, int maxArrivalTime, int maxFinishTime,
			int incrementSize) {
		this.numberOfProcesses = numberOfProcesses;
		this.maxSizeOfSpace = maxSizeOfSpace;
		this.maxArrivalTime = maxArrivalTime;
		this.maxFinishTime = maxFinishTime;
		this.incrementSize = incrementSize;
		init();
	}

	public void init() {
		generate = new Random();
		processNumber = 0;
		arrivalTime = 0;
		finishTime = arrivalTime + 1;
		processes = new int[numberOfProcesses][4];
		output = "";
	}

	public int[] generateAProcess(int processNumber) {
		int[] p = new int[4];
		arrivalTime = generate.nextInt(maxArrivalTime);

		sizeOfSpace = generate.nextInt(maxSizeOfSpace) + incrementSize;

		finishTime = generate.nextInt(maxFinishTime);
		while (finishTime <= arrivalTime) { // to assure the final time will not be before or at the arrival time
			finishTime = generate.nextInt(maxFinishTime);
		}

		// store values
		p[0] = processNumber;
		p[1] = arrivalTime;
		p[2] = sizeOfSpace;
		p[3] = finishTime;

		return p;
	}

	public void generateAllProcesses() {

		for (int i = 0; i < numberOfProcesses; i++) {
			processes[i] = generateAProcess(i);
		}
	}

	public void display() {

		for (int[] row : processes) {
			for (int col : row) {
				output += (col + "\t");
			}
			output += "\n";
		}
	}

	public static void main(String[] args) {

		generateData small = new generateData(50, 50, 20, 60, 1); // ranges from 0-51
		generateData medium = new generateData(50, 50, 20, 60, 51); // ranges from 51-101
		generateData large = new generateData(50, 50, 20, 60, 101); // ranges from 101-151
		generateData random = new generateData(50, 150, 20, 60, 1); // ranges from 1-151

		for (int i = 0; i < 5; i++) {
			small.generateAllProcesses();
			small.display();
			medium.generateAllProcesses();
			medium.display();
			large.generateAllProcesses();
			large.display();
			random.generateAllProcesses();
			random.display();
		}

		String file1 = "F:/Small.txt";
		String file2 = "F:/Medium.txt";
		String file3 = "F:/Large.txt";
		String file4 = "F:/Random.txt";

		try {
			WriteFile s = new WriteFile(file1, true);
			s.write(small.output);
			WriteFile m = new WriteFile(file2, true);
			m.write(medium.output);
			WriteFile l = new WriteFile(file3, true);
			l.write(large.output);
			WriteFile r = new WriteFile(file4, true);
			r.write(random.output);
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
