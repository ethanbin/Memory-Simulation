package com.company;

/**
 * Created by Ethan on 11/27/2017.
 */
public class MemoryAllocation {
    protected int memorySizeUsed;
    protected int startingPositionInMemory;
    protected int endingPositionInMemory;
    protected int processFinishTime;

	public MemoryAllocation(){
        memorySizeUsed = -1;
        this.startingPositionInMemory = -1;
        this.endingPositionInMemory = -1;
        this.processFinishTime = -1;
    }


    public MemoryAllocation(int size){
        memorySizeUsed = size;
        this.startingPositionInMemory = -1;
        this.endingPositionInMemory = -1;
        this.processFinishTime = -1;
    }

    public MemoryAllocation(int size, int startingPositionInMemory, int endingPositionInMemory){
        memorySizeUsed = size;
        this.startingPositionInMemory = startingPositionInMemory;
        this.endingPositionInMemory = endingPositionInMemory;
        this.processFinishTime = -1;	//depending on what time it starts at, so for now it's -1
    }

    public int getStartingPositionInMemory() {
        return startingPositionInMemory;
    }

    public void setStartingPositionInMemory(int startingPositionInMemory) {
        this.startingPositionInMemory = startingPositionInMemory;
    }

    public int getEndingPositionInMemory() {
        return endingPositionInMemory;
    }

    public void setEndingPositionInMemory(int endingPositionInMemory) {
        this.endingPositionInMemory = endingPositionInMemory;
    }

    public int getMemorySizeUsed() {
        return memorySizeUsed;
    }

    public void setMemorySizeUsed(int memorySizeUsed) {
        this.memorySizeUsed = memorySizeUsed;
    }
    
    public int getProcessFinishTime() {
		return processFinishTime;
	}

	public void setProcessFinishTime(int processFinishTime) {
		this.processFinishTime = processFinishTime;
	}


    @Override
    public String toString() {
        return "MemoryAllocation{" +
                "memorySizeUsed=" + memorySizeUsed +
                ", startingPositionInMemory=" + startingPositionInMemory +
                ", endingPositionInMemory=" + endingPositionInMemory +
                ", processFinishTime=" + processFinishTime +
                '}';
    }
}
