package com.company;

import java.util.*;

public class generateData {

	private final static int DEFAULT_NUMBER_OF_PROCESSES = 20;
	private final static int DEFAULT_MAX_ARRIVAL_TIME = 50;
	private final static int DEFAULT_MAX_FINISH_TIME = 100;

	private int numberOfProcesses; // based on the user input
	private int maxArrivalTime;
	private int maxFinishTime;

	private int processNumber;
	private int sizeOfSpace;
	private int arrivalTime;
	private int finishTime;

	public generateData() {
		numberOfProcesses = DEFAULT_NUMBER_OF_PROCESSES;
		maxArrivalTime = DEFAULT_MAX_ARRIVAL_TIME;
		maxFinishTime = DEFAULT_MAX_FINISH_TIME;
		init();
	}

	public generateData(int numberOfProcesses, int maxArrivalTime, int maxFinishTime) {
		this.numberOfProcesses = numberOfProcesses;
		this.maxArrivalTime = maxArrivalTime;
		this.maxFinishTime = maxFinishTime;
		init();
	}

	public void init() {
		processNumber = 0;
		arrivalTime = 0;
		finishTime = arrivalTime + 1;
	}

	public static void main(String[] args) {

	}
}
