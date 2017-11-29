package com.company;

/**
 * Created by Ethan on 11/27/2017.
 */
public class MemoryAllocation {
    protected int memorySizeUsed;
    protected int startingPositionInMemory;
    protected int endingPositionInMemory;

    public MemoryAllocation(){
        memorySizeUsed = -1;
        this.startingPositionInMemory = -1;
        this.endingPositionInMemory = -1;
    }


    public MemoryAllocation(int size){
        memorySizeUsed = size;
        this.startingPositionInMemory = -1;
        this.endingPositionInMemory = -1;
    }

    public MemoryAllocation(int size, int startingPositionInMemory, int endingPositionInMemory){
        memorySizeUsed = size;
        this.startingPositionInMemory = startingPositionInMemory;
        this.endingPositionInMemory = endingPositionInMemory;
    }

    public int getMemorySize() {
        return memorySizeUsed;
    }

    public void setMemorySize(int memorySizeNeeded) {
        this.memorySizeUsed = memorySizeNeeded;
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

    @Override
    public String toString() {
        return "MemoryAllocation{" +
                "memorySizeUsed=" + memorySizeUsed +
                ", startingPositionInMemory=" + startingPositionInMemory +
                ", endingPositionInMemory=" + endingPositionInMemory +
                '}';
    }
}
