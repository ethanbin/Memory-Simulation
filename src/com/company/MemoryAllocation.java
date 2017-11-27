package com.company;

/**
 * Created by Ethan on 11/27/2017.
 */
public class MemoryAllocation {
    protected int memorySize;
    protected int startingPositionInMemory;
    protected int endingPositionInMemory;

    public int getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(int memorySize) {
        this.memorySize = memorySize;
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
                "memorySize=" + memorySize +
                ", startingPositionInMemory=" + startingPositionInMemory +
                ", endingPositionInMemory=" + endingPositionInMemory +
                '}';
    }
}
