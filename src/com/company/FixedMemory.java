package com.company;

/**
 * Created by Ethan on 11/29/2017.
 */
public abstract class FixedMemory extends Memory {
	
	@Override
	public boolean addProcesses(Process p, int partition) {
		for (int i = 0; i < memoryList.size(); i++) {
			MemoryAllocation currentAllocation = memoryList.get(i);

			// if current place isn't free space, continue loop to next place
			if (isMemoryAllocationAProcess(currentAllocation))
				continue;

			// checking to make sure the process can fit
			if (!p.isHasBeenAllocated()) {
				p.setMemorySizeUsed(p.getMemorySizeNeeded()); // set the size we will be taking up

				p.setStartingPositionInMemory(currMemoryPos); // the last stored time
				currMemoryPos += p.getMemorySizeNeeded();
				p.setEndingPositionInMemory(currMemoryPos);
				currMemoryPos = 128 * (partition + 1);

				p.setProcessFinishTime(p.getArrivalTime() + p.getTimeofProcess());
				currentTime = p.getArrivalTime();
				calculateInternalFragmentation(p);
				memoryList.set(i, p);
				return true;
			}
		}
		return false;
	}

	@Override
	public void removeProcesses() {
		for (int i = 0; i < memoryList.size(); i++) {
			Process currentProcess = (Process) memoryList.get(i);
			
			if (currentProcess.getProcessFinishTime() <= currentTime) {
				// calculateInternalFragmentation(currentProcess);
				MemoryAllocation memAlloc = new MemoryAllocation();
				memoryList.set(i, memAlloc);
			}
		}
	}
}
