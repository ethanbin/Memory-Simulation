package com.company;

/**
 * Created by Ethan on 11/27/2017.
 */
public class MemoryAllocation {
    protected int memorySizeNeeded;
    protected int startingPositionInMemory;
    protected int endingPositionInMemory;
    protected int memorySizeUsed;

    public MemoryAllocation(){
        memorySizeNeeded = -1;
        this.startingPositionInMemory = -1;
        this.endingPositionInMemory = -1;
    }


    public MemoryAllocation(int size){
        memorySizeNeeded = size;
        this.startingPositionInMemory = -1;
        this.endingPositionInMemory = -1;
    }

    public MemoryAllocation(int size, int startingPositionInMemory, int endingPositionInMemory){
        memorySizeNeeded = size;
        this.startingPositionInMemory = startingPositionInMemory;
        this.endingPositionInMemory = endingPositionInMemory;
    }

    public int getMemorySize() {
        return memorySizeNeeded;
    }

    public void setMemorySize(int memorySizeNeeded) {
        this.memorySizeNeeded = memorySizeNeeded;
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

    @Override
    public String toString() {
        return "MemoryAllocation{" +
                "memorySizeNeeded=" + memorySizeNeeded +
                ", startingPositionInMemory=" + startingPositionInMemory +
                ", endingPositionInMemory=" + endingPositionInMemory +
                '}';
    }
}
