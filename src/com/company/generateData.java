package com.company;

import java.util.*;

public class generateData {

		private final static int DEFAULT_NUMBER_OF_PROCESSES = 20;
	private final static int DEFAULT_MAX_ARRIVAL_TIME = 50;
	private final static int DEFAULT_MAX_FINISH_TIME = 100;

	private int numberOfProcesses; // based on the user input
	private int maxArrivalTime;
	private int maxFinishTime;

	private Random generate;
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
		generate = new Random();
		processNumber = 0;
		arrivalTime = 0;
		finishTime = arrivalTime + 1;
	}

	public int[] generateValues() {
		int[] p = new int[3];

		return p;
	}

	@Override
	public String toString() {
		return "generateData [numberOfProcesses=" + numberOfProcesses + ", maxArrivalTime=" + maxArrivalTime
				+ ", maxFinishTime=" + maxFinishTime + ", generate=" + generate + ", processNumber=" + processNumber
				+ ", sizeOfSpace=" + sizeOfSpace + ", arrivalTime=" + arrivalTime + ", finishTime=" + finishTime + "]";
	}

	public static void main(String[] args) {
		generateData test = new generateData();
		System.out.println(test);
		
		generateData test2= new generateData(5,10,15);
		System.out.println(test2);
	}
}
