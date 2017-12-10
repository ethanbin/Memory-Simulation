package com.company;

import java.util.*;

public class generateData {

	private final static int DEFAULT_NUMBER_OF_PROCESSES = 20;
	private final static int DEFAULT_MAX_SIZE_OF_SPACE = 1024;
	private final static int DEFAULT_MAX_ARRIVAL_TIME = 50;
	private final static int DEFAULT_MAX_FINISH_TIME = 100;

	private int numberOfProcesses; // based on the user input
	private int maxSizeOfSpace;
	private int maxArrivalTime;
	private int maxFinishTime;

	private Random generate;
	private int processNumber;
	private int sizeOfSpace;
	private int arrivalTime;
	private int finishTime;
	private int [][] processes;

	public generateData() {
		numberOfProcesses = DEFAULT_NUMBER_OF_PROCESSES;
		maxSizeOfSpace = DEFAULT_MAX_SIZE_OF_SPACE;
		maxArrivalTime = DEFAULT_MAX_ARRIVAL_TIME;
		maxFinishTime = DEFAULT_MAX_FINISH_TIME;
		init();
	}

	public generateData(int numberOfProcesses, int maxSizeOfSpace, int maxArrivalTime, int maxFinishTime) {
		this.numberOfProcesses = numberOfProcesses;
		this.maxSizeOfSpace = maxSizeOfSpace;
		this.maxArrivalTime = maxArrivalTime;
		this.maxFinishTime = maxFinishTime;
		init();
	}

	public void init() {
		generate = new Random();
		processNumber = 0;
		arrivalTime = 0;
		finishTime = arrivalTime + 1;
		processes = new int [numberOfProcesses][4];
	}

	public int[] generateAProcess(int processNumber) {
		int[] p = new int[4];
		arrivalTime = generate.nextInt(maxArrivalTime);
		
		sizeOfSpace = generate.nextInt(maxSizeOfSpace);
		
		finishTime = generate.nextInt(maxArrivalTime);
		while (finishTime <= arrivalTime) { // to assure the final time will not be before or at the arrival time
			finishTime = generate.nextInt(maxArrivalTime);
		}
		
		//store values
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
		for(int [] row : processes) {
			for(int col: row) {
				System.out.print(col + "\t");
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void main(String[] args) {
		generateData test = new generateData();

		test.generateAllProcesses();
		test.display();
		
		generateData test2 = new generateData(5, 1000, 10, 85);
		
		test2.generateAllProcesses();
		test2.display();
	}
}
