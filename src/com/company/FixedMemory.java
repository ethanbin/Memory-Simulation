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
	public void removeFinishedProcesses() {
		
	}
}
