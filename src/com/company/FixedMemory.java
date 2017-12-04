package com.company;

/**
 * Created by Ethan on 11/29/2017.
 */
public abstract class FixedMemory extends Memory {
	
	@Override
	public void calculateInternalFragmentation(int partitionSize, Process p) {
		internalFragmentation += partitionSize - p.getMemorySizeNeeded();
	}

	@Override
	public void calculateExternalFragmentation(Process P) {
		// fixed sized partitions don't have external fragmentation
	}
	
	@Override
	public void removeFinishedProcesses(int currTime, int totalPartitions) {
		for(int i = 0; i < totalPartitions; i++) {
			Process currProcess = (Process)memoryList.get(i);
			if(currProcess.getFinishTime() <= currTime) 	//if it should be done, remove it
				memoryList.remove(i);
		}
	}
}
